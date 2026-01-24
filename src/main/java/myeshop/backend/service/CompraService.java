package myeshop.backend.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import myeshop.backend.dto.CompraRespuestaDTO;
import myeshop.backend.dto.CompraSolicitudDTO;
import myeshop.backend.dto.LineaCompraDTO;
import myeshop.backend.model.Articulo;
import myeshop.backend.model.ArticuloCompra;
import myeshop.backend.model.Cliente;
import myeshop.backend.model.Compra;
import myeshop.backend.repository.ArticuloCompraRepository;
import myeshop.backend.repository.ArticuloRepository;
import myeshop.backend.repository.ClienteRepository;
import myeshop.backend.repository.CompraRepository;

@Service
public class CompraService {

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private ArticuloRepository articuloRepository;

    @Autowired
    private ArticuloCompraRepository articuloCompraRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional
    public CompraRespuestaDTO procesarCompra(CompraSolicitudDTO solicitud) {
        
        // 1. Validar Cliente
        Cliente cliente = clienteRepository.findById(solicitud.getNifCliente())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con NIF: " + solicitud.getNifCliente()));

        // 2. Crear Cabecera de Compra
        Compra compra = new Compra();
        compra.setCliente(cliente);
        compra.setFechaCompra(LocalDateTime.now());
        compra.setEstado("COMPLETADO"); // Por defecto simple
        compra.setDireccionEntrega(solicitud.getDireccionEntrega());
        compra.setPrecioTotal(BigDecimal.ZERO); // Se calculará después

        // Guardamos primero la compra para generar el ID necesario para las líneas
        compra = compraRepository.save(compra);

        BigDecimal totalCompra = BigDecimal.ZERO;
        List<LineaCompraDTO> lineasDTO = new ArrayList<>();

        // 3. Procesar Líneas
        for (CompraSolicitudDTO.ItemCestaDTO item : solicitud.getItems()) {
            Articulo articulo = articuloRepository.findById(item.getArticuloId())
                    .orElseThrow(() -> new RuntimeException("Artículo no encontrado ID: " + item.getArticuloId()));

            // Validar Stock
            if (articulo.getStock() < item.getUnidades()) {
                throw new RuntimeException("Stock insuficiente para: " + articulo.getNombre());
            }

            // Actualizar Stock
            articulo.setStock(articulo.getStock() - item.getUnidades());
            articuloRepository.save(articulo);

            // Calcular Precios
            BigDecimal precioUnitario = articulo.getPrecioActual();
            BigDecimal subtotal = precioUnitario.multiply(new BigDecimal(item.getUnidades()));
            totalCompra = totalCompra.add(subtotal);

            // Crear Entidad Relación N:N
            ArticuloCompra linea = new ArticuloCompra(articulo, compra, item.getUnidades(), precioUnitario);
            articuloCompraRepository.save(linea);

            // Añadir a respuesta DTO
            lineasDTO.add(new LineaCompraDTO(
                articulo.getId(),
                articulo.getNombre(),
                item.getUnidades(),
                precioUnitario,
                subtotal
            ));
        }

        // 4. Actualizar Total Compra
        compra.setPrecioTotal(totalCompra);
        compraRepository.save(compra);

        // 5. Construir Respuesta
        CompraRespuestaDTO respuesta = new CompraRespuestaDTO();
        respuesta.setId(compra.getId());
        respuesta.setFechaCompra(compra.getFechaCompra());
        respuesta.setEstado(compra.getEstado());
        respuesta.setDireccionEntrega(compra.getDireccionEntrega());
        respuesta.setPrecioTotal(compra.getPrecioTotal());
        respuesta.setLineas(lineasDTO);

        return respuesta;
    }

    @Transactional(readOnly = true)
    public List<CompraRespuestaDTO> obtenerComprasPorCliente(String nif) {
        return compraRepository.findByCliente_NifCif(nif).stream()
                .map(this::convertirACompraRespuestaDTO)
                .collect(Collectors.toList());
    }

    private CompraRespuestaDTO convertirACompraRespuestaDTO(Compra compra) {
        CompraRespuestaDTO dto = new CompraRespuestaDTO();
        dto.setId(compra.getId());
        dto.setFechaCompra(compra.getFechaCompra());
        dto.setEstado(compra.getEstado());
        dto.setDireccionEntrega(compra.getDireccionEntrega());
        dto.setPrecioTotal(compra.getPrecioTotal());

        // Recuperar líneas: Ojo con Lazy Loading, aquí confiamos en que 
        // la sesión esté abierta (@Transactional) o usamos una query dedicada
        List<LineaCompraDTO> lineas = articuloCompraRepository.findById_CompraId(compra.getId()).stream()
            .map(ac -> {
                BigDecimal subtotal = ac.getPrecioCompra().multiply(new BigDecimal(ac.getUnidades()));
                return new LineaCompraDTO(
                    ac.getArticulo().getId(),
                    ac.getArticulo().getNombre(),
                    ac.getUnidades(),
                    ac.getPrecioCompra(),
                    subtotal
                );
            }).collect(Collectors.toList());
        
        dto.setLineas(lineas);
        return dto;
    }
}

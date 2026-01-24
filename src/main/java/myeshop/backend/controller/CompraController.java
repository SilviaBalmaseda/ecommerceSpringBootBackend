package myeshop.backend.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import myeshop.backend.dto.CompraRespuestaDTO;
import myeshop.backend.dto.CompraSolicitudDTO;
import myeshop.backend.service.CompraService;

/**
 * Controlador para la gestión de procesos de Compra.
 * Facilita la interacción entre la capa de presentación y la lógica de negocio de compras.
 * 
 * @author Rafael Robles
 */
@Controller
public class CompraController {

    @Autowired
    private CompraService compraService;

    /**
     * Inicia el proceso de una nueva compra.
     * 
     * @param solicitud Datos necesarios para procesar el pedido.
     * @return Resultado de la compra con detalles confirmados.
     */
    public CompraRespuestaDTO procesarCompra(CompraSolicitudDTO solicitud) {
        System.out.println("--- CAPA CONTROLADOR: Procesando compra para cliente NIF: " + solicitud.getNifCliente() + " ---");
        return compraService.procesarCompra(solicitud);
    }

    /**
     * Consulta el historial de compras de un cliente.
     * 
     * @param nif NIF del cliente a consultar.
     * @return Lista de compras previas.
     */
    public List<CompraRespuestaDTO> obtenerHistorial(String nif) {
        System.out.println("--- CAPA CONTROLADOR: Obteniendo historial de compras del cliente: " + nif + " ---");
        return compraService.obtenerComprasPorCliente(nif);
    }
}
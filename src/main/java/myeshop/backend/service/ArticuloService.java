package myeshop.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import myeshop.backend.dto.ArticuloDTO;
import myeshop.backend.model.Articulo;
import myeshop.backend.repository.ArticuloRepository;

/**
 * Servicio de lógica de negocio para gestionar el catálogo de artículos.
 * 
 * @author Rafael Robles
 */
@Service
public class ArticuloService {

    @Autowired
    private ArticuloRepository articuloRepository;

    /**
     * Obtiene todos los artículos disponibles en el catálogo.
     * 
     * @return Lista de DTOs con la información de los artículos.
     */
    @Transactional(readOnly = true)
    public List<ArticuloDTO> listarTodos() {
        return articuloRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca un artículo específico por su identificador único.
     * 
     * @param id Identificador del artículo a buscar.
     * @return DTO con los detalles del artículo.
     * @throws RuntimeException si no encuentra el artículo.
     */
    @Transactional(readOnly = true)
    public ArticuloDTO obtenerPorId(Integer id) {
        Articulo articulo = articuloRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artículo no encontrado con ID: " + id));
        return convertirADTO(articulo);
    }

    // Método interno para convertir Entidad -> DTO
    private ArticuloDTO convertirADTO(Articulo articulo) {
        return new ArticuloDTO(
            articulo.getId(),
            articulo.getNombre(),
            articulo.getDescripcion(),
            articulo.getPrecioActual(),
            articulo.getStock()
        );
    }
}
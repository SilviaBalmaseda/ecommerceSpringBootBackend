package myeshop.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import myeshop.backend.dto.ArticuloDTO;
import myeshop.backend.model.Articulo;
import myeshop.backend.repository.ArticuloRepository;

@Service
public class ArticuloService {

    @Autowired
    private ArticuloRepository articuloRepository;

    @Transactional(readOnly = true)
    public List<ArticuloDTO> listarTodos() {
        return articuloRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

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

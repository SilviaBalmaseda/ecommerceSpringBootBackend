package myeshop.backend.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import myeshop.backend.dto.ArticuloDTO;
import myeshop.backend.service.ArticuloService;

/**
 * Controlador para la gestión y consulta de Artículos.
 * Actúa como punto de entrada para las operaciones relacionadas con el catálogo.
 * 
 * @author Rafael Robles
 */
@Controller
public class ArticuloController {

    @Autowired
    private ArticuloService articuloService;

    /**
     * Recupera el listado completo de artículos.
     * 
     * @return Lista de artículos disponibles.
     */
    public List<ArticuloDTO> listarArticulos() {
        System.out.println("--- CAPA CONTROLADOR: Listando artículos ---");
        return articuloService.listarTodos();
    }

    /**
     * Obtiene el detalle de un artículo específico.
     * 
     * @param id ID del artículo.
     * @return Datos del artículo solicitado.
     */
    public ArticuloDTO obtenerArticulo(Integer id) {
        System.out.println("--- CAPA CONTROLADOR: Buscando artículo ID: " + id + " ---");
        return articuloService.obtenerPorId(id);
    }
}
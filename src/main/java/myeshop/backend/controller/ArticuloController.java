package myeshop.backend.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import myeshop.backend.dto.ArticuloDTO;
import myeshop.backend.service.ArticuloService;

@Controller
public class ArticuloController {

    @Autowired
    private ArticuloService articuloService;

    public List<ArticuloDTO> listarArticulos() {
        System.out.println("--- CAPA CONTROLADOR: Listando artículos ---");
        return articuloService.listarTodos();
    }

    public ArticuloDTO obtenerArticulo(Integer id) {
        System.out.println("--- CAPA CONTROLADOR: Buscando artículo ID: " + id + " ---");
        return articuloService.obtenerPorId(id);
    }
}

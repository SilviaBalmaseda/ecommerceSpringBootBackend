package myeshop.backend.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import myeshop.backend.dto.CompraRespuestaDTO;
import myeshop.backend.dto.CompraSolicitudDTO;
import myeshop.backend.service.CompraService;

@Controller
public class CompraController {

    @Autowired
    private CompraService compraService;

    public CompraRespuestaDTO procesarCompra(CompraSolicitudDTO solicitud) {
        System.out.println("--- CAPA CONTROLADOR: Procesando compra para cliente NIF: " + solicitud.getNifCliente() + " ---");
        return compraService.procesarCompra(solicitud);
    }

    public List<CompraRespuestaDTO> obtenerHistorial(String nif) {
        System.out.println("--- CAPA CONTROLADOR: Obteniendo historial de compras del cliente: " + nif + " ---");
        return compraService.obtenerComprasPorCliente(nif);
    }
}

package myeshop.backend.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import myeshop.backend.dto.ClienteRegistroDTO;
import myeshop.backend.dto.ClienteRespuestaDTO;
import myeshop.backend.service.ClienteService;

/**
 * Controlador para gestionar las solicitudes relacionadas con clientes.	
 * 
 * @author Silvia Balmaseda
 */
@Controller
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    // Crear cliente
    public ClienteRespuestaDTO registrarCliente(ClienteRegistroDTO peticion) {
        System.out.println("--- CAPA CONTROLADOR: Registrando cliente ---");
        return clienteService.registrarCliente(peticion);
    }

    // Listar todos
    public List<ClienteRespuestaDTO> listarClientes() {
        System.out.println("--- CAPA CONTROLADOR: Listando clientes ---");
        return clienteService.listarTodos();
    }

    // Obtener por NIF/CIF
    public ClienteRespuestaDTO obtenerPorNif(String nifCif) {
        System.out.println("--- CAPA CONTROLADOR: Buscando cliente ---");
        return clienteService.obtenerPorNif(nifCif);
    }
}


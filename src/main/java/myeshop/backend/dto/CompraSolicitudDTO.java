package myeshop.backend.dto;

import java.util.List;
import lombok.Data;

/**
 * DTO de entrada para recibir una solicitud de nueva compra desde el cliente.
 * Contiene la información mínima necesaria para procesar un pedido.
 * 
 * @author Rafael Robles
 */
@Data
public class CompraSolicitudDTO {
    // Lista de productos y cantidades que quiere comprar el cliente
    // (DTO interno simple para recibir datos del front)
    private List<ItemCestaDTO> items; 
    private String direccionEntrega;
    private String nifCliente;

    /**
     * DTO interno estático para representar cada ítem de la cesta de la compra.
     */
    @Data
    public static class ItemCestaDTO {
        private Integer articuloId;
        private Integer unidades;
    }
}
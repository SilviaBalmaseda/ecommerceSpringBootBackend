package myeshop.backend.dto;

import java.util.List;
import lombok.Data;

@Data
public class CompraSolicitudDTO {
    // Lista de productos y cantidades que quiere comprar el cliente
    // (DTO interno simple para recibir datos del front)
    private List<ItemCestaDTO> items; 
    private String direccionEntrega;
    private String nifCliente;

    @Data
    public static class ItemCestaDTO {
        private Integer articuloId;
        private Integer unidades;
    }
}

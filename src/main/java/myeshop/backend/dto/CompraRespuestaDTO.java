package myeshop.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

/**
 * DTO de salida con el resumen completo de una compra procesada.
 * Incluye cabecera y lista de líneas de detalle.
 * 
 * @author Rafael Robles
 */
@Data
public class CompraRespuestaDTO {
    private Integer id;
    private LocalDateTime fechaCompra;
    private String estado;
    private String direccionEntrega;
    private BigDecimal precioTotal;
    private List<LineaCompraDTO> lineas;
}
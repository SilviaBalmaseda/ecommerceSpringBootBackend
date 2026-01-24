package myeshop.backend.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO que representa una línea de detalle de una compra finalizada.
 * Contiene la información calculada del producto, unidades y precio en el momento de la compra.
 * 
 * @author Rafael Robles
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LineaCompraDTO {
    private Integer articuloId;
    private String nombreArticulo;
    private Integer unidades;
    private BigDecimal precioUnitario; 
    private BigDecimal subtotal;
}
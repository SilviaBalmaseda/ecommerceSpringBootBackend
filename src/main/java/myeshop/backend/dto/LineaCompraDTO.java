package myeshop.backend.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

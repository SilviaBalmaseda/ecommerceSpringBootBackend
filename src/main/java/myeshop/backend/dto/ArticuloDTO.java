package myeshop.backend.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticuloDTO {
    private Integer id;
    private String nombre;
    private String descripcion;
    private BigDecimal precioActual;
    private Integer stock;
}

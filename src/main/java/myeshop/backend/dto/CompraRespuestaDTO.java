package myeshop.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class CompraRespuestaDTO {
    private Integer id;
    private LocalDateTime fechaCompra;
    private String estado;
    private String direccionEntrega;
    private BigDecimal precioTotal;
    private List<LineaCompraDTO> lineas;
}

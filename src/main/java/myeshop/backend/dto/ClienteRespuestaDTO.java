package myeshop.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Silvia Balmaseda
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteRespuestaDTO {

    private String nifCif;
    private String nombreCompleto;
    private String email;
}

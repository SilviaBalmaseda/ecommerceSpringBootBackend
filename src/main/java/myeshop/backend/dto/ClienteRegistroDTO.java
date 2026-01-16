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
public class ClienteRegistroDTO {

    private String nifCif;
    private String nombreCompleto;
    private String email;

    // Información fiscal (aplanada como Perfil)
    private String telefono;
    private String direccionFiscal;
}


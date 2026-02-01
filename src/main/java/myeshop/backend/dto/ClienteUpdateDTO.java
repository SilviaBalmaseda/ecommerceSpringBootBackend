package myeshop.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para transferir datos de registro de un cliente al backend.
 * Evita exponer la entidad JPA directamente.
 * Este puede ser Opcional
 * 
 * @author Silvia Balmaseda
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteUpdateDTO {
    private String nombreCompleto;
    private String email;
    
    // Datos fiscales (opcionales)
    private String telefono;
    private String direccionFiscal;
}


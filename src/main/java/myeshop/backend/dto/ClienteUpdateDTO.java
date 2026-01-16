package myeshop.backend.dto;

/**
 * 
 * Opcional
 * @author Silvia Balmaseda
 */
public class ClienteUpdateDTO {

    private String nombreCompleto;
    private String email;

    // Datos fiscales (opcionales)
    private String telefono;
    private String direccionFiscal;

    public ClienteUpdateDTO() {
    }

    public ClienteUpdateDTO(String nombreCompleto, String email,
                            String telefono, String direccionFiscal) {
        this.nombreCompleto = nombreCompleto;
        this.email = email;
        this.telefono = telefono;
        this.direccionFiscal = direccionFiscal;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccionFiscal() {
        return direccionFiscal;
    }

    public void setDireccionFiscal(String direccionFiscal) {
        this.direccionFiscal = direccionFiscal;
    }
}

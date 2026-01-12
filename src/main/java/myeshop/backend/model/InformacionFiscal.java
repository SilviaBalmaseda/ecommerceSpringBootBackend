package myeshop.backend.model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * Representa la información fiscal asociada a un cliente.
 * Mapeado a la tabla 'informacion_fiscal' de Oracle con PK compartida.
 * * @author Silvia Balmaseda
 */
@Entity
@Table(name = "informacion_fiscal")
public class InformacionFiscal {

    @Id
    @Column(name = "nif_cif", length = 20)
    private String nifCif;

    @Column(name = "telefono", length = 20)
    private String telefono;

    @Column(name = "direccion_fiscal", length = 255)
    private String direccionFiscal;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "nif_cif")
    private Cliente cliente;

    public InformacionFiscal() {
    }

    // Getters y Setters
    public String getNifCif() { return nifCif; }
    public void setNifCif(String nifCif) { this.nifCif = nifCif; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getDireccionFiscal() { return direccionFiscal; }
    public void setDireccionFiscal(String direccionFiscal) { this.direccionFiscal = direccionFiscal; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    @Override
    public String toString() {
        return "InfoFiscal [" + nifCif + "]: " + direccionFiscal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InformacionFiscal info = (InformacionFiscal) o;
        return Objects.equals(nifCif, info.nifCif);
    }

    @Override
    public int hashCode() {
        return nifCif != null ? Objects.hash(nifCif) : System.identityHashCode(this);
    }
}
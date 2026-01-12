package myeshop.backend.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * Representa un cliente en el sistema de gestión de e-commerce.
 * Mapeado a la tabla 'cliente' de Oracle.
 * * @author Silvia Balmaseda
 */
@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @Column(name = "nif_cif", length = 20)
    private String nifCif;

    @Column(name = "nombre_completo", nullable = false, length = 100)
    private String nombreCompleto;

    @Column(name = "email", length = 150)
    private String email;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    // --- RELACIÓN 1:1 ---
    @OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private InformacionFiscal informacionFiscal;

    // --- RELACIÓN 1:N ---
    // Mantenemos la lógica de seguridad: no borrar compras si se borra el cliente
    @OneToMany(mappedBy = "cliente", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private Set<Compra> compras = new HashSet<>();

    public Cliente() {
    }

    // Getters y Setters
    public String getNifCif() { return nifCif; }
    public void setNifCif(String nifCif) { this.nifCif = nifCif; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    public InformacionFiscal getInformacionFiscal() { return informacionFiscal; }

    /* Sincronización manual de relación 1:1 bidireccional */
    public void setInformacionFiscal(InformacionFiscal info) {
        if (info == null) {
            if (this.informacionFiscal != null) {
                this.informacionFiscal.setCliente(null);
            }
        } else {
            info.setCliente(this);
        }
        this.informacionFiscal = info;
    }

    public Set<Compra> getCompras() { return compras; }
    public void setCompras(Set<Compra> compras) { this.compras = compras; }

    /* Métodos Helper para relación 1:N */
    public void addCompra(Compra compra) {
        compras.add(compra);
        compra.setCliente(this);
    }

    public void removeCompra(Compra compra) {
        compras.remove(compra);
        compra.setCliente(null);
    }

    public int getNumCompras() {
        return compras != null ? compras.size() : 0;
    }

    @Override
    public String toString() {
        return "Cliente [" + nifCif + "]: " + nombreCompleto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(nifCif, cliente.nifCif);
    }

    @Override
    public int hashCode() {
        return nifCif != null ? Objects.hash(nifCif) : System.identityHashCode(this);
    }
}
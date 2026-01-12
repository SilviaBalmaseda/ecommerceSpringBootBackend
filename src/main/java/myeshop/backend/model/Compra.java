package myeshop.backend.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Entidad que representa la cabecera de una compra.
 * Mapeada a la tabla 'compra' de Oracle.
 * * @author Rafael Robles
 */
@Entity
@Table(name = "compra")
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "fecha_compra")
    private LocalDateTime fechaCompra;

    @Column(name = "estado", length = 20)
    private String estado;

    @Column(name = "direccion_entrega", length = 255)
    private String direccionEntrega;

    @Column(name = "precio_total", precision = 10, scale = 2, nullable = false)
    private BigDecimal precioTotal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_nif_cif", nullable = true) 
    private Cliente cliente;

    @OneToMany(mappedBy = "compra", fetch = FetchType.LAZY)
    private Set<ArticuloCompra> lineas = new HashSet<>();

    public Compra() {
    }

    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public LocalDateTime getFechaCompra() { return fechaCompra; }
    public void setFechaCompra(LocalDateTime fechaCompra) { this.fechaCompra = fechaCompra; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getDireccionEntrega() { return direccionEntrega; }
    public void setDireccionEntrega(String direccionEntrega) { this.direccionEntrega = direccionEntrega; }

    public BigDecimal getPrecioTotal() { return precioTotal; }
    public void setPrecioTotal(BigDecimal precioTotal) { this.precioTotal = precioTotal; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public Set<ArticuloCompra> getLineas() { return lineas; }
    public void setLineas(Set<ArticuloCompra> lineas) { this.lineas = lineas; }

    // Métodos Helper para sincronizar la relación bidireccional con las líneas
    public void addLinea(ArticuloCompra linea) {
        lineas.add(linea);
        linea.setCompra(this);
    }

    public void removeLinea(ArticuloCompra linea) {
        lineas.remove(linea);
        linea.setCompra(null);
    }

    @Override
    public String toString() {
        return "Compra [ID=" + id + ", Total=" + precioTotal + ", Estado=" + estado + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Compra other = (Compra) o;
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return id != null ? Objects.hash(id) : System.identityHashCode(this);
    }
}
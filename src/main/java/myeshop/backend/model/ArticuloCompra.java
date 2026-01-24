package myeshop.backend.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Entidad que representa una línea dentro de una compra (relación N:N entre
 * Articulo y Compra) con unidades y precio unitario aplicado.
 * @author Rafael Robles
 */
@Entity
@Table(name = "articulo_compra")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ArticuloCompra {

    @EmbeddedId
    private ArticuloCompraId id = new ArticuloCompraId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("articuloId")
    @JoinColumn(name = "articulo_id", nullable = false)
    @ToString.Exclude
    private Articulo articulo;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("compraId")
    @JoinColumn(name = "compra_id", nullable = false)
    @ToString.Exclude
    private Compra compra;

    @Column(name = "unidades")
    private Integer unidades;

    @Column(name = "precio_compra", precision = 10, scale = 2)
    private BigDecimal precioCompra;

    public ArticuloCompra(Articulo articulo, Compra compra, Integer unidades, BigDecimal precioCompra) {
        this.articulo = articulo;
        this.compra = compra;
        this.unidades = unidades;
        this.precioCompra = precioCompra;
        if (articulo != null) {
            this.id.setArticuloId(articulo.getId());
        }
        if (compra != null && compra.getId() != null) {
            this.id.setCompraId(compra.getId());
        }
    }

    // Custom helper setters that manage the composite key
    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
        if (articulo != null) {
            this.id.setArticuloId(articulo.getId());
        } else {
            this.id.setArticuloId(null);
        }
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
        if (compra != null) {
            this.id.setCompraId(compra.getId());
        } else {
            this.id.setCompraId(null);
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticuloCompra other = (ArticuloCompra) o;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public int hashCode() {
        return (id != null) ? Objects.hash(id) : System.identityHashCode(this);
    }

    // --- Clase para Clave Compuesta ---
    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ArticuloCompraId implements Serializable {
        private static final long serialVersionUID = 1L;

        @Column(name = "articulo_id")
        private Integer articuloId;

        @Column(name = "compra_id")
        private Integer compraId;
    }
}
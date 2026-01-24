package myeshop.backend.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Representa un artículo en la tienda online.
 * Mapeado para base de datos Oracle.
 * @author Rafael Robles
 */
@Entity
@Table(name = "articulo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "compras")
public class Articulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre", length = 100)
    private String nombre;

    @Lob // Necesario para el tipo CLOB en Oracle
    @Column(name = "descripcion")
    @Basic(fetch = FetchType.LAZY)
    private String descripcion;

    @Column(name = "precio_actual", precision = 10, scale = 2)
    private BigDecimal precioActual;

    @Column(name = "stock")
    private Integer stock;

    @OneToMany(mappedBy = "articulo", fetch = FetchType.LAZY)
    private Set<ArticuloCompra> compras = new HashSet<>();

    // Métodos Helper para relaciones bidireccionales
    public void addCompra(ArticuloCompra compra) {
        compras.add(compra);
        compra.setArticulo(this);
    }

    public void removeCompra(ArticuloCompra compra) {
        compras.remove(compra);
        compra.setArticulo(null);
    }

    @Override
    public int hashCode() {
        return id != null ? Objects.hash(id) : System.identityHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Articulo other = (Articulo) obj;
        return Objects.equals(id, other.id);
    }
}
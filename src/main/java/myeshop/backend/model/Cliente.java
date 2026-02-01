package myeshop.backend.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Representa un cliente en el sistema de gestión de e-commerce.
 * Mapeado a la tabla 'cliente' de Oracle.
 * @author Silvia Balmaseda
 */
@Entity
@Table(name = "cliente")
@Data
@NoArgsConstructor
public class Cliente {

    @Id
    @Column(name = "nif_cif", length = 20)
    private String nifCif;

    @Column(name = "nombre_completo", nullable = false, length = 100)
    private String nombreCompleto;

    @Column(length = 150)
    private String email;

    @CreationTimestamp
    @Column(name = "fecha_registro", updatable = false)
    private LocalDateTime fechaRegistro;

    // ---------- RELACIONES ----------

    // 1:1 con InformacionFiscal (PK compartida)
    @OneToOne(
        mappedBy = "cliente",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private InformacionFiscal informacionFiscal;

    // 1:N con Compra
    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Compra> compras = new HashSet<>();
}

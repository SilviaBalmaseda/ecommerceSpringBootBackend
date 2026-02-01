package myeshop.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Representa la información fiscal asociada a un cliente.
 * Mapeado a la tabla 'informacion_fiscal' de Oracle con PK compartida.
 * @author Silvia Balmaseda
 */
@Entity
@Table(name = "informacion_fiscal")
@Data
@NoArgsConstructor
public class InformacionFiscal {

    @Id
    @Column(name = "nif_cif", length = 20)
    private String nifCif;

    @Column(length = 20)
    private String telefono;

    @Column(name = "direccion_fiscal", length = 255)
    private String direccionFiscal;

    @OneToOne
    @MapsId
    @JoinColumn(name = "nif_cif")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Cliente cliente;
}

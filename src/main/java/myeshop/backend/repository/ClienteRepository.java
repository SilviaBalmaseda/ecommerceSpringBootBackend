package myeshop.backend.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import myeshop.backend.model.Cliente;


/**
 * Repositorio para la entidad intermedia Cliente.
 * Gestiona las líneas de detalle de cliente.
 * @author Silvia Balmaseda
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, String> {

    // ---------- BÚSQUEDAS BÁSICAS ----------

    Optional<Cliente> findByEmail(String email);

    List<Cliente> findByNombreCompletoContainingIgnoreCase(String texto);

    // ---------- JOIN FETCH (OPTIMIZACIÓN) ----------

    // Cliente + Información Fiscal
    @Query("""
        SELECT c 
        FROM Cliente c
        LEFT JOIN FETCH c.informacionFiscal
        WHERE c.nifCif = :nif
    """)
    Optional<Cliente> findByNifWithInformacionFiscal(@Param("nif") String nifCif);

    // Cliente + Compras
    @Query("""
        SELECT c 
        FROM Cliente c
        LEFT JOIN FETCH c.compras
        WHERE c.nifCif = :nif
    """)
    Optional<Cliente> findByNifWithCompras(@Param("nif") String nifCif);

    // Cliente + Compras + Info Fiscal (completo)
    @Query("""
        SELECT DISTINCT c
        FROM Cliente c
        LEFT JOIN FETCH c.compras
        LEFT JOIN FETCH c.informacionFiscal
        WHERE c.nifCif = :nif
    """)
    Optional<Cliente> findByNifCompleto(@Param("nif") String nifCif);
}

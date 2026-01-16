package myeshop.backend.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import myeshop.backend.model.InformacionFiscal;

/**
 * Repositorio para la entidad intermedia InformacionFiscal.
 * Gestiona las líneas de detalle de la información fiscal.
 * @author Silvia Balmaseda
 */
@Repository
public interface InformacionFiscalRepository extends JpaRepository<InformacionFiscal, String> {

    // Buscar por teléfono
    List<InformacionFiscal> findByTelefonoContaining(String telefono);

    // Buscar con dirección fiscal informada
    List<InformacionFiscal> findByDireccionFiscalIsNotNull();

    // Buscar por nif del cliente (aunque sea PK, queda más expresivo)
    Optional<InformacionFiscal> findByCliente_NifCif(String nifCif);
}

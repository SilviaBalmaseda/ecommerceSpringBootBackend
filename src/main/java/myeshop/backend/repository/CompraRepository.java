package myeshop.backend.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import myeshop.backend.model.Compra;

/**
 * Repositorio para la entidad intermedia Compra.
 * Gestiona las líneas de detalle de cada pedido.
 * @author Rafael Robles
 */
@Repository
public interface CompraRepository extends JpaRepository<Compra, Integer> {

    // Ver todas las compras de un cliente específico (usando su NIF)
    List<Compra> findByCliente_NifCif(String nifCif);

    // Ver compras entre dos fechas
    List<Compra> findByFechaCompraBetween(LocalDateTime inicio, LocalDateTime fin);

    // Ver compras por estado (PENDIENTE, COMPLETADO, etc.)
    List<Compra> findByEstadoIgnoreCase(String estado);
}
package myeshop.backend.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import myeshop.backend.model.ArticuloCompra;
import myeshop.backend.model.ArticuloCompra.ArticuloCompraId;

/**
 * Repositorio para la entidad intermedia ArticuloCompra.
 * Gestiona las líneas de detalle de cada pedido.
 * @author Rafael Robles
 */
@Repository
public interface ArticuloCompraRepository extends JpaRepository<ArticuloCompra, ArticuloCompraId> {

    // 1. Buscar todas las líneas de una compra específica usando el ID de la compra
    // Spring "bucea" dentro del ID compuesto para buscar por compraId
    List<ArticuloCompra> findById_CompraId(Integer compraId);

    // 2. Buscar todas las líneas donde aparezca un artículo concreto
    List<ArticuloCompra> findById_ArticuloId(Integer articuloId);

    // 3. Buscar líneas de compra que superen una cantidad de unidades (ej: pedidos grandes)
    List<ArticuloCompra> findByUnidadesGreaterThan(Integer cantidad);

    // 4. Ejemplo de consulta con JPQL para obtener el total vendido de un producto
    // Útil para estadísticas de la tienda
    @Query("SELECT SUM(ac.unidades) FROM ArticuloCompra ac WHERE ac.articulo.id = :articuloId")
    Long countTotalUnidadesVendidas(@Param("articuloId") Integer articuloId);

    // 5. Optimización: Buscar todas las líneas de una compra trayendo los datos del artículo 
    // para evitar el problema N+1 al mostrar el carrito
    @Query("SELECT ac FROM ArticuloCompra ac JOIN FETCH ac.articulo WHERE ac.id.compraId = :compraId")
    List<ArticuloCompra> findAllByCompraIdWithArticulo(@Param("compraId") Integer compraId);
}
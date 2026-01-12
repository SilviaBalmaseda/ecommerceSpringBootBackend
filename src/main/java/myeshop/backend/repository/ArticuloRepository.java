package myeshop.backend.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import myeshop.backend.model.Articulo;

@Repository
public interface ArticuloRepository extends JpaRepository<Articulo, Integer> {

    // Buscar artículos por nombre (ej: para un buscador en el front)
    List<Articulo> findByNombreContainingIgnoreCase(String nombre);

    // Buscar artículos con stock bajo (ej: menos de 5 unidades)
    List<Articulo> findByStockLessThan(Integer limite);
    
    // Consulta JPQL para traer artículos y sus líneas de compra de un golpe
    @Query("SELECT DISTINCT a FROM Articulo a LEFT JOIN FETCH a.compras")
    List<Articulo> findAllWithCompras();
}
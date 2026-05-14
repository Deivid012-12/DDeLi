// ProductoRepository.java
package co.edu.unbosque.ddeli.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.unbosque.ddeli.entity.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

	List<Producto> findByCategoriaIdCategoria(Long idCategoria);

	List<Producto> findByDisponibilidad(boolean disponibilidad);

	List<Producto> findByTipo(String tipo);

	List<Producto> findByCategoriaIdCategoriaAndDisponibilidad(Long idCategoria, boolean disponibilidad);

	List<Producto> findByNombreContainingIgnoreCase(String nombre);
}
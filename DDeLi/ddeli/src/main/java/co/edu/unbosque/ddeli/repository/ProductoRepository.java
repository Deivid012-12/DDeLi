package co.edu.unbosque.ddeli.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.edu.unbosque.ddeli.entity.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

	List<Producto> findByCategoriaIdCategoria(Long idCategoria);

	List<Producto> findByDisponibilidad(boolean disponibilidad);

	List<Producto> findByCategoriaIdCategoriaAndDisponibilidad(Long idCategoria, boolean disponibilidad);

	List<Producto> findByNombreContainingIgnoreCase(String nombre);

	Optional<Producto> findByNombre(String nombre);

	@Query("SELECT p FROM Producto p WHERE TYPE(p) = PostrePersonalizado")
	List<Producto> findAllPersonalizados();
}
package co.edu.unbosque.ddeli.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.edu.unbosque.ddeli.entity.ItemCarrito;

public interface ItemCarritoRepository extends JpaRepository<ItemCarrito, Long> {

	List<ItemCarrito> findByCarritoIdCarrito(Long idCarrito);

	Optional<ItemCarrito> findByCarritoIdCarritoAndProductoIdProducto(Long idCarrito, Long idProducto);

	void deleteByCarritoIdCarrito(Long idCarrito);

	@Query("""
			SELECT i FROM ItemCarrito i
			LEFT JOIN FETCH i.opciones
			WHERE i.carrito.idCarrito = :idCarrito
			""")
	List<ItemCarrito> findByCarritoIdCarritoWithOpciones(@Param("idCarrito") Long idCarrito);
}
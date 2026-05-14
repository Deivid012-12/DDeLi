// ItemCarritoRepository.java
package co.edu.unbosque.ddeli.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.unbosque.ddeli.entity.ItemCarrito;

@Repository
public interface ItemCarritoRepository extends JpaRepository<ItemCarrito, Long> {

	List<ItemCarrito> findByCarritoIdCarrito(Long idCarrito);

	Optional<ItemCarrito> findByCarritoIdCarritoAndProductoIdProducto(Long idCarrito, Long idProducto);

	void deleteByCarritoIdCarrito(Long idCarrito);
}
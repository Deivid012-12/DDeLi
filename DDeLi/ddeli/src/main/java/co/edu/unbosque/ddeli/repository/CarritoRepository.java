// CarritoRepository.java
package co.edu.unbosque.ddeli.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.unbosque.ddeli.entity.Carrito;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Long> {

	// Buscar carrito activo de un usuario
	Optional<Carrito> findByUsuarioIdUsuarioAndEstado(Long idUsuario, String estado);

	// Verificar si un usuario ya tiene carrito
	boolean existsByUsuarioIdUsuario(Long idUsuario);
}
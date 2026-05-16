package co.edu.unbosque.ddeli.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.ddeli.entity.Carrito;

public interface CarritoRepository extends JpaRepository<Carrito, Long> {

	Optional<Carrito> findByUsuarioIdUsuarioAndEstado(Long idUsuario, String estado);

	boolean existsByUsuarioIdUsuario(Long idUsuario);

	Optional<Carrito> findTopByUsuarioIdUsuarioOrderByIdCarritoDesc(Long idUsuario);



}
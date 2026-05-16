package co.edu.unbosque.ddeli.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.ddeli.entity.Direccion;

public interface DireccionRepository extends JpaRepository<Direccion, Long> {

	List<Direccion> findByUsuarioIdUsuario(Long idUsuario);

	boolean existsByUsuarioIdUsuarioAndCalleAndCiudad(Long idUsuario, String calle, String ciudad);
}
package co.edu.unbosque.ddeli.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.ddeli.entity.TipoPersonalizacion;

public interface TipoPersonalizacionRepository extends JpaRepository<TipoPersonalizacion, Long> {

	Optional<TipoPersonalizacion> findByNombre(String nombre);

	boolean existsByNombre(String nombre);
}
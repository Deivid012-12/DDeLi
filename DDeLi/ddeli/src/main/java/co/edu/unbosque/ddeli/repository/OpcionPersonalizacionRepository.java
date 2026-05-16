package co.edu.unbosque.ddeli.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.ddeli.entity.OpcionPersonalizacion;

public interface OpcionPersonalizacionRepository extends JpaRepository<OpcionPersonalizacion, Long> {

	List<OpcionPersonalizacion> findByTipoPersonalizacionIdTipo(Long idTipo);

	boolean existsByNombreAndTipoPersonalizacionIdTipo(String nombre, Long idTipo);
}
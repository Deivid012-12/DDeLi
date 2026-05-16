package co.edu.unbosque.ddeli.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import co.edu.unbosque.ddeli.entity.PlanSuscripcion;

public interface PlanSuscripcionRepository extends JpaRepository<PlanSuscripcion, Long> {

	Optional<PlanSuscripcion> findByNombre(String nombre);

	boolean existsByNombre(String nombre);
}
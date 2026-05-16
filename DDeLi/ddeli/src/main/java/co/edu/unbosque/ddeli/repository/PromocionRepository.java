package co.edu.unbosque.ddeli.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.unbosque.ddeli.entity.Promocion;

@Repository
public interface PromocionRepository extends JpaRepository<Promocion, Long> {

	boolean existsByNombre(String nombre);

	List<Promocion> findByFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(LocalDate fecha1, LocalDate fecha2);
}
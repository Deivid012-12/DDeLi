package co.edu.unbosque.ddeli.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.unbosque.ddeli.entity.Promocion;

@Repository
public interface PromocionRepository extends JpaRepository<Promocion, Long> {

}
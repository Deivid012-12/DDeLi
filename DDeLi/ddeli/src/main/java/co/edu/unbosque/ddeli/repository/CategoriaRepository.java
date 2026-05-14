// CategoriaRepository.java
package co.edu.unbosque.ddeli.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.unbosque.ddeli.entity.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

	Optional<Categoria> findByNombre(String nombre);

	boolean existsByNombre(String nombre);
}
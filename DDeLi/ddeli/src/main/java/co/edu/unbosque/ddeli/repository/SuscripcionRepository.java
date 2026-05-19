package co.edu.unbosque.ddeli.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.ddeli.entity.Suscripcion;

public interface SuscripcionRepository extends JpaRepository<Suscripcion, Long> {
    List<Suscripcion> findByUsuarioCorreo(String correo);
    Optional<Suscripcion> findByUsuarioCorreoAndEstado(String correo, String estado);
    boolean existsByUsuarioCorreoAndEstado(String correo, String estado);
}
package co.edu.unbosque.ddeli.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.ddeli.entity.Evento;

public interface EventoRepository extends JpaRepository<Evento, Long> {

	List<Evento> findByUsuarioIdUsuario(Long idUsuario);

	List<Evento> findByTipoEvento(String tipoEvento);

	boolean existsByUsuarioIdUsuarioAndTipoEventoAndFechaEvento(Long idUsuario, String tipoEvento,
			java.time.LocalDate fechaEvento);
}
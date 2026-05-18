package co.edu.unbosque.ddeli.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.ddeli.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	public Optional<Usuario> findByCorreo(String correo);

	public boolean existsByCorreo(String correo);

	public void deleteByCorreo(String correo);

	public Optional<Usuario> findByToken(int token);

	public List<Usuario> findAllByNombre(String nombre);

}
package co.edu.unbosque.ddeli.configuration;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import co.edu.unbosque.ddeli.entity.Usuario;
import co.edu.unbosque.ddeli.repository.UsuarioRepository;

@Configuration
public class LoadDatabase {

	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

	@Bean
	CommandLineRunner initDatabase(UsuarioRepository userRepo, PasswordEncoder passwordEncoder) {

		return args -> {

			Optional<Usuario> found = userRepo.findByNombre("admin");

			if (found.isPresent()) {

				log.info("El administrador ya existe");

			} else {

				Usuario adminUser = new Usuario();

				adminUser.setNombre("admin");
				adminUser.setCorreo("admin@ddeli.com");
				adminUser.setTelefono("3000000000");
				adminUser.setContrasenia(passwordEncoder.encode("1234567890"));
				adminUser.setRol(Usuario.Role.ADMIN);
				adminUser.setVerificado(true);

				userRepo.save(adminUser);

				log.info("Precargando usuario administrador");
			}

			Optional<Usuario> found2 = userRepo.findByNombre("cliente");

			if (found2.isPresent()) {

				log.info("El usuario cliente ya existe");

			} else {

				Usuario clienteUser = new Usuario();

				clienteUser.setNombre("cliente");
				clienteUser.setCorreo("cliente@ddeli.com");
				clienteUser.setTelefono("3111111111");
				clienteUser.setContrasenia(passwordEncoder.encode("1234567890"));
				clienteUser.setRol(Usuario.Role.CLIENTE);
				clienteUser.setVerificado(true);

				userRepo.save(clienteUser);

				log.info("Precargando usuario cliente");
			}
		};
	}
}
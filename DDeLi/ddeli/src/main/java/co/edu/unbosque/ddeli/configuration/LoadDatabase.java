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

import co.edu.unbosque.ddeli.entity.Categoria;
import co.edu.unbosque.ddeli.entity.Producto;
import co.edu.unbosque.ddeli.repository.CategoriaRepository;
import co.edu.unbosque.ddeli.repository.ProductoRepository;

@Configuration
public class LoadDatabase {

	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

	@Bean
	CommandLineRunner initDatabase(UsuarioRepository userRepo, CategoriaRepository categoriaRepo,
			ProductoRepository productoRepo, PasswordEncoder passwordEncoder) {

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
				Categoria brownies = new Categoria();
				brownies.setNombre("Brownies");
				brownies.setDescripcion("Brownies artesanales");

				Categoria cheesecakes = new Categoria();
				cheesecakes.setNombre("Cheesecakes");
				cheesecakes.setDescripcion("Cheesecakes cremosos");

				Categoria tortas = new Categoria();
				tortas.setNombre("Tortas");
				tortas.setDescripcion("Tortas especiales");
				if (categoriaRepo.count() == 0) {

					categoriaRepo.save(brownies);
					categoriaRepo.save(cheesecakes);
					categoriaRepo.save(tortas);

					log.info("Precargando categorías");
				}
				if (productoRepo.count() == 0) {

					Producto brownieChocolate = new Producto();

					brownieChocolate.setNombre("Brownie de Chocolate");
					brownieChocolate.setDescripcion("Brownie artesanal de chocolate");
					brownieChocolate.setPrecioBase(15000);
					brownieChocolate.setDisponibilidad(true);
					brownieChocolate.setTipo("POSTRE");
					brownieChocolate.setImagenURL("assets/brownie.jpg");
					brownieChocolate.setCategoria(brownies);

					productoRepo.save(brownieChocolate);

					Producto cheesecake = new Producto();

					cheesecake.setNombre("Cheesecake de Frutos Rojos");
					cheesecake.setDescripcion("Cheesecake cremoso con frutos rojos");
					cheesecake.setPrecioBase(25000);
					cheesecake.setDisponibilidad(true);
					cheesecake.setTipo("POSTRE");
					cheesecake.setImagenURL("assets/cheesecake.jpg");
					cheesecake.setCategoria(cheesecakes);

					productoRepo.save(cheesecake);

					Producto torta = new Producto();

					torta.setNombre("Torta Red Velvet");
					torta.setDescripcion("Torta suave Red Velvet");
					torta.setPrecioBase(45000);
					torta.setDisponibilidad(true);
					torta.setTipo("POSTRE");
					torta.setImagenURL("assets/redvelvet.jpg");
					torta.setCategoria(tortas);

					productoRepo.save(torta);

					log.info("Precargando productos");
				}
			}
		};
	}
}
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
import co.edu.unbosque.ddeli.entity.OpcionPersonalizacion;
import co.edu.unbosque.ddeli.entity.PlanSuscripcion;
import co.edu.unbosque.ddeli.entity.Producto;
import co.edu.unbosque.ddeli.entity.Promocion;
import co.edu.unbosque.ddeli.entity.TipoPersonalizacion;
import co.edu.unbosque.ddeli.repository.CategoriaRepository;
import co.edu.unbosque.ddeli.repository.OpcionPersonalizacionRepository;
import co.edu.unbosque.ddeli.repository.PlanSuscripcionRepository;
import co.edu.unbosque.ddeli.repository.ProductoRepository;
import co.edu.unbosque.ddeli.repository.PromocionRepository;
import co.edu.unbosque.ddeli.repository.TipoPersonalizacionRepository;

@Configuration
public class LoadDatabase {

	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

	@Bean
	CommandLineRunner initDatabase(UsuarioRepository userRepo, CategoriaRepository categoriaRepo,
			ProductoRepository productoRepo, PasswordEncoder passwordEncoder, TipoPersonalizacionRepository tipoRepo,
			OpcionPersonalizacionRepository opcionRepo, PlanSuscripcionRepository planRepo,
			PromocionRepository promocionRepo) {
		return args -> {

			Optional<Usuario> found = userRepo.findByCorreo("admin@ddeli.com");

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

			Optional<Usuario> found2 = userRepo.findByCorreo("cliente@ddeli.com");

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

					Producto tiramisu = new Producto();
					tiramisu.setNombre("Tiramisú Clásico");
					tiramisu.setDescripcion("Postre italiano con café y cacao");
					tiramisu.setPrecioBase(28000);
					tiramisu.setDisponibilidad(true);
					tiramisu.setTipo("POSTRE");
					tiramisu.setImagenURL("assets/tiramisu.jpg");
					tiramisu.setCategoria(cheesecakes);
					productoRepo.save(tiramisu);

					Producto flan = new Producto();
					flan.setNombre("Flan de Caramelo");
					flan.setDescripcion("Flan tradicional con caramelo casero");
					flan.setPrecioBase(12000);
					flan.setDisponibilidad(true);
					flan.setTipo("POSTRE");
					flan.setImagenURL("assets/flan.jpg");
					flan.setCategoria(cheesecakes);
					productoRepo.save(flan);

					Producto tresLeches = new Producto();
					tresLeches.setNombre("Torta Tres Leches");
					tresLeches.setDescripcion("Bizcocho suave con mezcla de tres leches");
					tresLeches.setPrecioBase(30000);
					tresLeches.setDisponibilidad(true);
					tresLeches.setTipo("POSTRE");
					tresLeches.setImagenURL("assets/tresleches.jpg");
					tresLeches.setCategoria(tortas);
					productoRepo.save(tresLeches);

					Producto galletaChoco = new Producto();
					galletaChoco.setNombre("Galletas con Chips de Chocolate");
					galletaChoco.setDescripcion("Galletas caseras con chispas de chocolate");
					galletaChoco.setPrecioBase(8000);
					galletaChoco.setDisponibilidad(true);
					galletaChoco.setTipo("POSTRE");
					galletaChoco.setImagenURL("assets/galletas.jpg");
					galletaChoco.setCategoria(brownies);
					productoRepo.save(galletaChoco);

					Producto mousseChocolate = new Producto();
					mousseChocolate.setNombre("Mousse de Chocolate");
					mousseChocolate.setDescripcion("Postre ligero y cremoso de chocolate");
					mousseChocolate.setPrecioBase(20000);
					mousseChocolate.setDisponibilidad(true);
					mousseChocolate.setTipo("POSTRE");
					mousseChocolate.setImagenURL("assets/mousse.jpg");
					mousseChocolate.setCategoria(cheesecakes);
					productoRepo.save(mousseChocolate);

					Producto pieLimon = new Producto();
					pieLimon.setNombre("Pie de Limón");
					pieLimon.setDescripcion("Postre cítrico con base crocante");
					pieLimon.setPrecioBase(22000);
					pieLimon.setDisponibilidad(true);
					pieLimon.setTipo("POSTRE");
					pieLimon.setImagenURL("assets/pielimon.jpg");
					pieLimon.setCategoria(tortas);
					productoRepo.save(pieLimon);

					Producto brownieNuez = new Producto();
					brownieNuez.setNombre("Brownie con Nueces");
					brownieNuez.setDescripcion("Brownie clásico con trozos de nuez");
					brownieNuez.setPrecioBase(17000);
					brownieNuez.setDisponibilidad(true);
					brownieNuez.setTipo("POSTRE");
					brownieNuez.setImagenURL("assets/browniemz.jpg");
					brownieNuez.setCategoria(brownies);
					productoRepo.save(brownieNuez);

					Producto cupcakeVainilla = new Producto();
					cupcakeVainilla.setNombre("Cupcake de Vainilla");
					cupcakeVainilla.setDescripcion("Cupcake suave con crema de vainilla");
					cupcakeVainilla.setPrecioBase(9000);
					cupcakeVainilla.setDisponibilidad(true);
					cupcakeVainilla.setTipo("POSTRE");
					cupcakeVainilla.setImagenURL("assets/cupcake.jpg");
					cupcakeVainilla.setCategoria(tortas);
					productoRepo.save(cupcakeVainilla);

					Producto alfajor = new Producto();
					alfajor.setNombre("Alfajor Artesanal");
					alfajor.setDescripcion("Dulce relleno de arequipe y coco");
					alfajor.setPrecioBase(6000);
					alfajor.setDisponibilidad(true);
					alfajor.setTipo("POSTRE");
					alfajor.setImagenURL("assets/alfajor.jpg");
					alfajor.setCategoria(brownies);
					productoRepo.save(alfajor);

					Producto arrozLeche = new Producto();
					arrozLeche.setNombre("Arroz con Leche");
					arrozLeche.setDescripcion("Postre tradicional con canela");
					arrozLeche.setPrecioBase(10000);
					arrozLeche.setDisponibilidad(true);
					arrozLeche.setTipo("POSTRE");
					arrozLeche.setImagenURL("assets/arrozleche.jpg");
					arrozLeche.setCategoria(cheesecakes);
					productoRepo.save(arrozLeche);

					Producto selvaNegra = new Producto();
					selvaNegra.setNombre("Torta Selva Negra");
					selvaNegra.setDescripcion("Torta de chocolate con cerezas");
					selvaNegra.setPrecioBase(38000);
					selvaNegra.setDisponibilidad(true);
					selvaNegra.setTipo("POSTRE");
					selvaNegra.setImagenURL("assets/selvanegra.jpg");
					selvaNegra.setCategoria(tortas);
					productoRepo.save(selvaNegra);

					log.info("Precargando 15 productos en total");

				}
			}

			if (tipoRepo.count() == 0) {
				TipoPersonalizacion sabor = new TipoPersonalizacion();
				sabor.setNombre("Sabor");
				tipoRepo.save(sabor);

				TipoPersonalizacion topping = new TipoPersonalizacion();
				topping.setNombre("Topping");
				tipoRepo.save(topping);

				TipoPersonalizacion decoracion = new TipoPersonalizacion();
				decoracion.setNombre("Decoración");
				tipoRepo.save(decoracion);

				TipoPersonalizacion tamano = new TipoPersonalizacion();
				tamano.setNombre("Tamaño");
				tipoRepo.save(tamano);

				OpcionPersonalizacion fresa = new OpcionPersonalizacion();
				fresa.setNombre("Fresa");
				fresa.setCostoAdicional(0);
				fresa.setTipoPersonalizacion(sabor);
				opcionRepo.save(fresa);

				OpcionPersonalizacion chocolate = new OpcionPersonalizacion();
				chocolate.setNombre("Chocolate");
				chocolate.setCostoAdicional(0);
				chocolate.setTipoPersonalizacion(sabor);
				opcionRepo.save(chocolate);

				OpcionPersonalizacion vainilla = new OpcionPersonalizacion();
				vainilla.setNombre("Vainilla");
				vainilla.setCostoAdicional(0);
				vainilla.setTipoPersonalizacion(sabor);
				opcionRepo.save(vainilla);

				OpcionPersonalizacion maracuya = new OpcionPersonalizacion();
				maracuya.setNombre("Maracuyá");
				maracuya.setCostoAdicional(2000);
				maracuya.setTipoPersonalizacion(sabor);
				opcionRepo.save(maracuya);

				OpcionPersonalizacion chispas = new OpcionPersonalizacion();
				chispas.setNombre("Chispas de chocolate");
				chispas.setCostoAdicional(1500);
				chispas.setTipoPersonalizacion(topping);
				opcionRepo.save(chispas);

				OpcionPersonalizacion frutas = new OpcionPersonalizacion();
				frutas.setNombre("Frutas");
				frutas.setCostoAdicional(2000);
				frutas.setTipoPersonalizacion(topping);
				opcionRepo.save(frutas);

				OpcionPersonalizacion caramelo = new OpcionPersonalizacion();
				caramelo.setNombre("Caramelo");
				caramelo.setCostoAdicional(1000);
				caramelo.setTipoPersonalizacion(topping);
				opcionRepo.save(caramelo);

				OpcionPersonalizacion oreo = new OpcionPersonalizacion();
				oreo.setNombre("Oreo");
				oreo.setCostoAdicional(1500);
				oreo.setTipoPersonalizacion(topping);
				opcionRepo.save(oreo);

				OpcionPersonalizacion sinDeco = new OpcionPersonalizacion();
				sinDeco.setNombre("Sin decoración");
				sinDeco.setCostoAdicional(0);
				sinDeco.setTipoPersonalizacion(decoracion);
				opcionRepo.save(sinDeco);

				OpcionPersonalizacion flores = new OpcionPersonalizacion();
				flores.setNombre("Flores");
				flores.setCostoAdicional(3000);
				flores.setTipoPersonalizacion(decoracion);
				opcionRepo.save(flores);

				OpcionPersonalizacion mensaje = new OpcionPersonalizacion();
				mensaje.setNombre("Mensaje personalizado");
				mensaje.setCostoAdicional(2000);
				mensaje.setTipoPersonalizacion(decoracion);
				opcionRepo.save(mensaje);

				OpcionPersonalizacion tematica = new OpcionPersonalizacion();
				tematica.setNombre("Temática especial");
				tematica.setCostoAdicional(5000);
				tematica.setTipoPersonalizacion(decoracion);
				opcionRepo.save(tematica);

				OpcionPersonalizacion pequeno = new OpcionPersonalizacion();
				pequeno.setNombre("Pequeño");
				pequeno.setCostoAdicional(0);
				pequeno.setTipoPersonalizacion(tamano);
				opcionRepo.save(pequeno);

				OpcionPersonalizacion mediano = new OpcionPersonalizacion();
				mediano.setNombre("Mediano");
				mediano.setCostoAdicional(5000);
				mediano.setTipoPersonalizacion(tamano);
				opcionRepo.save(mediano);

				OpcionPersonalizacion grande = new OpcionPersonalizacion();
				grande.setNombre("Grande");
				grande.setCostoAdicional(10000);
				grande.setTipoPersonalizacion(tamano);
				opcionRepo.save(grande);

				log.info("Precargando tipos y opciones de personalización");
			}
			if (productoRepo.findByTipo("PERSONALIZADO").isEmpty()) {
				Producto postPersonalizado = new Producto();
				postPersonalizado.setNombre("Postre Personalizado");
				postPersonalizado.setDescripcion("Postre artesanal personalizado a tu gusto");
				postPersonalizado.setPrecioBase(25000);
				postPersonalizado.setDisponibilidad(true);
				postPersonalizado.setTipo("PERSONALIZADO");
				postPersonalizado.setImagenURL("assets/postrePersonalizado.jpg");
				productoRepo.save(postPersonalizado);
				log.info("Precargando producto personalizado");
			}
			if (planRepo.count() == 0) {
				PlanSuscripcion basico = new PlanSuscripcion();
				basico.setNombre("Básico");
				basico.setPrecioMensual(15000);
				basico.setCostoAdicional(0);
				planRepo.save(basico);

				PlanSuscripcion estandar = new PlanSuscripcion();
				estandar.setNombre("Estándar");
				estandar.setPrecioMensual(25000);
				estandar.setCostoAdicional(5000);
				planRepo.save(estandar);

				PlanSuscripcion premium = new PlanSuscripcion();
				premium.setNombre("Premium");
				premium.setPrecioMensual(40000);
				premium.setCostoAdicional(0);
				planRepo.save(premium);

				log.info("Precargando planes de suscripción");
			}

			if (promocionRepo.count() == 0) {
				Promocion promoVerano = new Promocion();
				promoVerano.setNombre("Promo Verano");
				promoVerano.setPorcentajeDescuento(20);
				promoVerano.setFechaInicio(java.time.LocalDate.of(2026, 1, 1));
				promoVerano.setFechaFin(java.time.LocalDate.of(2026, 12, 31));
				promocionRepo.save(promoVerano);

				Promocion promoEspecial = new Promocion();
				promoEspecial.setNombre("Descuento Especial");
				promoEspecial.setPorcentajeDescuento(15);
				promoEspecial.setFechaInicio(java.time.LocalDate.of(2026, 5, 1));
				promoEspecial.setFechaFin(java.time.LocalDate.of(2026, 6, 30));
				promocionRepo.save(promoEspecial);

				Promocion promoPostres = new Promocion();
				promoPostres.setNombre("Festival de Postres");
				promoPostres.setPorcentajeDescuento(30);
				promoPostres.setFechaInicio(java.time.LocalDate.of(2026, 5, 15));
				promoPostres.setFechaFin(java.time.LocalDate.of(2026, 5, 31));
				promocionRepo.save(promoPostres);

				log.info("Precargando promociones");
			}

		};

	}
}
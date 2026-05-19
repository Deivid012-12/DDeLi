package co.edu.unbosque.ddeli.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import co.edu.unbosque.ddeli.entity.Categoria;
import co.edu.unbosque.ddeli.entity.OpcionPersonalizacion;
import co.edu.unbosque.ddeli.entity.PlanSuscripcion;
import co.edu.unbosque.ddeli.entity.PostrePersonalizado;
import co.edu.unbosque.ddeli.entity.PostrePredeterminado;
import co.edu.unbosque.ddeli.entity.Promocion;
import co.edu.unbosque.ddeli.entity.TipoPersonalizacion;
import co.edu.unbosque.ddeli.entity.Usuario;
import co.edu.unbosque.ddeli.repository.CategoriaRepository;
import co.edu.unbosque.ddeli.repository.OpcionPersonalizacionRepository;
import co.edu.unbosque.ddeli.repository.PlanSuscripcionRepository;
import co.edu.unbosque.ddeli.repository.ProductoRepository;
import co.edu.unbosque.ddeli.repository.PromocionRepository;
import co.edu.unbosque.ddeli.repository.TipoPersonalizacionRepository;
import co.edu.unbosque.ddeli.repository.UsuarioRepository;

@Configuration
public class LoadDatabase {

	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

	@Bean
	CommandLineRunner initDatabase(UsuarioRepository userRepo, CategoriaRepository categoriaRepo,
			ProductoRepository productoRepo, PasswordEncoder passwordEncoder, TipoPersonalizacionRepository tipoRepo,
			OpcionPersonalizacionRepository opcionRepo, PlanSuscripcionRepository planRepo,
			PromocionRepository promocionRepo) {

		return args -> {

			if (userRepo.findByCorreo("admin@ddeli.com").isEmpty()) {
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

			if (userRepo.findByCorreo("cliente@ddeli.com").isEmpty()) {
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

			Categoria brownies = categoriaRepo.findByNombre("Brownies").orElseGet(() -> {
				Categoria c = new Categoria();
				c.setNombre("Brownies");
				c.setDescripcion("Brownies artesanales");
				log.info("Precargando categoría: Brownies");
				return categoriaRepo.save(c);
			});

			Categoria cheesecakes = categoriaRepo.findByNombre("Cheesecakes").orElseGet(() -> {
				Categoria c = new Categoria();
				c.setNombre("Cheesecakes");
				c.setDescripcion("Cheesecakes cremosos");
				log.info("Precargando categoría: Cheesecakes");
				return categoriaRepo.save(c);
			});

			Categoria tortas = categoriaRepo.findByNombre("Tortas").orElseGet(() -> {
				Categoria c = new Categoria();
				c.setNombre("Tortas");
				c.setDescripcion("Tortas especiales");
				log.info("Precargando categoría: Tortas");
				return categoriaRepo.save(c);
			});

			Categoria postres = categoriaRepo.findByNombre("Postres").orElseGet(() -> {
				Categoria c = new Categoria();
				c.setNombre("Postres");
				c.setDescripcion("Postres variados");
				log.info("Precargando categoría: Postres");
				return categoriaRepo.save(c);
			});

			if (productoRepo.count() == 0) {

				PostrePredeterminado brownieChocolate = new PostrePredeterminado();
				brownieChocolate.setNombre("Brownie de Chocolate");
				brownieChocolate.setDescripcion("Brownie artesanal de chocolate");
				brownieChocolate.setPrecioBase(15000);
				brownieChocolate.setEstiloBase("Chocolate clásico");
				brownieChocolate.setImagenURL("assets/brownie.jpg");
				brownieChocolate.setDisponibilidad(true);
				brownieChocolate.setCategoria(brownies);
				productoRepo.save(brownieChocolate);

				PostrePredeterminado cheesecake = new PostrePredeterminado();
				cheesecake.setNombre("Cheesecake de Frutos Rojos");
				cheesecake.setDescripcion("Cheesecake cremoso con frutos rojos");
				cheesecake.setPrecioBase(25000);
				cheesecake.setEstiloBase("Frutos rojos");
				cheesecake.setImagenURL("assets/cheesecake.jpg");
				cheesecake.setDisponibilidad(true);
				cheesecake.setCategoria(cheesecakes);
				productoRepo.save(cheesecake);

				PostrePredeterminado torta = new PostrePredeterminado();
				torta.setNombre("Torta Red Velvet");
				torta.setDescripcion("Torta suave Red Velvet");
				torta.setPrecioBase(45000);
				torta.setEstiloBase("Red Velvet");
				torta.setImagenURL("assets/redvelvet.jpg");
				torta.setDisponibilidad(true);
				torta.setCategoria(tortas);
				productoRepo.save(torta);

				PostrePredeterminado tiramisu = new PostrePredeterminado();
				tiramisu.setNombre("Tiramisú Clásico");
				tiramisu.setDescripcion("Postre italiano con café y cacao");
				tiramisu.setPrecioBase(28000);
				tiramisu.setEstiloBase("Italiano clásico");
				tiramisu.setImagenURL("assets/tiramisu.jpg");
				tiramisu.setDisponibilidad(true);
				tiramisu.setCategoria(postres);
				productoRepo.save(tiramisu);

				PostrePredeterminado flan = new PostrePredeterminado();
				flan.setNombre("Flan de Caramelo");
				flan.setDescripcion("Flan tradicional con caramelo casero");
				flan.setPrecioBase(12000);
				flan.setEstiloBase("Caramelo tradicional");
				flan.setImagenURL("assets/flan.jpg");
				flan.setDisponibilidad(true);
				flan.setCategoria(postres);
				productoRepo.save(flan);

				PostrePredeterminado tresLeches = new PostrePredeterminado();
				tresLeches.setNombre("Torta Tres Leches");
				tresLeches.setDescripcion("Bizcocho suave con mezcla de tres leches");
				tresLeches.setPrecioBase(30000);
				tresLeches.setEstiloBase("Tres leches clásico");
				tresLeches.setImagenURL("assets/tresleches.jpg");
				tresLeches.setDisponibilidad(true);
				tresLeches.setCategoria(tortas);
				productoRepo.save(tresLeches);

				PostrePredeterminado cupcakeVainilla = new PostrePredeterminado();
				cupcakeVainilla.setNombre("Cupcake de Vainilla");
				cupcakeVainilla.setDescripcion("Cupcake suave con crema de vainilla");
				cupcakeVainilla.setPrecioBase(9000);
				cupcakeVainilla.setEstiloBase("Vainilla clásica");
				cupcakeVainilla.setImagenURL("assets/cupcake.jpg");
				cupcakeVainilla.setDisponibilidad(true);
				cupcakeVainilla.setCategoria(tortas);
				productoRepo.save(cupcakeVainilla);

				PostrePredeterminado alfajor = new PostrePredeterminado();
				alfajor.setNombre("Alfajor Artesanal");
				alfajor.setDescripcion("Alfajor relleno de arequipe con coco rallado");
				alfajor.setPrecioBase(8000);
				alfajor.setEstiloBase("Arequipe clásico");
				alfajor.setImagenURL("assets/alfajor.jpg");
				alfajor.setDisponibilidad(true);
				alfajor.setCategoria(postres);
				productoRepo.save(alfajor);

				PostrePredeterminado arrozLeche = new PostrePredeterminado();
				arrozLeche.setNombre("Arroz con Leche");
				arrozLeche.setDescripcion("Arroz con leche cremoso con canela");
				arrozLeche.setPrecioBase(10000);
				arrozLeche.setEstiloBase("Canela tradicional");
				arrozLeche.setImagenURL("assets/arrozleche.jpg");
				arrozLeche.setDisponibilidad(true);
				arrozLeche.setCategoria(postres);
				productoRepo.save(arrozLeche);

				PostrePredeterminado brownieMz = new PostrePredeterminado();
				brownieMz.setNombre("Brownie de Manzana");
				brownieMz.setDescripcion("Brownie húmedo con trozos de manzana caramelizada");
				brownieMz.setPrecioBase(16000);
				brownieMz.setEstiloBase("Manzana caramelizada");
				brownieMz.setImagenURL("assets/browniemz.jpg");
				brownieMz.setDisponibilidad(true);
				brownieMz.setCategoria(brownies);
				productoRepo.save(brownieMz);

				PostrePredeterminado galletas = new PostrePredeterminado();
				galletas.setNombre("Galletas Artesanales");
				galletas.setDescripcion("Galletas crujientes con chips de chocolate");
				galletas.setPrecioBase(7000);
				galletas.setEstiloBase("Chips de chocolate");
				galletas.setImagenURL("assets/galletas.jpg");
				galletas.setDisponibilidad(true);
				galletas.setCategoria(postres);
				productoRepo.save(galletas);

				PostrePredeterminado mousse = new PostrePredeterminado();
				mousse.setNombre("Mousse de Chocolate");
				mousse.setDescripcion("Mousse esponjoso de chocolate oscuro");
				mousse.setPrecioBase(18000);
				mousse.setEstiloBase("Chocolate oscuro");
				mousse.setImagenURL("assets/mousse.jpg");
				mousse.setDisponibilidad(true);
				mousse.setCategoria(postres);
				productoRepo.save(mousse);

				PostrePredeterminado pieLimon = new PostrePredeterminado();
				pieLimon.setNombre("Pie de Limón");
				pieLimon.setDescripcion("Pie de limón con merengue tostado");
				pieLimon.setPrecioBase(22000);
				pieLimon.setEstiloBase("Merengue tostado");
				pieLimon.setImagenURL("assets/pielimon.jpg");
				pieLimon.setDisponibilidad(true);
				pieLimon.setCategoria(tortas);
				productoRepo.save(pieLimon);

				PostrePredeterminado selvaNegra = new PostrePredeterminado();
				selvaNegra.setNombre("Torta Selva Negra");
				selvaNegra.setDescripcion("Torta alemana de chocolate con cerezas y crema");
				selvaNegra.setPrecioBase(48000);
				selvaNegra.setEstiloBase("Cereza y crema");
				selvaNegra.setImagenURL("assets/selvanegra.jpg");
				selvaNegra.setDisponibilidad(true);
				selvaNegra.setCategoria(tortas);
				productoRepo.save(selvaNegra);

				PostrePredeterminado wuafle = new PostrePredeterminado();
				wuafle.setNombre("Wafle Artesanal");
				wuafle.setDescripcion("Wafle crujiente con frutos rojos y miel");
				wuafle.setPrecioBase(14000);
				wuafle.setEstiloBase("Frutos rojos y miel");
				wuafle.setImagenURL("assets/wuafle.png");
				wuafle.setDisponibilidad(true);
				wuafle.setCategoria(postres);
				productoRepo.save(wuafle);

				log.info("Precargando productos predeterminados");
			}

			boolean existePersonalizado = productoRepo.findAll().stream()
					.anyMatch(p -> p instanceof PostrePersonalizado);

			if (!existePersonalizado) {
				PostrePersonalizado personalizado = new PostrePersonalizado();
				personalizado.setNombre("Postre Personalizado");
				personalizado.setDescripcion("Postre artesanal personalizado a tu gusto");
				personalizado.setPrecioBase(25000);
				personalizado.setMaximoOpciones(5);
				personalizado.setImagenURL("assets/postrePersonalizado.jpg");
				personalizado.setDisponibilidad(true);
				productoRepo.save(personalizado);
				log.info("Precargando producto personalizado");
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
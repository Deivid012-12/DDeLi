package co.edu.unbosque.ddeli.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.ddeli.dto.CarritoDTO;
import co.edu.unbosque.ddeli.dto.ItemCarritoDTO;
import co.edu.unbosque.ddeli.service.CarritoService;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/carrito")
@Tag(name = "Carrito")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class CarritoController {

	@Autowired
	private CarritoService carritoService;

	@GetMapping("/verCarrito")
	public ResponseEntity<CarritoDTO> obtenerCarrito(Authentication authentication) {

		String correo = authentication.getName();

		CarritoDTO carrito = carritoService.obtenerOCrearCarritoPorCorreo(correo);

		return ResponseEntity.ok(carrito);
	}

	@GetMapping("/obtenerItems/{idCarrito}")
	public ResponseEntity<List<ItemCarritoDTO>> obtenerItems(@PathVariable Long idCarrito) {

		List<ItemCarritoDTO> items = carritoService.obtenerItems(idCarrito);

		return ResponseEntity.ok(items);
	}

	@PostMapping("/agregarProducto/{idCarrito}")
	public ResponseEntity<String> agregarProducto(@PathVariable Long idCarrito, @RequestParam Long idProducto,
			@RequestParam int cantidad, @RequestParam(required = false) List<Long> idOpciones) {

		int resultado = carritoService.agregarProducto(idCarrito, idProducto, cantidad, idOpciones);

		if (resultado == 0) {
			return ResponseEntity.status(HttpStatus.CREATED).body("Producto agregado al carrito");
		}
		if (resultado == 1) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Carrito no encontrado");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado");
	}

	@PutMapping("/actualizarCantidad/{idItem}")
	public ResponseEntity<String> actualizarCantidad(@PathVariable Long idItem, @RequestParam int cantidad) {

		int resultado = carritoService.actualizarCantidad(idItem, cantidad);

		if (resultado == 0) {
			return ResponseEntity.ok("Cantidad actualizada");
		}

		if (resultado == 1) {
			return ResponseEntity.badRequest().body("La cantidad debe ser mayor a 0");
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item no encontrado");
	}

	@DeleteMapping("/eliminarProducto/{idItem}")
	public ResponseEntity<String> eliminarProducto(@PathVariable Long idItem) {

		int resultado = carritoService.eliminarProducto(idItem);

		if (resultado == 0) {
			return ResponseEntity.ok("Producto eliminado");
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item no encontrado");
	}

	@DeleteMapping("/vaciarCarrito/{idCarrito}")
	public ResponseEntity<String> vaciarCarrito(@PathVariable Long idCarrito) {

		int resultado = carritoService.vaciarCarrito(idCarrito);

		if (resultado == 0) {
			return ResponseEntity.ok("Carrito vaciado");
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Carrito no encontrado");
	}

	@GetMapping("/generarTotal/{idCarrito}")
	public ResponseEntity<Double> obtenerTotal(@PathVariable Long idCarrito) {

		double total = carritoService.calcularTotal(idCarrito);

		return ResponseEntity.ok(total);
	}
}
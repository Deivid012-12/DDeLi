// CarritoController.java
package co.edu.unbosque.ddeli.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.ddeli.entity.Carrito;
import co.edu.unbosque.ddeli.entity.ItemCarrito;
import co.edu.unbosque.ddeli.service.CarritoService;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/carrito")
@Tag(name = "Carrito")
@CrossOrigin(origins = "http://localhost:4200")
public class CarritoController {

	@Autowired
	private CarritoService carritoService;

	@GetMapping("/usuario/{idUsuario}")
	public ResponseEntity<Carrito> obtenerCarrito(@PathVariable Long idUsuario) {
		Carrito carrito = carritoService.obtenerOCrearCarrito(idUsuario);
		return ResponseEntity.ok(carrito);
	}

	@GetMapping("/{idCarrito}/items")
	public ResponseEntity<List<ItemCarrito>> obtenerItems(@PathVariable Long idCarrito) {
		return ResponseEntity.ok(carritoService.obtenerItems(idCarrito));
	}

	@PostMapping("/{idCarrito}/agregar")
	public ResponseEntity<ItemCarrito> agregarProducto(@PathVariable Long idCarrito, @RequestParam Long idProducto,
			@RequestParam int cantidad) {
		ItemCarrito item = carritoService.agregarProducto(idCarrito, idProducto, cantidad);
		return ResponseEntity.status(HttpStatus.CREATED).body(item);
	}

	@PutMapping("/item/{idItem}")
	public ResponseEntity<ItemCarrito> actualizarCantidad(@PathVariable Long idItem, @RequestParam int cantidad) {
		ItemCarrito item = carritoService.actualizarCantidad(idItem, cantidad);
		return ResponseEntity.ok(item);
	}

	@DeleteMapping("/item/{idItem}")
	public ResponseEntity<Void> eliminarProducto(@PathVariable Long idItem) {
		carritoService.eliminarProducto(idItem);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{idCarrito}/vaciar")
	public ResponseEntity<Void> vaciarCarrito(@PathVariable Long idCarrito) {
		carritoService.vaciarCarrito(idCarrito);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{idCarrito}/total")
	public ResponseEntity<Double> obtenerTotal(@PathVariable Long idCarrito) {
		return ResponseEntity.ok(carritoService.calcularTotal(idCarrito));
	}
}
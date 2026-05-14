package co.edu.unbosque.ddeli.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.ddeli.dto.ProductoDTO;
import co.edu.unbosque.ddeli.entity.Producto;
import co.edu.unbosque.ddeli.service.ProductoService;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/productos")
@Tag(name = "Productos(Postres)")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductoController {

	@Autowired
	private ProductoService productoService;

	@GetMapping
	public ResponseEntity<List<ProductoDTO>> obtenerTodos() {
		return ResponseEntity.ok(productoService.obtenerTodos());
	}

	@GetMapping("/disponibles")
	public ResponseEntity<List<ProductoDTO>> obtenerDisponibles() {
		return ResponseEntity.ok(productoService.obtenerDisponibles());
	}

	@GetMapping("/tipo/{tipo}")
	public ResponseEntity<List<ProductoDTO>> obtenerPorTipo(@PathVariable String tipo) {
		return ResponseEntity.ok(productoService.obtenerPorTipo(tipo));
	}

	@GetMapping("/categoria/{idCategoria}")
	public ResponseEntity<List<ProductoDTO>> obtenerPorCategoria(@PathVariable Long idCategoria) {
		return ResponseEntity.ok(productoService.obtenerPorCategoria(idCategoria));
	}

	@GetMapping("/buscar")
	public ResponseEntity<List<ProductoDTO>> buscarPorNombre(@RequestParam String nombre) {
		return ResponseEntity.ok(productoService.buscarPorNombre(nombre));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProductoDTO> obtenerPorId(@PathVariable Long id) {
		return productoService.obtenerPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<ProductoDTO> crear(@RequestBody Producto producto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(productoService.crear(producto));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ProductoDTO> actualizar(@PathVariable Long id, @RequestBody Producto producto) {
		return ResponseEntity.ok(productoService.actualizar(id, producto));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable Long id) {
		productoService.eliminar(id);
		return ResponseEntity.noContent().build();
	}
}
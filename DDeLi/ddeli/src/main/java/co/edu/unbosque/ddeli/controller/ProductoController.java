package co.edu.unbosque.ddeli.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.ddeli.dto.ProductoDTO;
import co.edu.unbosque.ddeli.service.ProductoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/producto")
@CrossOrigin(origins = { "http://localhost:4200" })
@Transactional
@Tag(name = "Gestión de Productos", description = "Endpoints para administrar productos")
@SecurityRequirement(name = "bearerAuth")
public class ProductoController {

	@Autowired
	private ProductoService productoSer;

	@GetMapping(path = "/getall")
	public ResponseEntity<List<ProductoDTO>> getAll() {
		List<ProductoDTO> productos = productoSer.getAll();
		if (productos.isEmpty()) {
			return new ResponseEntity<>(productos, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(productos, HttpStatus.OK);
		}
	}

	@GetMapping(path = "/disponibles")
	public ResponseEntity<List<ProductoDTO>> obtenerDisponibles() {
		List<ProductoDTO> productos = productoSer.obtenerDisponibles();
		if (productos.isEmpty()) {
			return new ResponseEntity<>(productos, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(productos, HttpStatus.OK);
		}
	}

	@GetMapping(path = "/obtenerPorTipo/{tipo}")
	public ResponseEntity<List<ProductoDTO>> obtenerPorTipo(@PathVariable String tipo) {
		List<ProductoDTO> productos = productoSer.obtenerPorTipo(tipo);
		if (productos.isEmpty()) {
			return new ResponseEntity<>(productos, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(productos, HttpStatus.OK);
		}
	}

	@GetMapping(path = "/obtenerPorCategoria/{idCategoria}")
	public ResponseEntity<List<ProductoDTO>> obtenerPorCategoria(@PathVariable Long idCategoria) {
		List<ProductoDTO> productos = productoSer.obtenerPorCategoria(idCategoria);
		if (productos.isEmpty()) {
			return new ResponseEntity<>(productos, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(productos, HttpStatus.OK);
		}
	}

	@GetMapping(path = "/buscar")
	public ResponseEntity<List<ProductoDTO>> buscarPorNombre(@RequestParam String nombre) {
		List<ProductoDTO> productos = productoSer.buscarPorNombre(nombre);
		if (productos.isEmpty()) {
			return new ResponseEntity<>(productos, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(productos, HttpStatus.OK);
		}
	}

	@GetMapping(path = "/getbyid/{id}")
	public ResponseEntity<ProductoDTO> getById(@PathVariable Long id) {
		return productoSer.obtenerPorId(id).map(producto -> new ResponseEntity<>(producto, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@PostMapping(path = "/crear")
	public ResponseEntity<String> crear(@RequestBody ProductoDTO newProducto) {
		int status = productoSer.create(newProducto);

		if (status == 0) {
			return new ResponseEntity<>("Producto creado con éxito", HttpStatus.CREATED);
		} else if (status == 2) {
			return new ResponseEntity<>("Categoría no encontrada", HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>("Error al crear el producto", HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(path = "/createjson", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createNewWithJSON(@RequestBody ProductoDTO newProducto) {
		int status = productoSer.create(newProducto);

		if (status == 0) {
			return new ResponseEntity<>("Producto creado correctamente", HttpStatus.CREATED);
		} else if (status == 2) {
			return new ResponseEntity<>("Categoría no encontrada", HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>("Error al crear el producto", HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping(path = "/actualizar/{id}")
	public ResponseEntity<String> actualizar(@PathVariable Long id, @RequestBody ProductoDTO newProducto) {
		int status = productoSer.updateByID(id, newProducto);

		if (status == 0) {
			return new ResponseEntity<>("Producto actualizado correctamente", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Producto no encontrado", HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping(path = "/deletebyid/{id}")
	public ResponseEntity<String> deleteById(@PathVariable Long id) {
		int status = productoSer.deleteByID(id);

		if (status == 0) {
			return new ResponseEntity<>("Producto eliminado correctamente", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Producto no encontrado", HttpStatus.NOT_FOUND);
		}
	}
}
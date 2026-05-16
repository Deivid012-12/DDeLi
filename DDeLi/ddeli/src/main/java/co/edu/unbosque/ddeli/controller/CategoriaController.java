package co.edu.unbosque.ddeli.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.ddeli.dto.CategoriaDTO;
import co.edu.unbosque.ddeli.service.CategoriaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/categoria")
@CrossOrigin(origins = { "http://localhost:4200" })
@Transactional
@Tag(name = "Gestión de Categorías", description = "Endpoints para administrar categorías")
@SecurityRequirement(name = "bearerAuth")
public class CategoriaController {

	@Autowired
	private CategoriaService categoriaSer;

	@GetMapping(path = "/getall")
	public ResponseEntity<List<CategoriaDTO>> getAll() {
		List<CategoriaDTO> categorias = categoriaSer.getAll();
		if (categorias.isEmpty()) {
			return new ResponseEntity<>(categorias, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(categorias, HttpStatus.OK);
		}
	}

	@GetMapping(path = "/getbyid/{id}")
	public ResponseEntity<CategoriaDTO> getById(@PathVariable Long id) {
		return categoriaSer.obtenerPorId(id).map(categoria -> new ResponseEntity<>(categoria, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@PostMapping(path = "/crear")
	public ResponseEntity<String> crear(@RequestBody CategoriaDTO newCategoria) {
		int status = categoriaSer.create(newCategoria);

		if (status == 0) {
			return new ResponseEntity<>("Categoría creada con éxito", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("Categoría ya existente", HttpStatus.NOT_ACCEPTABLE);
		}
	}

	@PostMapping(path = "/createjson", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createNewWithJSON(@RequestBody CategoriaDTO newCategoria) {
		int status = categoriaSer.create(newCategoria);

		if (status == 0) {
			return new ResponseEntity<>("Categoría creada correctamente", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("Categoría ya existente", HttpStatus.NOT_ACCEPTABLE);
		}
	}

	@PutMapping(path = "/actualizar/{id}")
	public ResponseEntity<String> actualizar(@PathVariable Long id, @RequestBody CategoriaDTO newCategoria) {
		int status = categoriaSer.updateByID(id, newCategoria);

		if (status == 0) {
			return new ResponseEntity<>("Categoría actualizada correctamente", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Categoría no encontrada", HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping(path = "/deletebyid/{id}")
	public ResponseEntity<String> deleteById(@PathVariable Long id) {
		int status = categoriaSer.deleteByID(id);

		if (status == 0) {
			return new ResponseEntity<>("Categoría eliminada correctamente", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Categoría no encontrada", HttpStatus.NOT_FOUND);
		}
	}
}
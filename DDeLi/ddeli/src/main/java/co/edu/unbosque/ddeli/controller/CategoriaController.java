
package co.edu.unbosque.ddeli.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.ddeli.dto.CategoriaDTO;
import co.edu.unbosque.ddeli.service.CategoriaService;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/categorias")
@Tag(name = "Categoria")
@CrossOrigin(origins = "http://localhost:4200")
public class CategoriaController {

	@Autowired
	private CategoriaService categoriaService;

	@GetMapping
	public ResponseEntity<List<CategoriaDTO>> obtenerTodas() {
		return ResponseEntity.ok(categoriaService.obtenerTodas());
	}

	@GetMapping("/{id}")
	public ResponseEntity<CategoriaDTO> obtenerPorId(@PathVariable Long id) {
		return categoriaService.obtenerPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<CategoriaDTO> crear(@RequestBody CategoriaDTO dto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.crear(dto));
	}

	@PutMapping("/{id}")
	public ResponseEntity<CategoriaDTO> actualizar(@PathVariable Long id, @RequestBody CategoriaDTO dto) {
		return ResponseEntity.ok(categoriaService.actualizar(id, dto));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable Long id) {
		categoriaService.eliminar(id);
		return ResponseEntity.noContent().build();
	}
}
package co.edu.unbosque.ddeli.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.ddeli.dto.PromocionConProductoDTO;
import co.edu.unbosque.ddeli.dto.PromocionDTO;
import co.edu.unbosque.ddeli.service.PromocionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/promocion")
@CrossOrigin(origins = { "http://localhost:4200" })
@Transactional
@Tag(name = "Gestión de Promociones", description = "Endpoints para administrar promociones")
@SecurityRequirement(name = "bearerAuth")
public class PromocionController {

	@Autowired
	private PromocionService promocionSer;

	@GetMapping(path = "/getall")
	public ResponseEntity<List<PromocionDTO>> getAll() {
		List<PromocionDTO> promociones = promocionSer.getAll();
		if (promociones.isEmpty()) {
			return new ResponseEntity<>(promociones, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(promociones, HttpStatus.OK);
		}
	}

	@GetMapping(path = "/vigentes-con-productos")
	public ResponseEntity<List<PromocionConProductoDTO>> vigentesConProductos() {
		List<PromocionConProductoDTO> resultado = promocionSer.obtenerVigentesConProductos();
		if (resultado.isEmpty()) {
			return new ResponseEntity<>(resultado, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(resultado, HttpStatus.OK);
	}

	@PostMapping(path = "/crear")
	public ResponseEntity<String> crear(@RequestBody PromocionDTO newPromocion) {
		int status = promocionSer.create(newPromocion);

		if (status == 0) {
			return new ResponseEntity<>("Promoción creada con éxito", HttpStatus.CREATED);
		} else if (status == 1) {
			return new ResponseEntity<>("Ya existe una promoción con ese nombre", HttpStatus.NOT_ACCEPTABLE);
		} else {
			return new ResponseEntity<>("Error al crear la promoción", HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(path = "/createjson", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createNewWithJSON(@RequestBody PromocionDTO newPromocion) {
		int status = promocionSer.create(newPromocion);

		if (status == 0) {
			return new ResponseEntity<>("Promoción creada correctamente", HttpStatus.CREATED);
		} else if (status == 1) {
			return new ResponseEntity<>("Ya existe una promoción con ese nombre", HttpStatus.NOT_ACCEPTABLE);
		} else {
			return new ResponseEntity<>("Error al crear la promoción", HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping(path = "/actualizar/{id}")
	public ResponseEntity<String> actualizar(@PathVariable Long id, @RequestBody PromocionDTO newPromocion) {
		int status = promocionSer.updateByID(id, newPromocion);

		if (status == 0) {
			return new ResponseEntity<>("Promoción actualizada correctamente", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Promoción no encontrada", HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping(path = "/deletebyid/{id}")
	public ResponseEntity<String> deleteById(@PathVariable Long id) {
		int status = promocionSer.deleteByID(id);

		if (status == 0) {
			return new ResponseEntity<>("Promoción eliminada correctamente", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Promoción no encontrada", HttpStatus.NOT_FOUND);
		}
	}
}
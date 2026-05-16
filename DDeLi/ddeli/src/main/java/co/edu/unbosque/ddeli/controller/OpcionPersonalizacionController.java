package co.edu.unbosque.ddeli.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.ddeli.dto.OpcionPersonalizacionDTO;
import co.edu.unbosque.ddeli.service.OpcionPersonalizacionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/opcion")
@CrossOrigin(origins = { "http://localhost:4200" })
@Transactional
@Tag(name = "Gestión de Opciones de Personalización", description = "Endpoints para administrar opciones de personalización")
@SecurityRequirement(name = "bearerAuth")
public class OpcionPersonalizacionController {

	@Autowired
	private OpcionPersonalizacionService opcionSer;

	@GetMapping(path = "/getall")
	public ResponseEntity<List<OpcionPersonalizacionDTO>> getAll() {
		List<OpcionPersonalizacionDTO> opciones = opcionSer.getAll();
		if (opciones.isEmpty()) {
			return new ResponseEntity<>(opciones, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(opciones, HttpStatus.OK);
		}
	}

	@GetMapping(path = "/obtenerPorTipo/{idTipo}")
	public ResponseEntity<List<OpcionPersonalizacionDTO>> obtenerPorTipo(@PathVariable Long idTipo) {
		List<OpcionPersonalizacionDTO> opciones = opcionSer.obtenerPorTipo(idTipo);
		if (opciones.isEmpty()) {
			return new ResponseEntity<>(opciones, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(opciones, HttpStatus.OK);
		}
	}

	@PostMapping(path = "/crear")
	public ResponseEntity<String> crear(@RequestBody OpcionPersonalizacionDTO newOpcion) {
		int status = opcionSer.create(newOpcion);

		if (status == 0) {
			return new ResponseEntity<>("Opción creada con éxito", HttpStatus.CREATED);
		} else if (status == 1) {
			return new ResponseEntity<>("Tipo de personalización no encontrado", HttpStatus.NOT_FOUND);
		} else if (status == 2) {
			return new ResponseEntity<>("Ya existe esa opción en este tipo", HttpStatus.NOT_ACCEPTABLE);
		} else {
			return new ResponseEntity<>("Error al crear la opción", HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(path = "/createjson", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createNewWithJSON(@RequestBody OpcionPersonalizacionDTO newOpcion) {
		int status = opcionSer.create(newOpcion);

		if (status == 0) {
			return new ResponseEntity<>("Opción creada correctamente", HttpStatus.CREATED);
		} else if (status == 1) {
			return new ResponseEntity<>("Tipo de personalización no encontrado", HttpStatus.NOT_ACCEPTABLE);
		} else if (status == 2) {
			return new ResponseEntity<>("Ya existe esa opción en este tipo", HttpStatus.NOT_ACCEPTABLE);
		} else {
			return new ResponseEntity<>("Error al crear la opción", HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping(path = "/actualizar/{id}")
	public ResponseEntity<String> actualizar(@PathVariable Long id, @RequestBody OpcionPersonalizacionDTO newOpcion) {
		int status = opcionSer.updateByID(id, newOpcion);

		if (status == 0) {
			return new ResponseEntity<>("Opción actualizada correctamente", HttpStatus.OK);
		} else if (status == 1) {
			return new ResponseEntity<>("Opción no encontrada", HttpStatus.NOT_FOUND);
		} else if (status == 2) {
			return new ResponseEntity<>("Tipo de personalización no encontrado", HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>("Error al actualizar la opción", HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping(path = "/deletebyid/{id}")
	public ResponseEntity<String> deleteById(@PathVariable Long id) {
		int status = opcionSer.deleteByID(id);

		if (status == 0) {
			return new ResponseEntity<>("Opción eliminada correctamente", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Opción no encontrada", HttpStatus.NOT_FOUND);
		}
	}
}
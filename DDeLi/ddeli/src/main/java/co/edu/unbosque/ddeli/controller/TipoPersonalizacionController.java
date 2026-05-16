package co.edu.unbosque.ddeli.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.ddeli.dto.TipoPersonalizacionDTO;
import co.edu.unbosque.ddeli.service.TipoPersonalizacionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/tipo-personalizacion")
@CrossOrigin(origins = { "http://localhost:4200" })
@Transactional
@Tag(name = "Gestión de Tipos de Personalización", description = "Endpoints para administrar tipos de personalización")
@SecurityRequirement(name = "bearerAuth")
public class TipoPersonalizacionController {

	@Autowired
	private TipoPersonalizacionService tipoSer;

	
	@GetMapping(path = "/getall")
	public ResponseEntity<List<TipoPersonalizacionDTO>> getAll() {
		List<TipoPersonalizacionDTO> tipos = tipoSer.getAll();
		if (tipos.isEmpty()) {
			return new ResponseEntity<>(tipos, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(tipos, HttpStatus.OK);
		}
	}


	@PostMapping(path = "/crear")
	public ResponseEntity<String> crear(@RequestBody TipoPersonalizacionDTO newTipo) {
		int status = tipoSer.create(newTipo);

		if (status == 0) {
			return new ResponseEntity<>("Tipo creado con éxito", HttpStatus.CREATED);
		} else if (status == 1) {
			return new ResponseEntity<>("Ya existe un tipo con ese nombre", HttpStatus.NOT_ACCEPTABLE);
		} else {
			return new ResponseEntity<>("Error al crear el tipo", HttpStatus.BAD_REQUEST);
		}
	}


	@PostMapping(path = "/createjson", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createNewWithJSON(@RequestBody TipoPersonalizacionDTO newTipo) {
		int status = tipoSer.create(newTipo);

		if (status == 0) {
			return new ResponseEntity<>("Tipo creado correctamente", HttpStatus.CREATED);
		} else if (status == 1) {
			return new ResponseEntity<>("Ya existe un tipo con ese nombre", HttpStatus.NOT_ACCEPTABLE);
		} else {
			return new ResponseEntity<>("Error al crear el tipo", HttpStatus.BAD_REQUEST);
		}
	}

	
	@PutMapping(path = "/actualizar/{id}")
	public ResponseEntity<String> actualizar(@PathVariable Long id, @RequestBody TipoPersonalizacionDTO newTipo) {
		int status = tipoSer.updateByID(id, newTipo);

		if (status == 0) {
			return new ResponseEntity<>("Tipo actualizado correctamente", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Tipo no encontrado", HttpStatus.NOT_FOUND);
		}
	}


	@DeleteMapping(path = "/deletebyid/{id}")
	public ResponseEntity<String> deleteById(@PathVariable Long id) {
		int status = tipoSer.deleteByID(id);

		if (status == 0) {
			return new ResponseEntity<>("Tipo eliminado correctamente", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Tipo no encontrado", HttpStatus.NOT_FOUND);
		}
	}
}
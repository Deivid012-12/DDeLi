package co.edu.unbosque.ddeli.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.ddeli.dto.DireccionDTO;
import co.edu.unbosque.ddeli.service.DireccionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/direccion")
@CrossOrigin(origins = { "http://localhost:4200" })
@Transactional
@Tag(name = "Gestión de Direcciones", description = "Endpoints para administrar direcciones")
@SecurityRequirement(name = "bearerAuth")
public class DireccionController {

	@Autowired
	private DireccionService direccionSer;

	@GetMapping(path = "/getall")
	public ResponseEntity<List<DireccionDTO>> getAll() {
		List<DireccionDTO> direcciones = direccionSer.getAll();
		if (direcciones.isEmpty()) {
			return new ResponseEntity<>(direcciones, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(direcciones, HttpStatus.OK);
		}
	}

	@PostMapping(path = "/crearMia")
	public ResponseEntity<String> crearMiDireccion(Authentication authentication,
			@RequestBody DireccionDTO newDireccion) {
		try {
			Long idDireccion = direccionSer.crearPorCorreo(authentication.getName(), newDireccion);
			return new ResponseEntity<>("Dirección creada - ID: " + idDireccion, HttpStatus.CREATED);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(path = "/obtenerPorUsuario/{idUsuario}")
	public ResponseEntity<List<DireccionDTO>> obtenerPorUsuario(@PathVariable Long idUsuario) {
		List<DireccionDTO> direcciones = direccionSer.obtenerPorUsuario(idUsuario);
		if (direcciones.isEmpty()) {
			return new ResponseEntity<>(direcciones, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(direcciones, HttpStatus.OK);
		}
	}

	@GetMapping(path = "/getbyid/{id}")
	public ResponseEntity<DireccionDTO> getById(@PathVariable Long id) {
		return direccionSer.obtenerPorId(id).map(usuario -> new ResponseEntity<>(usuario, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@PostMapping(path = "/crear")
	public ResponseEntity<String> crear(@RequestBody DireccionDTO newDireccion) {
		int status = direccionSer.create(newDireccion);

		if (status == 0) {
			return new ResponseEntity<>("Dirección creada con éxito", HttpStatus.CREATED);
		} else if (status == 1) {
			return new ResponseEntity<>("Dirección ya existente para este usuario", HttpStatus.NOT_ACCEPTABLE);
		} else if (status == 2) {
			return new ResponseEntity<>("Usuario no encontrado", HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>("Error al crear la dirección", HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(path = "/createjson", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createNewWithJSON(@RequestBody DireccionDTO newDireccion) {
		int status = direccionSer.create(newDireccion);

		if (status == 0) {
			return new ResponseEntity<>("Dirección creada correctamente", HttpStatus.CREATED);
		} else if (status == 1) {
			return new ResponseEntity<>("Dirección ya existente", HttpStatus.NOT_ACCEPTABLE);
		} else if (status == 2) {
			return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_ACCEPTABLE);
		} else {
			return new ResponseEntity<>("Error al crear la dirección", HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping(path = "/actualizar/{id}")
	public ResponseEntity<String> actualizar(@PathVariable Long id, @RequestBody DireccionDTO newDireccion) {
		int status = direccionSer.updateByID(id, newDireccion);

		if (status == 0) {
			return new ResponseEntity<>("Dirección actualizada correctamente", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Dirección no encontrada", HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping(path = "/deletebyid/{id}")
	public ResponseEntity<String> deleteById(@PathVariable Long id) {
		int status = direccionSer.deleteByID(id);

		if (status == 0) {
			return new ResponseEntity<>("Dirección eliminada correctamente", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Dirección no encontrada", HttpStatus.NOT_FOUND);
		}
	}
}
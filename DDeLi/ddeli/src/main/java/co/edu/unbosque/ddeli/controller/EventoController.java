package co.edu.unbosque.ddeli.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.ddeli.dto.EventoDTO;
import co.edu.unbosque.ddeli.service.EventoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/evento")
@CrossOrigin(origins = { "http://localhost:4200" })
@Transactional
@Tag(name = "Gestión de Eventos", description = "Endpoints para administrar eventos")
@SecurityRequirement(name = "bearerAuth")
public class EventoController {

	@Autowired
	private EventoService eventoSer;

	@GetMapping(path = "/getall")
	public ResponseEntity<List<EventoDTO>> getAll() {
		List<EventoDTO> eventos = eventoSer.getAll();
		if (eventos.isEmpty()) {
			return new ResponseEntity<>(eventos, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(eventos, HttpStatus.OK);
		}
	}

	@GetMapping("/obtenerPorUsuario")
	public ResponseEntity<List<EventoDTO>> obtenerPorUsuario(Authentication authentication) {

		String correo = authentication.getName();
		List<EventoDTO> eventos = eventoSer.obtenerPorCorreo(correo);

		if (eventos.isEmpty()) {
			return new ResponseEntity<>(eventos, HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(eventos, HttpStatus.OK);
	}

	@GetMapping(path = "/obtenerPorTipo/{tipoEvento}")
	public ResponseEntity<List<EventoDTO>> obtenerPorTipo(@PathVariable String tipoEvento) {
		List<EventoDTO> eventos = eventoSer.obtenerPorTipo(tipoEvento);
		if (eventos.isEmpty()) {
			return new ResponseEntity<>(eventos, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(eventos, HttpStatus.OK);
		}
	}

	@PostMapping("/crear")
	public ResponseEntity<String> crear(@RequestBody EventoDTO newEvento, Authentication authentication) {

		String correo = authentication.getName();

		int status = eventoSer.crearParaUsuario(correo, newEvento);

		if (status == 0) {
			return new ResponseEntity<>("Evento creado con éxito", HttpStatus.CREATED);
		}

		return new ResponseEntity<>("Error al crear evento", HttpStatus.BAD_REQUEST);
	}

	@PostMapping("/crearMio")
	public ResponseEntity<String> crearMio(@RequestBody EventoDTO newEvento, Authentication authentication) {
		String correo = authentication.getName();
		int status = eventoSer.crearParaUsuario(correo, newEvento);

		if (status == 0)
			return new ResponseEntity<>("Evento creado con éxito", HttpStatus.CREATED);
		else if (status == 2)
			return new ResponseEntity<>("Ya existe ese evento en esa fecha", HttpStatus.NOT_ACCEPTABLE);
		else
			return new ResponseEntity<>("Error al crear el evento", HttpStatus.BAD_REQUEST);
	}

	// Y mis eventos:
	@GetMapping("/misEventos")
	public ResponseEntity<List<EventoDTO>> misEventos(Authentication authentication) {
		String correo = authentication.getName();
		List<EventoDTO> eventos = eventoSer.obtenerPorCorreo(correo);
		if (eventos.isEmpty())
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<>(eventos, HttpStatus.OK);
	}

	@PostMapping(path = "/createjson", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createNewWithJSON(@RequestBody EventoDTO newEvento) {
		int status = eventoSer.create(newEvento);

		if (status == 0) {
			return new ResponseEntity<>("Evento creado correctamente", HttpStatus.CREATED);
		} else if (status == 1) {
			return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_ACCEPTABLE);
		} else if (status == 2) {
			return new ResponseEntity<>("Ya existe ese evento para este usuario en esa fecha",
					HttpStatus.NOT_ACCEPTABLE);
		} else {
			return new ResponseEntity<>("Error al crear el evento", HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping(path = "/actualizar/{id}")
	public ResponseEntity<String> actualizar(@PathVariable Long id, @RequestBody EventoDTO newEvento) {
		int status = eventoSer.updateByID(id, newEvento);

		if (status == 0) {
			return new ResponseEntity<>("Evento actualizado correctamente", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Evento no encontrado", HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping(path = "/deletebyid/{id}")
	public ResponseEntity<String> deleteById(@PathVariable Long id) {
		int status = eventoSer.deleteByID(id);

		if (status == 0) {
			return new ResponseEntity<>("Evento eliminado correctamente", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Evento no encontrado", HttpStatus.NOT_FOUND);
		}
	}
}
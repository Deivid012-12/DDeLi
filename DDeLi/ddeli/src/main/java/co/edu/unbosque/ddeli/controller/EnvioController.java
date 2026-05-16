package co.edu.unbosque.ddeli.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.ddeli.dto.EnvioDTO;
import co.edu.unbosque.ddeli.service.EnvioService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/envio")
@CrossOrigin(origins = { "http://localhost:4200" })
@Transactional
@Tag(name = "Gestión de Envíos", description = "Endpoints para administrar envíos")
@SecurityRequirement(name = "bearerAuth")
public class EnvioController {

	@Autowired
	private EnvioService envioSer;

	@GetMapping(path = "/getall")
	public ResponseEntity<List<EnvioDTO>> getAll() {
		List<EnvioDTO> envios = envioSer.getAll();
		if (envios.isEmpty()) {
			return new ResponseEntity<>(envios, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(envios, HttpStatus.OK);
		}
	}

	@GetMapping(path = "/obtenerPorPedido/{idPedido}")
	public ResponseEntity<EnvioDTO> obtenerPorPedido(@PathVariable Long idPedido) {
		return envioSer.obtenerPorPedido(idPedido).map(envio -> new ResponseEntity<>(envio, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@GetMapping(path = "/obtenerPorEstado/{estado}")
	public ResponseEntity<List<EnvioDTO>> obtenerPorEstado(@PathVariable String estado) {
		List<EnvioDTO> envios = envioSer.obtenerPorEstado(estado);
		if (envios.isEmpty()) {
			return new ResponseEntity<>(envios, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(envios, HttpStatus.OK);
		}
	}

	@GetMapping(path = "/obtenerPorTipo/{tipoEntrega}")
	public ResponseEntity<List<EnvioDTO>> obtenerPorTipo(@PathVariable String tipoEntrega) {
		List<EnvioDTO> envios = envioSer.obtenerPorTipoEntrega(tipoEntrega);
		if (envios.isEmpty()) {
			return new ResponseEntity<>(envios, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(envios, HttpStatus.OK);
		}
	}

	@PostMapping(path = "/crear")
	public ResponseEntity<String> crear(@RequestBody EnvioDTO newEnvio) {
		int status = envioSer.create(newEnvio);

		if (status == 0) {
			return new ResponseEntity<>("Envío creado con éxito", HttpStatus.CREATED);
		} else if (status == 1) {
			return new ResponseEntity<>("Pedido no encontrado", HttpStatus.NOT_FOUND);
		} else if (status == 2) {
			return new ResponseEntity<>("Ya existe un envío para este pedido", HttpStatus.NOT_ACCEPTABLE);
		} else {
			return new ResponseEntity<>("Error al crear el envío", HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(path = "/createjson", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createNewWithJSON(@RequestBody EnvioDTO newEnvio) {
		int status = envioSer.create(newEnvio);

		if (status == 0) {
			return new ResponseEntity<>("Envío creado correctamente", HttpStatus.CREATED);
		} else if (status == 1) {
			return new ResponseEntity<>("Pedido no encontrado", HttpStatus.NOT_ACCEPTABLE);
		} else if (status == 2) {
			return new ResponseEntity<>("Ya existe un envío para este pedido", HttpStatus.NOT_ACCEPTABLE);
		} else {
			return new ResponseEntity<>("Error al crear el envío", HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping(path = "/actualizar/{id}")
	public ResponseEntity<String> actualizar(@PathVariable Long id, @RequestBody EnvioDTO newEnvio) {
		int status = envioSer.updateByID(id, newEnvio);

		if (status == 0) {
			return new ResponseEntity<>("Envío actualizado correctamente", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Envío no encontrado", HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping(path = "/deletebyid/{id}")
	public ResponseEntity<String> deleteById(@PathVariable Long id) {
		int status = envioSer.deleteByID(id);

		if (status == 0) {
			return new ResponseEntity<>("Envío eliminado correctamente", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Envío no encontrado", HttpStatus.NOT_FOUND);
		}
	}
}
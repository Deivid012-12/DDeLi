package co.edu.unbosque.ddeli.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.ddeli.dto.PagoDTO;
import co.edu.unbosque.ddeli.service.PagoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/pago")
@CrossOrigin(origins = { "http://localhost:4200" })
@Transactional
@Tag(name = "Gestión de Pagos", description = "Endpoints para administrar pagos")
@SecurityRequirement(name = "bearerAuth")
public class PagoController {

	@Autowired
	private PagoService pagoSer;

	@GetMapping(path = "/getall")
	public ResponseEntity<List<PagoDTO>> getAll() {
		List<PagoDTO> pagos = pagoSer.getAll();
		if (pagos.isEmpty()) {
			return new ResponseEntity<>(pagos, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(pagos, HttpStatus.OK);
		}
	}

	@GetMapping(path = "/obtenerPorPedido/{idPedido}")
	public ResponseEntity<PagoDTO> obtenerPorPedido(@PathVariable Long idPedido) {
		return pagoSer.obtenerPorPedido(idPedido).map(pago -> new ResponseEntity<>(pago, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@GetMapping(path = "/obtenerPorEstado/{estado}")
	public ResponseEntity<List<PagoDTO>> obtenerPorEstado(@PathVariable String estado) {
		List<PagoDTO> pagos = pagoSer.obtenerPorEstado(estado);
		if (pagos.isEmpty()) {
			return new ResponseEntity<>(pagos, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(pagos, HttpStatus.OK);
		}
	}

	@GetMapping(path = "/obtenerPorMetodo/{metodoPago}")
	public ResponseEntity<List<PagoDTO>> obtenerPorMetodo(@PathVariable String metodoPago) {
		List<PagoDTO> pagos = pagoSer.obtenerPorMetodo(metodoPago);
		if (pagos.isEmpty()) {
			return new ResponseEntity<>(pagos, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(pagos, HttpStatus.OK);
		}
	}

	@PostMapping(path = "/crear")
	public ResponseEntity<String> crear(@RequestBody PagoDTO newPago) {
		int status = pagoSer.create(newPago);

		if (status == 0) {
			return new ResponseEntity<>("Pago creado con éxito", HttpStatus.CREATED);
		} else if (status == 1) {
			return new ResponseEntity<>("Pedido no encontrado", HttpStatus.NOT_FOUND);
		} else if (status == 2) {
			return new ResponseEntity<>("Ya existe un pago para este pedido", HttpStatus.NOT_ACCEPTABLE);
		} else {
			return new ResponseEntity<>("Error al crear el pago", HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(path = "/createjson", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createNewWithJSON(@RequestBody PagoDTO newPago) {
		int status = pagoSer.create(newPago);

		if (status == 0) {
			return new ResponseEntity<>("Pago creado correctamente", HttpStatus.CREATED);
		} else if (status == 1) {
			return new ResponseEntity<>("Pedido no encontrado", HttpStatus.NOT_ACCEPTABLE);
		} else if (status == 2) {
			return new ResponseEntity<>("Ya existe un pago para este pedido", HttpStatus.NOT_ACCEPTABLE);
		} else {
			return new ResponseEntity<>("Error al crear el pago", HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping(path = "/actualizar/{id}")
	public ResponseEntity<String> actualizar(@PathVariable Long id, @RequestBody PagoDTO newPago) {
		int status = pagoSer.updateByID(id, newPago);

		if (status == 0) {
			return new ResponseEntity<>("Pago actualizado correctamente", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Pago no encontrado", HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping(path = "/deletebyid/{id}")
	public ResponseEntity<String> deleteById(@PathVariable Long id) {
		int status = pagoSer.deleteByID(id);

		if (status == 0) {
			return new ResponseEntity<>("Pago eliminado correctamente", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Pago no encontrado", HttpStatus.NOT_FOUND);
		}
	}
}
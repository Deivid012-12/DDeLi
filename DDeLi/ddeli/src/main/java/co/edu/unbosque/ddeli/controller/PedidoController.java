package co.edu.unbosque.ddeli.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.ddeli.dto.PedidoDTO;
import co.edu.unbosque.ddeli.service.PedidoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/pedido")
@CrossOrigin(origins = { "http://localhost:4200" })
@Transactional
@Tag(name = "Gestión de Pedidos", description = "Endpoints para administrar pedidos")
@SecurityRequirement(name = "bearerAuth")
public class PedidoController {

	@Autowired
	private PedidoService pedidoSer;

	@GetMapping(path = "/getall")
	public ResponseEntity<List<PedidoDTO>> getAll() {
		List<PedidoDTO> pedidos = pedidoSer.getAll();
		if (pedidos.isEmpty()) {
			return new ResponseEntity<>(pedidos, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(pedidos, HttpStatus.OK);
		}
	}

	@GetMapping(path = "/obtenerPorUsuario/{idUsuario}")
	public ResponseEntity<List<PedidoDTO>> obtenerPorUsuario(@PathVariable Long idUsuario) {
		List<PedidoDTO> pedidos = pedidoSer.obtenerPorUsuario(idUsuario);
		if (pedidos.isEmpty()) {
			return new ResponseEntity<>(pedidos, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(pedidos, HttpStatus.OK);
		}
	}

	@GetMapping(path = "/getbyid/{id}")
	public ResponseEntity<PedidoDTO> getById(@PathVariable Long id) {
		return pedidoSer.obtenerPorId(id).map(pedido -> new ResponseEntity<>(pedido, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@PostMapping(path = "/confirmarCarrito/{idCarrito}")
	public ResponseEntity<String> confirmarCarrito(@PathVariable Long idCarrito,
			@RequestParam(required = false) Long idPromocion) {
		try {
			PedidoDTO pedido = pedidoSer.confirmarCarrito(idCarrito, idPromocion);
			return new ResponseEntity<>("Pedido creado con éxito - ID: " + pedido.getIdPedido(), HttpStatus.CREATED);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(path = "/confirmarjson/{idCarrito}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> confirmarCarritoJSON(@PathVariable Long idCarrito,
			@RequestParam(required = false) Long idPromocion) {
		try {
			PedidoDTO pedido = pedidoSer.confirmarCarrito(idCarrito, idPromocion);
			return new ResponseEntity<>("Pedido creado correctamente - ID: " + pedido.getIdPedido(),
					HttpStatus.CREATED);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(path = "/crear")
	public ResponseEntity<String> crear(@RequestBody PedidoDTO newPedido) {
		int status = pedidoSer.create(newPedido);
		if (status == 0) {
			return new ResponseEntity<>("Pedido creado con éxito", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("Error al crear el pedido", HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping(path = "/actualizar/{id}")
	public ResponseEntity<String> actualizar(@PathVariable Long id, @RequestBody PedidoDTO newPedido) {
		int status = pedidoSer.updateByID(id, newPedido);
		if (status == 0) {
			return new ResponseEntity<>("Pedido actualizado correctamente", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Pedido no encontrado", HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping(path = "/deletebyid/{id}")
	public ResponseEntity<String> deleteById(@PathVariable Long id) {
		int status = pedidoSer.deleteByID(id);
		if (status == 0) {
			return new ResponseEntity<>("Pedido eliminado correctamente", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Pedido no encontrado", HttpStatus.NOT_FOUND);
		}
	}
}
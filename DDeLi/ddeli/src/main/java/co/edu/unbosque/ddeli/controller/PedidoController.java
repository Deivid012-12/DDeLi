// PedidoController.java
package co.edu.unbosque.ddeli.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.ddeli.dto.PedidoDTO;
import co.edu.unbosque.ddeli.service.PedidoService;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/pedidos")
@Tag(name = "Pedido")
@CrossOrigin(origins = "http://localhost:4200")
public class PedidoController {

	@Autowired
	private PedidoService pedidoService;

	@PostMapping("/confirmar/{idCarrito}")
	public ResponseEntity<PedidoDTO> confirmarCarrito(@PathVariable Long idCarrito,
			@RequestParam(required = false) Long idPromocion) {
		PedidoDTO pedido = pedidoService.confirmarCarrito(idCarrito, idPromocion);
		return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
	}

	@GetMapping("/usuario/{idUsuario}")
	public ResponseEntity<List<PedidoDTO>> obtenerPorUsuario(@PathVariable Long idUsuario) {
		return ResponseEntity.ok(pedidoService.obtenerPorUsuario(idUsuario));
	}

	@GetMapping("/{id}")
	public ResponseEntity<PedidoDTO> obtenerPorId(@PathVariable Long id) {
		return pedidoService.obtenerPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@GetMapping
	public ResponseEntity<List<PedidoDTO>> obtenerTodos() {
		return ResponseEntity.ok(pedidoService.obtenerTodos());
	}
}
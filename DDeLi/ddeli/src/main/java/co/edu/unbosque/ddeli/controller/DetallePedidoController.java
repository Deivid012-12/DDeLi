package co.edu.unbosque.ddeli.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.ddeli.dto.DetallePedidoDTO;
import co.edu.unbosque.ddeli.service.DetallePedidoService;

@RestController
@RequestMapping("/api/detalles")
@CrossOrigin(origins = "http://localhost:4200")
public class DetallePedidoController {

	@Autowired
	private DetallePedidoService detallePedidoService;

	@GetMapping("/obtenerPorPedido")
	public ResponseEntity<List<DetallePedidoDTO>> obtenerPorPedido(@PathVariable Long idPedido) {
		return ResponseEntity.ok(detallePedidoService.obtenerPorPedido(idPedido));
	}

	@GetMapping("/getByID")
	public ResponseEntity<DetallePedidoDTO> getByID(@PathVariable Long id) {
		return detallePedidoService.obtenerPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/obtenerProducto")
	public ResponseEntity<List<DetallePedidoDTO>> obtenerPorProducto(@PathVariable Long idProducto) {
		return ResponseEntity.ok(detallePedidoService.obtenerPorProducto(idProducto));
	}
}
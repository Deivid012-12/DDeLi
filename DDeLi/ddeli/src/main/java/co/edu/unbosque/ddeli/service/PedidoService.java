// PedidoService.java
package co.edu.unbosque.ddeli.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.ddeli.dto.DetallePedidoDTO;
import co.edu.unbosque.ddeli.dto.PedidoDTO;
import co.edu.unbosque.ddeli.entity.Carrito;
import co.edu.unbosque.ddeli.entity.DetallePedido;
import co.edu.unbosque.ddeli.entity.ItemCarrito;
import co.edu.unbosque.ddeli.entity.Pedido;
import co.edu.unbosque.ddeli.entity.Promocion;
import co.edu.unbosque.ddeli.repository.CarritoRepository;
import co.edu.unbosque.ddeli.repository.PedidoRepository;
import co.edu.unbosque.ddeli.repository.PromocionRepository;

import jakarta.transaction.Transactional;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private CarritoRepository carritoRepository;

	@Autowired
	private PromocionRepository promocionRepository;

	@Autowired
	private CarritoService carritoService;

	public List<PedidoDTO> obtenerPorUsuario(Long idUsuario) {
		return pedidoRepository.findByUsuarioIdUsuario(idUsuario).stream().map(this::toDTO)
				.collect(Collectors.toList());
	}

	public Optional<PedidoDTO> obtenerPorId(Long id) {
		return pedidoRepository.findById(id).map(this::toDTO);
	}

	public List<PedidoDTO> obtenerTodos() {
		return pedidoRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
	}

	// Convertir DetallePedido a DTO
	private DetallePedidoDTO toDetalleDTO(DetallePedido d) {
		DetallePedidoDTO dto = new DetallePedidoDTO();
		dto.setIdDetalle(d.getIdDetalle());
		dto.setNombreProducto(d.getProducto().getNombre());
		dto.setCantidad(d.getCantidad());
		dto.setPrecioUnitario(d.getPrecioUnitario());
		dto.setSubtotal(d.getSubtotal());
		return dto;
	}

	// Convertir Pedido a DTO
	private PedidoDTO toDTO(Pedido p) {
		PedidoDTO dto = new PedidoDTO();
		dto.setIdPedido(p.getIdPedido());
		dto.setFechaPedido(p.getFechaPedido());
		dto.setValorTotal(p.getValorTotal());
		dto.setIdUsuario(p.getUsuario().getIdUsuario());
		dto.setNombreUsuario(p.getUsuario().getNombre());

		if (p.getEvento() != null) {
			dto.setIdEvento(p.getEvento().getIdEvento());
		}
		if (p.getPromocion() != null) {
			dto.setIdPromocion(p.getPromocion().getIdPromocion());
			dto.setNombrePromocion(p.getPromocion().getNombre());
		}
		if (p.getDetalles() != null) {
			dto.setDetalles(p.getDetalles().stream().map(this::toDetalleDTO).collect(Collectors.toList()));
		}
		return dto;
	}

	@Transactional
	public PedidoDTO confirmarCarrito(Long idCarrito, Long idPromocion) {

		Carrito carrito = carritoRepository.findById(idCarrito)
				.orElseThrow(() -> new RuntimeException("Carrito no encontrado: " + idCarrito));

		if (carrito.getItems() == null || carrito.getItems().isEmpty()) {
			throw new RuntimeException("El carrito está vacío");
		}

		Pedido pedido = new Pedido();
		pedido.setUsuario(carrito.getUsuario());
		pedido.setFechaPedido(LocalDate.now());

		if (idPromocion != null) {
			Promocion promo = promocionRepository.findById(idPromocion)
					.orElseThrow(() -> new RuntimeException("Promoción no encontrada: " + idPromocion));
			pedido.setPromocion(promo);
		}

		List<DetallePedido> detalles = carrito.getItems().stream().map(item -> {
			DetallePedido detalle = new DetallePedido();
			detalle.setPedido(pedido);
			detalle.setProducto(item.getProducto());
			detalle.setCantidad(item.getCantidad());
			detalle.setPrecioUnitario(item.getPrecioUnitario());
			detalle.setSubtotal(item.getSubtotal());
			return detalle;
		}).collect(Collectors.toList());

		double total = detalles.stream().mapToDouble(DetallePedido::getSubtotal).sum();

		if (pedido.getPromocion() != null) {
			double descuento = pedido.getPromocion().getPorcentajeDescuento() / 100.0;
			total = total - (total * descuento);
		}

		pedido.setDetalles(detalles);
		pedido.setValorTotal(total);

		Pedido guardado = pedidoRepository.save(pedido);

		carritoService.confirmarCarrito(idCarrito);

		return toDTO(guardado);
	}

}
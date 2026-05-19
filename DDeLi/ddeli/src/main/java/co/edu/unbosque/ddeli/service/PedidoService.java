package co.edu.unbosque.ddeli.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.ddeli.dto.DetallePedidoDTO;
import co.edu.unbosque.ddeli.dto.PedidoDTO;
import co.edu.unbosque.ddeli.entity.Carrito;
import co.edu.unbosque.ddeli.entity.DetallePedido;
import co.edu.unbosque.ddeli.entity.Direccion;
import co.edu.unbosque.ddeli.entity.Evento;
import co.edu.unbosque.ddeli.entity.Pedido;
import co.edu.unbosque.ddeli.entity.Promocion;
import co.edu.unbosque.ddeli.repository.CarritoRepository;
import co.edu.unbosque.ddeli.repository.EventoRepository;
import co.edu.unbosque.ddeli.repository.PedidoRepository;
import co.edu.unbosque.ddeli.repository.PromocionRepository;

import jakarta.transaction.Transactional;

@Service
public class PedidoService implements CRUDOperation<PedidoDTO> {

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private CarritoRepository carritoRepository;

	@Autowired
	private PromocionRepository promocionRepository;

	@Autowired
	private CarritoService carritoService;

	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private EventoRepository eventoRepository;

	public PedidoService() {

	}

	@Override
	public int create(PedidoDTO newData) {
		Pedido pedido = modelMapper.map(newData, Pedido.class);
		pedidoRepository.save(pedido);
		return 0;
	}

	@Override
	public List<PedidoDTO> getAll() {
		List<Pedido> entityList = pedidoRepository.findAll();
		List<PedidoDTO> dtoList = new ArrayList<>();

		entityList.forEach(entity -> {
			PedidoDTO dto = mapToDTO(entity);
			dtoList.add(dto);
		});

		return dtoList;
	}

	@Override
	public int deleteByID(Long id) {
		if (pedidoRepository.findById(id).isPresent()) {
			pedidoRepository.deleteById(id);
			return 0;
		}
		return 1;
	}

	@Override
	public int updateByID(Long id, PedidoDTO newData) {
		Optional<Pedido> found = pedidoRepository.findById(id);

		if (found.isPresent()) {
			Pedido temp = found.get();
			temp.setValorTotal(newData.getValorTotal());
			temp.setFechaPedido(newData.getFechaPedido());
			pedidoRepository.save(temp);
			return 0;
		}
		return 1;
	}

	public boolean exist(Long id) {
		return pedidoRepository.existsById(id);
	}

	public long count() {
		return pedidoRepository.count();
	}

	public int deleteById(Long id) {
		Optional<Pedido> found = pedidoRepository.findById(id);
		if (found.isPresent()) {
			pedidoRepository.delete(found.get());
			return 0;
		}
		return 1;
	}

	public List<PedidoDTO> obtenerPorUsuario(Long idUsuario) {
		return pedidoRepository.findByUsuarioIdUsuario(idUsuario).stream().map(this::mapToDTO)
				.collect(Collectors.toList());
	}

	public Optional<PedidoDTO> obtenerPorId(Long id) {
		return pedidoRepository.findById(id).map(this::mapToDTO);
	}

	@Transactional
	public PedidoDTO confirmarCarrito(Long idCarrito, Long idPromocion, Long idEvento) {
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

		if (idEvento != null) {
			Evento evento = eventoRepository.findById(idEvento)
					.orElseThrow(() -> new RuntimeException("Evento no encontrado: " + idEvento));
			pedido.setEvento(evento);
		}

		List<DetallePedido> detalles = carrito.getItems().stream().map(item -> {
			DetallePedido detalle = new DetallePedido();
			detalle.setPedido(pedido);
			detalle.setProducto(item.getProducto());
			detalle.setCantidad(item.getCantidad());
			detalle.setPrecioUnitario(item.getPrecioUnitario());
			detalle.setSubtotal(item.getSubtotal());
			if (item.getOpciones() != null && !item.getOpciones().isEmpty()) {
				detalle.setOpciones(new ArrayList<>(item.getOpciones()));
			}
			return detalle;
		}).collect(Collectors.toList());

		double total = detalles.stream().mapToDouble(DetallePedido::getSubtotal).sum();

		if (pedido.getPromocion() != null) {
			double descuento = pedido.getPromocion().getPorcentajeDescuento() / 100.0;
			total = total - (total * descuento);
		}

		pedido.setDetalles(detalles);
		pedido.setValorTotal(total + 8000);

		Pedido guardado = pedidoRepository.save(pedido);
		carritoService.confirmarCarrito(idCarrito);

		return mapToDTO(guardado);
	}

	private PedidoDTO mapToDTO(Pedido p) {
		PedidoDTO dto = modelMapper.map(p, PedidoDTO.class);

		dto.setIdUsuario(p.getUsuario().getIdUsuario());
		dto.setNombreUsuario(p.getUsuario().getNombre());

		if (p.getEvento() != null) {
			dto.setIdEvento(p.getEvento().getIdEvento());
		}
		if (p.getPromocion() != null) {
			dto.setIdPromocion(p.getPromocion().getIdPromocion());
			dto.setNombrePromocion(p.getPromocion().getNombre());
		}
		if (p.getEnvio() != null) {
			dto.setEstadoEnvio(p.getEnvio().getEstado());
			dto.setTipoEntrega(p.getEnvio().getTipoEntrega());

			if (p.getEnvio().getDireccion() != null) {
				Direccion d = p.getEnvio().getDireccion();
				dto.setDireccionEntrega(d.getCalle() + ", " + d.getCiudad() + ", " + d.getDepartamento());
			}
		}

		if (p.getDetalles() != null) {
			dto.setDetalles(p.getDetalles().stream().map(this::mapDetalleToDTO).collect(Collectors.toList()));
		}

		return dto;
	}

	private DetallePedidoDTO mapDetalleToDTO(DetallePedido d) {
		DetallePedidoDTO dto = modelMapper.map(d, DetallePedidoDTO.class);
		dto.setNombreProducto(d.getProducto().getNombre());
		return dto;
	}

	public List<PedidoDTO> obtenerPorCorreo(String correo) {
		return pedidoRepository.findByUsuarioCorreo(correo).stream().map(this::mapToDTO).collect(Collectors.toList());
	}
}
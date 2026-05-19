package co.edu.unbosque.ddeli.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.ddeli.dto.CarritoDTO;
import co.edu.unbosque.ddeli.dto.ItemCarritoDTO;
import co.edu.unbosque.ddeli.dto.OpcionPersonalizacionDTO;
import co.edu.unbosque.ddeli.dto.ProductoDTO;
import co.edu.unbosque.ddeli.entity.Carrito;
import co.edu.unbosque.ddeli.entity.ItemCarrito;
import co.edu.unbosque.ddeli.entity.OpcionPersonalizacion;
import co.edu.unbosque.ddeli.entity.Producto;
import co.edu.unbosque.ddeli.entity.Usuario;
import co.edu.unbosque.ddeli.repository.CarritoRepository;
import co.edu.unbosque.ddeli.repository.ItemCarritoRepository;
import co.edu.unbosque.ddeli.repository.OpcionPersonalizacionRepository;
import co.edu.unbosque.ddeli.repository.ProductoRepository;
import co.edu.unbosque.ddeli.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class CarritoService implements CRUDOperation<CarritoDTO> {

	@Autowired
	private CarritoRepository carritoRepository;

	@Autowired
	private ItemCarritoRepository itemCarritoRepository;

	@Autowired
	private ProductoRepository productoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private OpcionPersonalizacionRepository opcionRepository;

	public CarritoService() {

	}

	@Override
	public int create(CarritoDTO newData) {

		Optional<Usuario> usuarioOpt = usuarioRepository.findById(newData.getIdUsuario());

		if (!usuarioOpt.isPresent()) {
			return 1;
		}

		Optional<Carrito> carritoExistente = carritoRepository.findByUsuarioIdUsuarioAndEstado(newData.getIdUsuario(),
				"ACTIVO");

		if (carritoExistente.isPresent()) {
			return 2;
		}

		Carrito carrito = new Carrito();

		carrito.setUsuario(usuarioOpt.get());
		carrito.setEstado("ACTIVO");
		carrito.setFechaCreacion(LocalDate.now());

		carritoRepository.save(carrito);

		return 0;
	}

	@Override
	public List<CarritoDTO> getAll() {

		List<Carrito> entityList = carritoRepository.findAll();

		List<CarritoDTO> dtoList = new ArrayList<>();

		for (Carrito entity : entityList) {

			CarritoDTO dto = modelMapper.map(entity, CarritoDTO.class);

			dtoList.add(dto);
		}

		return dtoList;
	}

	@Override
	public int deleteByID(Long id) {

		Optional<Carrito> found = carritoRepository.findById(id);

		if (found.isPresent()) {

			carritoRepository.deleteById(id);

			return 0;
		}

		return 1;
	}

	@Override
	public int updateByID(Long id, CarritoDTO newData) {

		Optional<Carrito> found = carritoRepository.findById(id);

		if (found.isPresent()) {

			Carrito temp = found.get();

			temp.setEstado(newData.getEstado());

			carritoRepository.save(temp);

			return 0;
		}

		return 1;
	}

	public CarritoDTO obtenerOCrearCarritoPorCorreo(String correo) {

		Usuario usuario = usuarioRepository.findByCorreo(correo)
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

		Optional<Carrito> carritoExistente = carritoRepository.findByUsuarioIdUsuarioAndEstado(usuario.getIdUsuario(),
				"ACTIVO");

		if (carritoExistente.isPresent()) {
			Carrito carrito = carritoExistente.get();
			CarritoDTO dto = new CarritoDTO();
			dto.setIdCarrito(carrito.getIdCarrito());
			dto.setEstado(carrito.getEstado());
			dto.setFechaCreacion(carrito.getFechaCreacion());
			dto.setIdUsuario(usuario.getIdUsuario());
			dto.setItems(obtenerItems(carrito.getIdCarrito()));
			return dto;
		}

		Carrito nuevoCarrito = new Carrito();
		nuevoCarrito.setUsuario(usuario);
		nuevoCarrito.setEstado("ACTIVO");
		nuevoCarrito.setFechaCreacion(LocalDate.now());
		Carrito saved = carritoRepository.save(nuevoCarrito);

		CarritoDTO dto = new CarritoDTO();
		dto.setIdCarrito(saved.getIdCarrito());
		dto.setEstado(saved.getEstado());
		dto.setFechaCreacion(saved.getFechaCreacion());
		dto.setIdUsuario(usuario.getIdUsuario());
		dto.setItems(new ArrayList<>());
		return dto;
	}

	@Transactional
	public List<ItemCarritoDTO> obtenerItems(Long idCarrito) {

		List<ItemCarrito> items = itemCarritoRepository.findByCarritoIdCarritoWithOpciones(idCarrito);
		List<ItemCarritoDTO> dtoList = new ArrayList<>();

		for (ItemCarrito item : items) {
			ItemCarritoDTO dto = new ItemCarritoDTO();
			dto.setIdItem(item.getIdItem());
			dto.setCantidad(item.getCantidad());
			dto.setPrecioUnitario(item.getPrecioUnitario());
			dto.setSubtotal(item.getSubtotal());
			dto.setIdCarrito(item.getCarrito().getIdCarrito());
			dto.setIdProducto(item.getProducto().getIdProducto());

			if (item.getProducto() != null) {
				ProductoDTO productoDTO = new ProductoDTO();
				productoDTO.setIdProducto(item.getProducto().getIdProducto());
				productoDTO.setNombre(item.getProducto().getNombre());
				productoDTO.setDescripcion(item.getProducto().getDescripcion());
				productoDTO.setPrecioBase(item.getProducto().getPrecioBase());
				productoDTO.setImagenURL(item.getProducto().getImagenURL());
				dto.setProducto(productoDTO);
			}

			if (item.getOpciones() != null && !item.getOpciones().isEmpty()) {
				List<OpcionPersonalizacionDTO> opcionesDTO = item.getOpciones().stream().map(op -> {
					OpcionPersonalizacionDTO opDTO = new OpcionPersonalizacionDTO();
					opDTO.setIdOpcion(op.getIdOpcion());
					opDTO.setNombre(op.getNombre());
					opDTO.setCostoAdicional(op.getCostoAdicional());
					opDTO.setIdTipo(op.getTipoPersonalizacion().getIdTipo());
					opDTO.setNombreTipo(op.getTipoPersonalizacion().getNombre());
					return opDTO;
				}).collect(java.util.stream.Collectors.toList());
				dto.setOpciones(opcionesDTO);
			}

			dtoList.add(dto);
		}

		return dtoList;
	}

	@Transactional
	public int agregarProducto(Long idCarrito, Long idProducto, int cantidad, List<Long> idOpciones) {

		Optional<Carrito> carritoOpt = carritoRepository.findById(idCarrito);
		if (!carritoOpt.isPresent())
			return 1;

		Optional<Producto> productoOpt = productoRepository.findById(idProducto);
		if (!productoOpt.isPresent())
			return 2;

		Carrito carrito = carritoOpt.get();
		Producto producto = productoOpt.get();

	
		double costoOpciones = 0;
		List<OpcionPersonalizacion> opciones = new ArrayList<>();
		if (idOpciones != null && !idOpciones.isEmpty()) {
			opciones = opcionRepository.findAllById(idOpciones);
			costoOpciones = opciones.stream().mapToDouble(OpcionPersonalizacion::getCostoAdicional).sum();
		}

		double precioUnitario = producto.getPrecioBase() + costoOpciones;

		if (idOpciones != null && !idOpciones.isEmpty()) {
			ItemCarrito nuevoItem = new ItemCarrito();
			nuevoItem.setCarrito(carrito);
			nuevoItem.setProducto(producto);
			nuevoItem.setCantidad(cantidad);
			nuevoItem.setPrecioUnitario(precioUnitario);
			nuevoItem.setSubtotal(precioUnitario * cantidad);
			nuevoItem.setOpciones(opciones);
			itemCarritoRepository.save(nuevoItem);
			return 0;
		}

		Optional<ItemCarrito> itemExistente = itemCarritoRepository
				.findByCarritoIdCarritoAndProductoIdProducto(idCarrito, idProducto);

		if (itemExistente.isPresent()) {
			ItemCarrito item = itemExistente.get();
			item.setCantidad(item.getCantidad() + cantidad);
			item.setSubtotal(item.getPrecioUnitario() * item.getCantidad());
			itemCarritoRepository.save(item);
			return 0;
		}

		ItemCarrito nuevoItem = new ItemCarrito();
		nuevoItem.setCarrito(carrito);
		nuevoItem.setProducto(producto);
		nuevoItem.setCantidad(cantidad);
		nuevoItem.setPrecioUnitario(precioUnitario);
		nuevoItem.setSubtotal(precioUnitario * cantidad);
		itemCarritoRepository.save(nuevoItem);
		return 0;
	}

	@Transactional
	public int actualizarCantidad(Long idItem, int nuevaCantidad) {

		if (nuevaCantidad <= 0) {
			return 1;
		}

		Optional<ItemCarrito> itemOpt = itemCarritoRepository.findById(idItem);

		if (!itemOpt.isPresent()) {
			return 2;
		}

		ItemCarrito item = itemOpt.get();

		item.setCantidad(nuevaCantidad);

		item.setSubtotal(item.getPrecioUnitario() * nuevaCantidad);

		itemCarritoRepository.save(item);

		return 0;
	}

	@Transactional
	public int eliminarProducto(Long idItem) {

		Optional<ItemCarrito> itemOpt = itemCarritoRepository.findById(idItem);

		if (itemOpt.isPresent()) {
			ItemCarrito item = itemOpt.get();
			Carrito carrito = item.getCarrito();

			carrito.getItems().remove(item);
			carritoRepository.save(carrito);

			return 0;
		}

		return 1;
	}

	@Transactional
	public int vaciarCarrito(Long idCarrito) {

		Optional<Carrito> carritoOpt = carritoRepository.findById(idCarrito);

		if (carritoOpt.isPresent()) {

			itemCarritoRepository.deleteByCarritoIdCarrito(idCarrito);

			return 0;
		}

		return 1;
	}

	public double calcularTotal(Long idCarrito) {

		List<ItemCarrito> items = itemCarritoRepository.findByCarritoIdCarrito(idCarrito);

		double total = 0;

		for (ItemCarrito item : items) {

			total += item.getSubtotal();
		}

		return total;
	}

	@Transactional
	public int confirmarCarrito(Long idCarrito) {

		Optional<Carrito> carritoOpt = carritoRepository.findById(idCarrito);

		if (carritoOpt.isPresent()) {

			Carrito carrito = carritoOpt.get();

			carrito.setEstado("CONFIRMADO");

			carritoRepository.save(carrito);

			return 0;
		}

		return 1;
	}

	@Transactional
	public int abandonarCarrito(Long idCarrito) {

		Optional<Carrito> carritoOpt = carritoRepository.findById(idCarrito);

		if (carritoOpt.isPresent()) {

			Carrito carrito = carritoOpt.get();

			carrito.setEstado("ABANDONADO");

			carritoRepository.save(carrito);

			return 0;
		}

		return 1;
	}

	public boolean exist(Long id) {

		return carritoRepository.existsById(id);
	}

	public long count() {

		return carritoRepository.count();
	}

	public CarritoDTO obtenerPorId(Long id) {

		Carrito carrito = carritoRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

		CarritoDTO dto = new CarritoDTO();

		dto.setIdCarrito(carrito.getIdCarrito());

		dto.setEstado(carrito.getEstado());

		dto.setFechaCreacion(carrito.getFechaCreacion());

		dto.setIdUsuario(carrito.getUsuario().getIdUsuario());

		dto.setItems(obtenerItems(carrito.getIdCarrito()));

		return dto;
	}
}
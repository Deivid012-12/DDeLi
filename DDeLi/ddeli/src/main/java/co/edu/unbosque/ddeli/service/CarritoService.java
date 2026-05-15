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
import co.edu.unbosque.ddeli.dto.ProductoDTO;
import co.edu.unbosque.ddeli.entity.Carrito;
import co.edu.unbosque.ddeli.entity.ItemCarrito;
import co.edu.unbosque.ddeli.entity.Producto;
import co.edu.unbosque.ddeli.entity.Usuario;
import co.edu.unbosque.ddeli.repository.CarritoRepository;
import co.edu.unbosque.ddeli.repository.ItemCarritoRepository;
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

			CarritoDTO dto = modelMapper.map(carritoExistente.get(), CarritoDTO.class);

			dto.setItems(obtenerItems(carritoExistente.get().getIdCarrito()));

			return dto;
		}

		Carrito nuevoCarrito = new Carrito();

		nuevoCarrito.setUsuario(usuario);
		nuevoCarrito.setEstado("ACTIVO");
		nuevoCarrito.setFechaCreacion(LocalDate.now());

		Carrito saved = carritoRepository.save(nuevoCarrito);

		CarritoDTO dto = modelMapper.map(saved, CarritoDTO.class);

		dto.setItems(new ArrayList<>());

		return dto;
	}

	public List<ItemCarritoDTO> obtenerItems(Long idCarrito) {
		List<ItemCarrito> items = itemCarritoRepository.findByCarritoIdCarrito(idCarrito);
		List<ItemCarritoDTO> dtoList = new ArrayList<>();

		for (ItemCarrito item : items) {
			ItemCarritoDTO dto = modelMapper.map(item, ItemCarritoDTO.class);

			if (item.getProducto() != null) {
				ProductoDTO productoDTO = new ProductoDTO();
				productoDTO.setIdProducto(item.getProducto().getIdProducto());
				productoDTO.setNombre(item.getProducto().getNombre());
				productoDTO.setDescripcion(item.getProducto().getDescripcion());
				productoDTO.setPrecioBase(item.getProducto().getPrecioBase());
				productoDTO.setImagenURL(item.getProducto().getImagenURL());
				productoDTO.setTipo(item.getProducto().getTipo());

			}

			dtoList.add(dto);
		}
		return dtoList;
	}

	@Transactional
	public int agregarProducto(Long idCarrito, Long idProducto, int cantidad) {

		Optional<Carrito> carritoOpt = carritoRepository.findById(idCarrito);

		if (!carritoOpt.isPresent()) {
			return 1;
		}

		Optional<Producto> productoOpt = productoRepository.findById(idProducto);

		if (!productoOpt.isPresent()) {
			return 2;
		}

		Carrito carrito = carritoOpt.get();

		Producto producto = productoOpt.get();

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
		nuevoItem.setPrecioUnitario(producto.getPrecioBase());
		nuevoItem.setSubtotal(producto.getPrecioBase() * cantidad);

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

			itemCarritoRepository.deleteById(idItem);

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

		return modelMapper.map(carrito, CarritoDTO.class);
	}
}
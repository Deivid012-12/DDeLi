// CarritoService.java
package co.edu.unbosque.ddeli.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class CarritoService {

	@Autowired
	private CarritoRepository carritoRepository;

	@Autowired
	private ItemCarritoRepository itemCarritoRepository;

	@Autowired
	private ProductoRepository productoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public Carrito obtenerOCrearCarrito(Long idUsuario) {
		
		Optional<Carrito> carritoExistente = carritoRepository.findByUsuarioIdUsuarioAndEstado(idUsuario, "ACTIVO");

		if (carritoExistente.isPresent()) {
			return carritoExistente.get();
		}

		
		Usuario usuario = usuarioRepository.findById(idUsuario)
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + idUsuario));

		Carrito nuevoCarrito = new Carrito();
		nuevoCarrito.setUsuario(usuario);
		nuevoCarrito.setEstado("ACTIVO");
		nuevoCarrito.setFechaCreacion(LocalDate.now());

		return carritoRepository.save(nuevoCarrito);
	}

	
	public List<ItemCarrito> obtenerItems(Long idCarrito) {
		return itemCarritoRepository.findByCarritoIdCarrito(idCarrito);
	}

	
	@Transactional
	public ItemCarrito agregarProducto(Long idCarrito, Long idProducto, int cantidad) {

		Carrito carrito = carritoRepository.findById(idCarrito)
				.orElseThrow(() -> new RuntimeException("Carrito no encontrado: " + idCarrito));

		Producto producto = productoRepository.findById(idProducto)
				.orElseThrow(() -> new RuntimeException("Producto no encontrado: " + idProducto));

	
		Optional<ItemCarrito> itemExistente = itemCarritoRepository
				.findByCarritoIdCarritoAndProductoIdProducto(idCarrito, idProducto);

		if (itemExistente.isPresent()) {
			
			ItemCarrito item = itemExistente.get();
			item.setCantidad(item.getCantidad() + cantidad);
			item.setSubtotal(item.getPrecioUnitario() * item.getCantidad());
			return itemCarritoRepository.save(item);
		}

	
		ItemCarrito nuevoItem = new ItemCarrito();
		nuevoItem.setCarrito(carrito);
		nuevoItem.setProducto(producto);
		nuevoItem.setCantidad(cantidad);
		nuevoItem.setPrecioUnitario(producto.getPrecioBase());
		nuevoItem.setSubtotal(producto.getPrecioBase() * cantidad);

		return itemCarritoRepository.save(nuevoItem);
	}


	@Transactional
	public ItemCarrito actualizarCantidad(Long idItem, int nuevaCantidad) {

		if (nuevaCantidad <= 0) {
			throw new RuntimeException("La cantidad debe ser mayor a 0");
		}

		ItemCarrito item = itemCarritoRepository.findById(idItem)
				.orElseThrow(() -> new RuntimeException("Item no encontrado: " + idItem));

		item.setCantidad(nuevaCantidad);
		item.setSubtotal(item.getPrecioUnitario() * nuevaCantidad);

		return itemCarritoRepository.save(item);
	}

	
	@Transactional
	public void eliminarProducto(Long idItem) {
		if (!itemCarritoRepository.existsById(idItem)) {
			throw new RuntimeException("Item no encontrado: " + idItem);
		}
		itemCarritoRepository.deleteById(idItem);
	}

	
	@Transactional
	public void vaciarCarrito(Long idCarrito) {
		itemCarritoRepository.deleteByCarritoIdCarrito(idCarrito);
	}

	
	public double calcularTotal(Long idCarrito) {
		List<ItemCarrito> items = itemCarritoRepository.findByCarritoIdCarrito(idCarrito);
		return items.stream().mapToDouble(ItemCarrito::getSubtotal).sum();
	}

	
	@Transactional
	public void confirmarCarrito(Long idCarrito) {
		Carrito carrito = carritoRepository.findById(idCarrito)
				.orElseThrow(() -> new RuntimeException("Carrito no encontrado: " + idCarrito));
		carrito.setEstado("CONFIRMADO");
		carritoRepository.save(carrito);
	}

	
	@Transactional
	public void abandonarCarrito(Long idCarrito) {
		Carrito carrito = carritoRepository.findById(idCarrito)
				.orElseThrow(() -> new RuntimeException("Carrito no encontrado: " + idCarrito));
		carrito.setEstado("ABANDONADO");
		carritoRepository.save(carrito);
	}
}
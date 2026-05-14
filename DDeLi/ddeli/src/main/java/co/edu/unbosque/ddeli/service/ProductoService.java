// ProductoService.java
package co.edu.unbosque.ddeli.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.ddeli.dto.ProductoDTO;
import co.edu.unbosque.ddeli.entity.Categoria;
import co.edu.unbosque.ddeli.entity.Producto;
import co.edu.unbosque.ddeli.repository.CategoriaRepository;
import co.edu.unbosque.ddeli.repository.ProductoRepository;

@Service
public class ProductoService {

	@Autowired
	private ProductoRepository productoRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	private ProductoDTO toDTO(Producto p) {
		return new ProductoDTO(p.getIdProducto(), p.getNombre(), p.getDescripcion(), p.getPrecioBase(),
				p.isDisponibilidad(), p.getTipo(), p.getCategoria() != null ? p.getCategoria().getNombre() : null,
				p.getImagenURL());
	}

	public List<ProductoDTO> obtenerTodos() {
		return productoRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
	}

	public List<ProductoDTO> obtenerDisponibles() {
		return productoRepository.findByDisponibilidad(true).stream().map(this::toDTO).collect(Collectors.toList());
	}

	public List<ProductoDTO> obtenerPorTipo(String tipo) {
		return productoRepository.findByTipo(tipo).stream().map(this::toDTO).collect(Collectors.toList());
	}

	public List<ProductoDTO> obtenerPorCategoria(Long idCategoria) {
		return productoRepository.findByCategoriaIdCategoria(idCategoria).stream().map(this::toDTO)
				.collect(Collectors.toList());
	}

	public List<ProductoDTO> obtenerDisponiblesPorCategoria(Long idCategoria) {
		return productoRepository.findByCategoriaIdCategoriaAndDisponibilidad(idCategoria, true).stream()
				.map(this::toDTO).collect(Collectors.toList());
	}

	public List<ProductoDTO> buscarPorNombre(String nombre) {
		return productoRepository.findByNombreContainingIgnoreCase(nombre).stream().map(this::toDTO)
				.collect(Collectors.toList());
	}

	public Optional<ProductoDTO> obtenerPorId(Long id) {
		return productoRepository.findById(id).map(this::toDTO);
	}

	public ProductoDTO crear(Producto producto) {
		Categoria categoria = categoriaRepository.findById(producto.getCategoria().getIdCategoria())
				.orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

		producto.setCategoria(categoria);

		return toDTO(productoRepository.save(producto));
	}

	public ProductoDTO actualizar(Long id, Producto productoActualizado) {
		Producto existente = productoRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Producto no encontrado: " + id));

		existente.setNombre(productoActualizado.getNombre());
		existente.setDescripcion(productoActualizado.getDescripcion());
		existente.setPrecioBase(productoActualizado.getPrecioBase());
		existente.setDisponibilidad(productoActualizado.isDisponibilidad());
		existente.setTipo(productoActualizado.getTipo());

		existente.setImagenURL(productoActualizado.getImagenURL());
		;

		if (productoActualizado.getCategoria() != null) {
			Categoria categoria = categoriaRepository.findById(productoActualizado.getCategoria().getIdCategoria())
					.orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
			existente.setCategoria(categoria);
		}

		return toDTO(productoRepository.save(existente));
	}

	public void eliminar(Long id) {
		if (!productoRepository.existsById(id)) {
			throw new RuntimeException("Producto no encontrado: " + id);
		}
		productoRepository.deleteById(id);
	}
}
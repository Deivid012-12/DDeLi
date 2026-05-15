package co.edu.unbosque.ddeli.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.ddeli.dto.ProductoDTO;
import co.edu.unbosque.ddeli.entity.Categoria;
import co.edu.unbosque.ddeli.entity.Producto;
import co.edu.unbosque.ddeli.repository.CategoriaRepository;
import co.edu.unbosque.ddeli.repository.ProductoRepository;

@Service
public class ProductoService implements CRUDOperation<ProductoDTO> {

	@Autowired
	private ProductoRepository productoRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ModelMapper modelMapper;

	public ProductoService() {

	}

	@Override
	public int create(ProductoDTO newData) {

		if (newData.getIdCategoria() != null) {
			Optional<Categoria> categoriaOpt = categoriaRepository.findById(newData.getIdCategoria());
			if (!categoriaOpt.isPresent()) {
				return 2;
			}
		}

		Producto producto = modelMapper.map(newData, Producto.class);
		if (newData.getIdCategoria() != null) {
			producto.setCategoria(categoriaRepository.findById(newData.getIdCategoria()).get());
		}
		productoRepository.save(producto);
		return 0;
	}

	@Override
	public List<ProductoDTO> getAll() {
		List<Producto> entityList = productoRepository.findAll();
		List<ProductoDTO> dtoList = new ArrayList<>();

		entityList.forEach(entity -> {
			ProductoDTO dto = mapToDTO(entity);
			dtoList.add(dto);
		});

		return dtoList;
	}

	@Override
	public int deleteByID(Long id) {
		if (productoRepository.findById(id).isPresent()) {
			productoRepository.deleteById(id);
			return 0;
		}
		return 1;
	}

	@Override
	public int updateByID(Long id, ProductoDTO newData) {
		Optional<Producto> found = productoRepository.findById(id);

		if (found.isPresent()) {
			Producto temp = found.get();
			temp.setNombre(newData.getNombre());
			temp.setDescripcion(newData.getDescripcion());
			temp.setPrecioBase(newData.getPrecioBase());
			temp.setDisponibilidad(newData.isDisponibilidad());
			temp.setTipo(newData.getTipo());
			temp.setImagenURL(newData.getImagenURL());

			if (newData.getIdCategoria() != null) {
				Categoria categoria = categoriaRepository.findById(newData.getIdCategoria())
						.orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
				temp.setCategoria(categoria);
			}

			productoRepository.save(temp);
			return 0;
		}
		return 1;
	}

	public boolean exist(Long id) {
		return productoRepository.existsById(id);
	}

	public long count() {
		return productoRepository.count();
	}

	public int deleteById(Long id) {
		Optional<Producto> found = productoRepository.findById(id);
		if (found.isPresent()) {
			productoRepository.delete(found.get());
			return 0;
		}
		return 1;
	}

	public List<ProductoDTO> obtenerDisponibles() {
		return productoRepository.findByDisponibilidad(true).stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	public List<ProductoDTO> obtenerPorTipo(String tipo) {
		return productoRepository.findByTipo(tipo).stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	public List<ProductoDTO> obtenerPorCategoria(Long idCategoria) {
		return productoRepository.findByCategoriaIdCategoria(idCategoria).stream().map(this::mapToDTO)
				.collect(Collectors.toList());
	}

	public List<ProductoDTO> obtenerDisponiblesPorCategoria(Long idCategoria) {
		return productoRepository.findByCategoriaIdCategoriaAndDisponibilidad(idCategoria, true).stream()
				.map(this::mapToDTO).collect(Collectors.toList());
	}

	public List<ProductoDTO> buscarPorNombre(String nombre) {
		return productoRepository.findByNombreContainingIgnoreCase(nombre).stream().map(this::mapToDTO)
				.collect(Collectors.toList());
	}

	public Optional<ProductoDTO> obtenerPorId(Long id) {
		return productoRepository.findById(id).map(this::mapToDTO);
	}

	private ProductoDTO mapToDTO(Producto p) {
		ProductoDTO dto = modelMapper.map(p, ProductoDTO.class);
		dto.setNombreCategoria(p.getCategoria() != null ? p.getCategoria().getNombre() : null);
		return dto;
	}
}
package co.edu.unbosque.ddeli.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.ddeli.dto.ProductoDTO;
import co.edu.unbosque.ddeli.entity.Categoria;
import co.edu.unbosque.ddeli.entity.PostrePersonalizado;
import co.edu.unbosque.ddeli.entity.PostrePredeterminado;
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

		Optional<Categoria> categoriaOpt = Optional.empty();

		if (newData.getIdCategoria() != null) {
			categoriaOpt = categoriaRepository.findById(newData.getIdCategoria());
			if (!categoriaOpt.isPresent()) {
				return 2; 
			}
		}

		Producto producto;

		if ("PERSONALIZADO".equalsIgnoreCase(newData.getTipoProducto())) {
			PostrePersonalizado personalizado = modelMapper.map(newData, PostrePersonalizado.class);
			personalizado.setMaximoOpciones(newData.getMaximoOpciones());
			producto = personalizado;
		} else {
			PostrePredeterminado predeterminado = modelMapper.map(newData, PostrePredeterminado.class);
			predeterminado.setEstiloBase(newData.getEstiloBase());
			producto = predeterminado;
		}

		
		categoriaOpt.ifPresent(producto::setCategoria);

		productoRepository.save(producto);
		return 0;
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
			temp.setImagenURL(newData.getImagenURL());
		
			temp.setDisponibilidad(newData.isDisponibilidad());

			
			if (temp instanceof PostrePersonalizado personalizado) {
				personalizado.setMaximoOpciones(newData.getMaximoOpciones());
			} else if (temp instanceof PostrePredeterminado predeterminado) {
				predeterminado.setEstiloBase(newData.getEstiloBase());
			}

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

		if (p instanceof PostrePersonalizado personalizado) {
			dto.setTipoProducto("PERSONALIZADO");
			dto.setMaximoOpciones(personalizado.getMaximoOpciones());
		} else if (p instanceof PostrePredeterminado predeterminado) {
			dto.setTipoProducto("PREDETERMINADO");
			dto.setEstiloBase(predeterminado.getEstiloBase());
		}

		return dto;
	}

	public List<ProductoDTO> obtenerPredeterminados() {
		return productoRepository.findAll().stream().filter(producto -> producto instanceof PostrePredeterminado)
				.map(this::mapToDTO).collect(Collectors.toList());
	}

	public List<ProductoDTO> obtenerPersonalizados() {
		return productoRepository.findAll().stream().filter(producto -> producto instanceof PostrePersonalizado)
				.map(this::mapToDTO).collect(Collectors.toList());
	}


	@Override
	public List<ProductoDTO> getAll() {
		return productoRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
	}
}
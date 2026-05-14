// CategoriaService.java
package co.edu.unbosque.ddeli.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.ddeli.dto.CategoriaDTO;
import co.edu.unbosque.ddeli.entity.Categoria;
import co.edu.unbosque.ddeli.repository.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;

	private CategoriaDTO toDTO(Categoria c) {
		return new CategoriaDTO(c.getIdCategoria(), c.getNombre(), c.getDescripcion());
	}

	public List<CategoriaDTO> obtenerTodas() {
		return categoriaRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
	}

	public Optional<CategoriaDTO> obtenerPorId(Long id) {
		return categoriaRepository.findById(id).map(this::toDTO);
	}

	public CategoriaDTO crear(CategoriaDTO dto) {
		if (categoriaRepository.existsByNombre(dto.getNombre())) {
			throw new RuntimeException("Ya existe una categoría con el nombre: " + dto.getNombre());
		}
		Categoria categoria = new Categoria();
		categoria.setNombre(dto.getNombre());
		categoria.setDescripcion(dto.getDescripcion());
		return toDTO(categoriaRepository.save(categoria));
	}

	public CategoriaDTO actualizar(Long id, CategoriaDTO dto) {
		Categoria existente = categoriaRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Categoría no encontrada: " + id));

		existente.setNombre(dto.getNombre());
		existente.setDescripcion(dto.getDescripcion());

		return toDTO(categoriaRepository.save(existente));
	}

	public void eliminar(Long id) {
		if (!categoriaRepository.existsById(id)) {
			throw new RuntimeException("Categoría no encontrada: " + id);
		}
		categoriaRepository.deleteById(id);
	}
}
package co.edu.unbosque.ddeli.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.ddeli.dto.CategoriaDTO;
import co.edu.unbosque.ddeli.entity.Categoria;
import co.edu.unbosque.ddeli.repository.CategoriaRepository;

@Service
public class CategoriaService implements CRUDOperation<CategoriaDTO> {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ModelMapper modelMapper;

	public CategoriaService() {

	}

	@Override
	public int create(CategoriaDTO newData) {
		if (existsByNombre(newData.getNombre())) {
			return 1; 
		}

		Categoria categoria = modelMapper.map(newData, Categoria.class);
		categoriaRepository.save(categoria);
		return 0;
	}

	@Override
	public List<CategoriaDTO> getAll() {
		List<Categoria> entityList = categoriaRepository.findAll();
		List<CategoriaDTO> dtoList = new ArrayList<>();

		entityList.forEach(entity -> {
			CategoriaDTO dto = modelMapper.map(entity, CategoriaDTO.class);
			dtoList.add(dto);
		});

		return dtoList;
	}

	@Override
	public int deleteByID(Long id) {
		if (categoriaRepository.findById(id).isPresent()) {
			categoriaRepository.deleteById(id);
			return 0;
		}
		return 1;
	}

	@Override
	public int updateByID(Long id, CategoriaDTO newData) {
		Optional<Categoria> found = categoriaRepository.findById(id);

		if (found.isPresent()) {
			Categoria temp = found.get();
			temp.setNombre(newData.getNombre());
			temp.setDescripcion(newData.getDescripcion());
			categoriaRepository.save(temp);
			return 0;
		}
		return 1;
	}

	public boolean existsByNombre(String nombre) {
		return categoriaRepository.existsByNombre(nombre);
	}

	public boolean exist(Long id) {
		return categoriaRepository.existsById(id);
	}

	public long count() {
		return categoriaRepository.count();
	}

	public int deleteById(Long id) {
		Optional<Categoria> found = categoriaRepository.findById(id);
		if (found.isPresent()) {
			categoriaRepository.delete(found.get());
			return 0;
		}
		return 1;
	}

	public Optional<CategoriaDTO> obtenerPorId(Long id) {
		Optional<Categoria> found = categoriaRepository.findById(id);
		if (found.isPresent()) {
			CategoriaDTO dto = modelMapper.map(found.get(), CategoriaDTO.class);
			return Optional.of(dto);
		}
		return Optional.empty();
	}
}
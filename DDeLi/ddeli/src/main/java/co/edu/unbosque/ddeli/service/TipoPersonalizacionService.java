package co.edu.unbosque.ddeli.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.ddeli.dto.TipoPersonalizacionDTO;
import co.edu.unbosque.ddeli.entity.TipoPersonalizacion;
import co.edu.unbosque.ddeli.repository.TipoPersonalizacionRepository;

@Service
public class TipoPersonalizacionService implements CRUDOperation<TipoPersonalizacionDTO> {

	@Autowired
	private TipoPersonalizacionRepository tipoRepository;

	@Autowired
	private ModelMapper modelMapper;

	public TipoPersonalizacionService() {
	}

	@Override
	public int create(TipoPersonalizacionDTO newData) {
		if (tipoRepository.existsByNombre(newData.getNombre())) {
			return 1;
		}

		TipoPersonalizacion tipo = modelMapper.map(newData, TipoPersonalizacion.class);
		tipoRepository.save(tipo);
		return 0;
	}

	@Override
	public List<TipoPersonalizacionDTO> getAll() {
		List<TipoPersonalizacion> entityList = tipoRepository.findAll();
		List<TipoPersonalizacionDTO> dtoList = new ArrayList<>();

		entityList.forEach(entity -> {
			TipoPersonalizacionDTO dto = modelMapper.map(entity, TipoPersonalizacionDTO.class);
			dtoList.add(dto);
		});

		return dtoList;
	}

	@Override
	public int deleteByID(Long id) {
		if (tipoRepository.findById(id).isPresent()) {
			tipoRepository.deleteById(id);
			return 0;
		}
		return 1;
	}

	@Override
	public int updateByID(Long id, TipoPersonalizacionDTO newData) {
		Optional<TipoPersonalizacion> found = tipoRepository.findById(id);

		if (found.isPresent()) {
			TipoPersonalizacion temp = found.get();
			temp.setNombre(newData.getNombre());
			tipoRepository.save(temp);
			return 0;
		}
		return 1;
	}

	public boolean exist(Long id) {
		return tipoRepository.existsById(id);
	}

	public long count() {
		return tipoRepository.count();
	}
}
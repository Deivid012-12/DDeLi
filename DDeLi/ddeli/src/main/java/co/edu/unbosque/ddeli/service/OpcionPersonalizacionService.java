package co.edu.unbosque.ddeli.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.ddeli.dto.OpcionPersonalizacionDTO;
import co.edu.unbosque.ddeli.entity.OpcionPersonalizacion;
import co.edu.unbosque.ddeli.entity.TipoPersonalizacion;
import co.edu.unbosque.ddeli.repository.OpcionPersonalizacionRepository;
import co.edu.unbosque.ddeli.repository.TipoPersonalizacionRepository;

@Service
public class OpcionPersonalizacionService implements CRUDOperation<OpcionPersonalizacionDTO> {

	@Autowired
	private OpcionPersonalizacionRepository opcionRepository;

	@Autowired
	private TipoPersonalizacionRepository tipoRepository;

	@Autowired
	private ModelMapper modelMapper;

	public OpcionPersonalizacionService() {
	}

	@Override
	public int create(OpcionPersonalizacionDTO newData) {
		TipoPersonalizacion tipo = tipoRepository.findById(newData.getIdTipo()).orElse(null);

		if (tipo == null) {
			return 1;
		}

		if (opcionRepository.existsByNombreAndTipoPersonalizacionIdTipo(newData.getNombre(), newData.getIdTipo())) {
			return 2;
		}

		OpcionPersonalizacion opcion = modelMapper.map(newData, OpcionPersonalizacion.class);
		opcion.setTipoPersonalizacion(tipo);
		opcionRepository.save(opcion);
		return 0;
	}

	@Override
	public List<OpcionPersonalizacionDTO> getAll() {
		List<OpcionPersonalizacion> entityList = opcionRepository.findAll();
		List<OpcionPersonalizacionDTO> dtoList = new ArrayList<>();

		entityList.forEach(entity -> {
			OpcionPersonalizacionDTO dto = modelMapper.map(entity, OpcionPersonalizacionDTO.class);
			dto.setIdTipo(entity.getTipoPersonalizacion().getIdTipo());
			dto.setNombreTipo(entity.getTipoPersonalizacion().getNombre());
			dtoList.add(dto);
		});

		return dtoList;
	}

	@Override
	public int deleteByID(Long id) {
		if (opcionRepository.findById(id).isPresent()) {
			opcionRepository.deleteById(id);
			return 0;
		}
		return 1;
	}

	@Override
	public int updateByID(Long id, OpcionPersonalizacionDTO newData) {
		Optional<OpcionPersonalizacion> found = opcionRepository.findById(id);

		if (found.isPresent()) {
			OpcionPersonalizacion temp = found.get();
			temp.setNombre(newData.getNombre());
			temp.setCostoAdicional(newData.getCostoAdicional());

			if (newData.getIdTipo() != null) {
				TipoPersonalizacion tipo = tipoRepository.findById(newData.getIdTipo()).orElse(null);
				if (tipo == null)
					return 2;
				temp.setTipoPersonalizacion(tipo);
			}

			opcionRepository.save(temp);
			return 0;
		}
		return 1;
	}

	public List<OpcionPersonalizacionDTO> obtenerPorTipo(Long idTipo) {
		List<OpcionPersonalizacion> entityList = opcionRepository.findByTipoPersonalizacionIdTipo(idTipo);
		List<OpcionPersonalizacionDTO> dtoList = new ArrayList<>();

		entityList.forEach(entity -> {
			OpcionPersonalizacionDTO dto = modelMapper.map(entity, OpcionPersonalizacionDTO.class);
			dto.setIdTipo(entity.getTipoPersonalizacion().getIdTipo());
			dto.setNombreTipo(entity.getTipoPersonalizacion().getNombre());
			dtoList.add(dto);
		});

		return dtoList;
	}

	public boolean exist(Long id) {
		return opcionRepository.existsById(id);
	}

	public long count() {
		return opcionRepository.count();
	}
}
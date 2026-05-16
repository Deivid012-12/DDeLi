package co.edu.unbosque.ddeli.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.ddeli.dto.PlanSuscripcionDTO;
import co.edu.unbosque.ddeli.entity.PlanSuscripcion;
import co.edu.unbosque.ddeli.repository.PlanSuscripcionRepository;

@Service
public class PlanSuscripcionService implements CRUDOperation<PlanSuscripcionDTO> {

	@Autowired
	private PlanSuscripcionRepository planRepository;

	@Autowired
	private ModelMapper modelMapper;

	public PlanSuscripcionService() {
	}

	@Override
	public int create(PlanSuscripcionDTO newData) {
		if (planRepository.existsByNombre(newData.getNombre())) {
			return 1;
		}
		PlanSuscripcion plan = modelMapper.map(newData, PlanSuscripcion.class);
		planRepository.save(plan);
		return 0;
	}

	@Override
	public List<PlanSuscripcionDTO> getAll() {
		List<PlanSuscripcion> entityList = planRepository.findAll();
		List<PlanSuscripcionDTO> dtoList = new ArrayList<>();

		entityList.forEach(entity -> {
			PlanSuscripcionDTO dto = modelMapper.map(entity, PlanSuscripcionDTO.class);
			dtoList.add(dto);
		});

		return dtoList;
	}

	@Override
	public int deleteByID(Long id) {
		if (planRepository.findById(id).isPresent()) {
			planRepository.deleteById(id);
			return 0;
		}
		return 1;
	}

	@Override
	public int updateByID(Long id, PlanSuscripcionDTO newData) {
		Optional<PlanSuscripcion> found = planRepository.findById(id);

		if (found.isPresent()) {
			PlanSuscripcion temp = found.get();
			temp.setNombre(newData.getNombre());
			temp.setPrecioMensual(newData.getPrecioMensual());
			temp.setCostoAdicional(newData.getCostoAdicional());
			planRepository.save(temp);
			return 0;
		}
		return 1;
	}

	public boolean exist(Long id) {
		return planRepository.existsById(id);
	}

	public long count() {
		return planRepository.count();
	}
}
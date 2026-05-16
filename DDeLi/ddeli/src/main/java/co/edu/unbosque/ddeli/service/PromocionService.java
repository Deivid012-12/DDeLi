package co.edu.unbosque.ddeli.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.ddeli.dto.PromocionDTO;
import co.edu.unbosque.ddeli.entity.Promocion;
import co.edu.unbosque.ddeli.repository.PromocionRepository;

@Service
public class PromocionService implements CRUDOperation<PromocionDTO> {

	@Autowired
	private PromocionRepository promocionRepository;

	@Autowired
	private ModelMapper modelMapper;

	public PromocionService() {
	}

	@Override
	public int create(PromocionDTO newData) {
		if (promocionRepository.existsByNombre(newData.getNombre())) {
			return 1;
		}
		Promocion promocion = modelMapper.map(newData, Promocion.class);
		promocionRepository.save(promocion);
		return 0;
	}

	@Override
	public List<PromocionDTO> getAll() {
		List<Promocion> entityList = promocionRepository.findAll();
		List<PromocionDTO> dtoList = new ArrayList<>();

		entityList.forEach(entity -> {
			PromocionDTO dto = modelMapper.map(entity, PromocionDTO.class);
			dtoList.add(dto);
		});

		return dtoList;
	}

	@Override
	public int deleteByID(Long id) {
		if (promocionRepository.findById(id).isPresent()) {
			promocionRepository.deleteById(id);
			return 0;
		}
		return 1;
	}

	@Override
	public int updateByID(Long id, PromocionDTO newData) {
		Optional<Promocion> found = promocionRepository.findById(id);

		if (found.isPresent()) {
			Promocion temp = found.get();
			temp.setNombre(newData.getNombre());
			temp.setPorcentajeDescuento(newData.getPorcentajeDescuento());
			temp.setFechaInicio(newData.getFechaInicio());
			temp.setFechaFin(newData.getFechaFin());
			promocionRepository.save(temp);
			return 0;
		}
		return 1;
	}

	
	public List<PromocionDTO> obtenerVigentes() {
		LocalDate hoy = LocalDate.now();
		List<Promocion> entityList = promocionRepository.findByFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(hoy,
				hoy);
		List<PromocionDTO> dtoList = new ArrayList<>();

		entityList.forEach(entity -> {
			PromocionDTO dto = modelMapper.map(entity, PromocionDTO.class);
			dtoList.add(dto);
		});

		return dtoList;
	}

	public boolean exist(Long id) {
		return promocionRepository.existsById(id);
	}

	public long count() {
		return promocionRepository.count();
	}
}
package co.edu.unbosque.ddeli.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.ddeli.dto.DireccionDTO;
import co.edu.unbosque.ddeli.entity.Direccion;
import co.edu.unbosque.ddeli.entity.Usuario;
import co.edu.unbosque.ddeli.repository.DireccionRepository;
import co.edu.unbosque.ddeli.repository.UsuarioRepository;

@Service
public class DireccionService implements CRUDOperation<DireccionDTO> {

	@Autowired
	private DireccionRepository direccionRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private ModelMapper modelMapper;

	public DireccionService() {

	}

	@Override
	public int create(DireccionDTO newData) {

		if (existsByUsuarioIdAndCalleAndCiudad(newData.getIdUsuario(), newData.getCalle(), newData.getCiudad())) {
			return 1;
		}

		Optional<Usuario> usuarioOpt = usuarioRepository.findById(newData.getIdUsuario());
		if (!usuarioOpt.isPresent()) {
			return 2;
		}

		Direccion direccion = modelMapper.map(newData, Direccion.class);
		direccion.setUsuario(usuarioOpt.get());
		direccionRepository.save(direccion);
		return 0;
	}

	@Override
	public List<DireccionDTO> getAll() {
		List<Direccion> entityList = direccionRepository.findAll();
		List<DireccionDTO> dtoList = new ArrayList<>();

		entityList.forEach(entity -> {
			DireccionDTO dto = mapToDTO(entity);
			dtoList.add(dto);
		});

		return dtoList;
	}

	@Override
	public int deleteByID(Long id) {
		if (direccionRepository.findById(id).isPresent()) {
			direccionRepository.deleteById(id);
			return 0;
		}
		return 1;
	}

	@Override
	public int updateByID(Long id, DireccionDTO newData) {
		Optional<Direccion> found = direccionRepository.findById(id);

		if (found.isPresent()) {
			Direccion temp = found.get();
			temp.setCalle(newData.getCalle());
			temp.setCiudad(newData.getCiudad());
			temp.setCodigoPostal(newData.getCodigoPostal());
			temp.setDepartamento(newData.getDepartamento());
			direccionRepository.save(temp);
			return 0;
		}
		return 1;
	}

	public boolean exist(Long id) {
		return direccionRepository.existsById(id);
	}

	public Long crearPorCorreo(String correo, DireccionDTO newData) {
		Usuario usuario = usuarioRepository.findByCorreo(correo)
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

		Direccion direccion = new Direccion();
		direccion.setUsuario(usuario);
		direccion.setCalle(newData.getCalle());
		direccion.setCiudad(newData.getCiudad());
		direccion.setCodigoPostal(newData.getCodigoPostal());
		direccion.setDepartamento(newData.getDepartamento());
		direccion.setIndicaciones(newData.getIndicaciones());

		Direccion saved = direccionRepository.save(direccion);
		return saved.getIdDireccion();
	}

	public long count() {
		return direccionRepository.count();
	}

	public int deleteById(Long id) {
		Optional<Direccion> found = direccionRepository.findById(id);
		if (found.isPresent()) {
			direccionRepository.delete(found.get());
			return 0;
		}
		return 1;
	}

	public boolean existsByUsuarioIdAndCalleAndCiudad(Long idUsuario, String calle, String ciudad) {
		return direccionRepository.existsByUsuarioIdUsuarioAndCalleAndCiudad(idUsuario, calle, ciudad);
	}

	public List<DireccionDTO> obtenerPorUsuario(Long idUsuario) {
		return direccionRepository.findByUsuarioIdUsuario(idUsuario).stream().map(this::mapToDTO)
				.collect(Collectors.toList());
	}

	public Optional<DireccionDTO> obtenerPorId(Long id) {
		return direccionRepository.findById(id).map(this::mapToDTO);
	}

	private DireccionDTO mapToDTO(Direccion d) {
		DireccionDTO dto = modelMapper.map(d, DireccionDTO.class);
		dto.setIdUsuario(d.getUsuario().getIdUsuario());
		return dto;
	}
}
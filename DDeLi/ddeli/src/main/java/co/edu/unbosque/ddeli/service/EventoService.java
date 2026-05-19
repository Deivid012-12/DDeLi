package co.edu.unbosque.ddeli.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.ddeli.dto.EventoDTO;
import co.edu.unbosque.ddeli.entity.Evento;
import co.edu.unbosque.ddeli.entity.Usuario;
import co.edu.unbosque.ddeli.repository.EventoRepository;
import co.edu.unbosque.ddeli.repository.UsuarioRepository;

@Service
public class EventoService implements CRUDOperation<EventoDTO> {

	@Autowired
	private EventoRepository eventoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private ModelMapper modelMapper;

	public EventoService() {
	}

	@Override
	public int create(EventoDTO newData) {
		Usuario usuario = usuarioRepository.findById(newData.getIdUsuario()).orElse(null);

		if (usuario == null) {
			return 1;
		}

		if (eventoRepository.existsByUsuarioIdUsuarioAndTipoEventoAndFechaEvento(newData.getIdUsuario(),
				newData.getTipoEvento(), newData.getFechaEvento())) {
			return 2;
		}

		Evento evento = modelMapper.map(newData, Evento.class);
		evento.setUsuario(usuario);
		eventoRepository.save(evento);
		return 0;
	}

	@Override
	public List<EventoDTO> getAll() {
		List<Evento> entityList = eventoRepository.findAll();
		List<EventoDTO> dtoList = new ArrayList<>();

		entityList.forEach(entity -> {
			EventoDTO dto = modelMapper.map(entity, EventoDTO.class);
			dto.setIdUsuario(entity.getUsuario().getIdUsuario());
			dtoList.add(dto);
		});

		return dtoList;
	}

	@Override
	public int deleteByID(Long id) {
		if (eventoRepository.findById(id).isPresent()) {
			eventoRepository.deleteById(id);
			return 0;
		}
		return 1;
	}

	@Override
	public int updateByID(Long id, EventoDTO newData) {
		Optional<Evento> found = eventoRepository.findById(id);

		if (found.isPresent()) {
			Evento temp = found.get();
			temp.setFechaEvento(newData.getFechaEvento());
			temp.setNumeroPersonas(newData.getNumeroPersonas());
			temp.setTipoEvento(newData.getTipoEvento());
			eventoRepository.save(temp);
			return 0;
		}
		return 1;
	}

	public List<EventoDTO> obtenerPorUsuario(Long idUsuario) {
		List<Evento> entityList = eventoRepository.findByUsuarioIdUsuario(idUsuario);
		List<EventoDTO> dtoList = new ArrayList<>();

		entityList.forEach(entity -> {
			EventoDTO dto = modelMapper.map(entity, EventoDTO.class);
			dto.setIdUsuario(entity.getUsuario().getIdUsuario());
			dtoList.add(dto);
		});

		return dtoList;
	}

	public List<EventoDTO> obtenerPorTipo(String tipoEvento) {
		List<Evento> entityList = eventoRepository.findByTipoEvento(tipoEvento);
		List<EventoDTO> dtoList = new ArrayList<>();

		entityList.forEach(entity -> {
			EventoDTO dto = modelMapper.map(entity, EventoDTO.class);
			dto.setIdUsuario(entity.getUsuario().getIdUsuario());
			dtoList.add(dto);
		});

		return dtoList;
	}

	public boolean exist(Long id) {
		return eventoRepository.existsById(id);
	}

	public long count() {
		return eventoRepository.count();
	}

	public int crearParaUsuario(String correo, EventoDTO newData) {
		Usuario usuario = usuarioRepository.findByCorreo(correo).orElse(null);
		if (usuario == null)
			return 1;

		if (eventoRepository.existsByUsuarioIdUsuarioAndTipoEventoAndFechaEvento(usuario.getIdUsuario(),
				newData.getTipoEvento(), newData.getFechaEvento())) {
			return 2;
		}

		Evento evento = new Evento();
		evento.setUsuario(usuario);
		evento.setFechaEvento(newData.getFechaEvento());
		evento.setNumeroPersonas(newData.getNumeroPersonas());
		evento.setTipoEvento(newData.getTipoEvento());
		eventoRepository.save(evento);
		return 0;
	}

	public List<EventoDTO> obtenerPorCorreo(String correo) {
		Usuario usuario = usuarioRepository.findByCorreo(correo).orElse(null);
		if (usuario == null)
			return new ArrayList<>();
		return obtenerPorUsuario(usuario.getIdUsuario());
	}
}
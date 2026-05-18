package co.edu.unbosque.ddeli.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.ddeli.dto.PlanSuscripcionDTO;
import co.edu.unbosque.ddeli.dto.SuscripcionDTO;
import co.edu.unbosque.ddeli.entity.PlanSuscripcion;
import co.edu.unbosque.ddeli.entity.Suscripcion;
import co.edu.unbosque.ddeli.entity.Usuario;
import co.edu.unbosque.ddeli.repository.PlanSuscripcionRepository;
import co.edu.unbosque.ddeli.repository.SuscripcionRepository;
import co.edu.unbosque.ddeli.repository.UsuarioRepository;

@Service
public class SuscripcionService {

	@Autowired
	private SuscripcionRepository suscripcionRepository;

	@Autowired
	private PlanSuscripcionRepository planRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public SuscripcionDTO suscribir(String correo, Long idPlan) {
		if (suscripcionRepository.existsByUsuarioCorreoAndEstado(correo, "activa")) {
			throw new RuntimeException("El usuario ya tiene una suscripción activa");
		}

		Usuario usuario = usuarioRepository.findByCorreo(correo)
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

		PlanSuscripcion plan = planRepository.findById(idPlan)
				.orElseThrow(() -> new RuntimeException("Plan no encontrado"));

		Suscripcion suscripcion = new Suscripcion();
		suscripcion.setUsuario(usuario);
		suscripcion.setPlan(plan);
		suscripcion.setFechaInicio(LocalDate.now());
		suscripcion.setEstado("activa");

		Suscripcion guardada = suscripcionRepository.save(suscripcion);
		return mapToDTO(guardada);
	}

	public void cancelar(String correo) {
		Suscripcion suscripcion = suscripcionRepository.findByUsuarioCorreoAndEstado(correo, "activa")
				.orElseThrow(() -> new RuntimeException("No tienes una suscripción activa"));

		suscripcion.setEstado("cancelada");
		suscripcionRepository.save(suscripcion);
	}

	public SuscripcionDTO obtenerActiva(String correo) {
		return suscripcionRepository.findByUsuarioCorreoAndEstado(correo, "activa").map(this::mapToDTO).orElse(null);
	}

	public List<SuscripcionDTO> obtenerHistorial(String correo) {
		return suscripcionRepository.findByUsuarioCorreo(correo).stream().map(this::mapToDTO)
				.collect(Collectors.toList());
	}

	private SuscripcionDTO mapToDTO(Suscripcion s) {
		SuscripcionDTO dto = new SuscripcionDTO();
		dto.setIdSuscripcion(s.getIdSuscripcion());
		dto.setFechaInicio(s.getFechaInicio());
		dto.setEstado(s.getEstado());

		if (s.getPlan() != null) {
			PlanSuscripcionDTO planDTO = new PlanSuscripcionDTO();
			planDTO.setIdPlan(s.getPlan().getIdPlan());
			planDTO.setNombre(s.getPlan().getNombre());
			planDTO.setPrecioMensual(s.getPlan().getPrecioMensual());
			planDTO.setCostoAdicional(s.getPlan().getCostoAdicional());
			dto.setPlan(planDTO);
		}

		return dto;
	}

}
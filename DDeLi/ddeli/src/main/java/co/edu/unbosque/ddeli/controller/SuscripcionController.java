package co.edu.unbosque.ddeli.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.ddeli.dto.SuscripcionDTO;
import co.edu.unbosque.ddeli.service.SuscripcionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/suscripcion")
@CrossOrigin(origins = { "http://localhost:4200" })
@Transactional
@Tag(name = "Gestión de Suscripciones", description = "Endpoints para administrar suscripciones")
@SecurityRequirement(name = "bearerAuth")
public class SuscripcionController {

	@Autowired
	private SuscripcionService suscripcionSer;

	// Suscribirse a un plan
	@PostMapping("/suscribirse/{idPlan}")
	public ResponseEntity<String> suscribirse(@PathVariable Long idPlan, Authentication authentication) {
		try {
			String correo = authentication.getName();
			SuscripcionDTO dto = suscripcionSer.suscribir(correo, idPlan);
			return new ResponseEntity<>("Suscripción creada con éxito - ID: " + dto.getIdSuscripcion(),
					HttpStatus.CREATED);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/cancelar")
	public ResponseEntity<String> cancelar(Authentication authentication) {
		try {
			String correo = authentication.getName();
			suscripcionSer.cancelar(correo);
			return new ResponseEntity<>("Suscripción cancelada", HttpStatus.OK);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/miSuscripcion")
	public ResponseEntity<SuscripcionDTO> obtenerActiva(Authentication authentication) {
		String correo = authentication.getName();
		SuscripcionDTO dto = suscripcionSer.obtenerActiva(correo);
		if (dto == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@GetMapping("/historial")
	public ResponseEntity<List<SuscripcionDTO>> historial(Authentication authentication) {
		String correo = authentication.getName();
		List<SuscripcionDTO> historial = suscripcionSer.obtenerHistorial(correo);
		if (historial.isEmpty()) {
			return new ResponseEntity<>(historial, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(historial, HttpStatus.OK);
	}
}
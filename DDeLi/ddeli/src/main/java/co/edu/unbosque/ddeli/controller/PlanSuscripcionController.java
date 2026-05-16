package co.edu.unbosque.ddeli.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.ddeli.dto.PlanSuscripcionDTO;
import co.edu.unbosque.ddeli.service.PlanSuscripcionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/plan")
@CrossOrigin(origins = { "http://localhost:4200" })
@Transactional
@Tag(name = "Gestión de Planes de Suscripción", description = "Endpoints para administrar planes de suscripción")
@SecurityRequirement(name = "bearerAuth")
public class PlanSuscripcionController {

	@Autowired
	private PlanSuscripcionService planSer;

	@GetMapping(path = "/getall")
	public ResponseEntity<List<PlanSuscripcionDTO>> getAll() {
		List<PlanSuscripcionDTO> planes = planSer.getAll();
		if (planes.isEmpty()) {
			return new ResponseEntity<>(planes, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(planes, HttpStatus.OK);
		}
	}

	@PostMapping(path = "/crear")
	public ResponseEntity<String> crear(@RequestBody PlanSuscripcionDTO newPlan) {
		int status = planSer.create(newPlan);

		if (status == 0) {
			return new ResponseEntity<>("Plan creado con éxito", HttpStatus.CREATED);
		} else if (status == 1) {
			return new ResponseEntity<>("Ya existe un plan con ese nombre", HttpStatus.NOT_ACCEPTABLE);
		} else {
			return new ResponseEntity<>("Error al crear el plan", HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(path = "/createjson", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createNewWithJSON(@RequestBody PlanSuscripcionDTO newPlan) {
		int status = planSer.create(newPlan);

		if (status == 0) {
			return new ResponseEntity<>("Plan creado correctamente", HttpStatus.CREATED);
		} else if (status == 1) {
			return new ResponseEntity<>("Ya existe un plan con ese nombre", HttpStatus.NOT_ACCEPTABLE);
		} else {
			return new ResponseEntity<>("Error al crear el plan", HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping(path = "/actualizar/{id}")
	public ResponseEntity<String> actualizar(@PathVariable Long id, @RequestBody PlanSuscripcionDTO newPlan) {
		int status = planSer.updateByID(id, newPlan);

		if (status == 0) {
			return new ResponseEntity<>("Plan actualizado correctamente", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Plan no encontrado", HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping(path = "/deletebyid/{id}")
	public ResponseEntity<String> deleteById(@PathVariable Long id) {
		int status = planSer.deleteByID(id);

		if (status == 0) {
			return new ResponseEntity<>("Plan eliminado correctamente", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Plan no encontrado", HttpStatus.NOT_FOUND);
		}
	}
}
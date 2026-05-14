package co.edu.unbosque.ddeli.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.ddeli.dto.UsuarioDTO;
import co.edu.unbosque.ddeli.entity.Usuario.Role;
import co.edu.unbosque.ddeli.service.UsuarioService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = { "http://localhost:4200" })
@Transactional
@Tag(name = "Gestión de Usuarios", description = "Endpoints para administrar usuarios")
@SecurityRequirement(name = "bearerAuth")
public class UsuarioController {

	@Autowired
	private UsuarioService userSer;

	@PostMapping(path = "/crear")
	public ResponseEntity<String> crear(@RequestParam String correo, @RequestParam String nombre,
			@RequestParam String contrasenia, @RequestParam String telefono) {

		UsuarioDTO newUser = new UsuarioDTO();
		newUser.setNombre(nombre);
		newUser.setCorreo(correo);
		newUser.setTelefono(telefono);

		newUser.setContrasenia(contrasenia);

		newUser.setRol(Role.CLIENTE);

		int status = userSer.create(newUser);

		if (status == 0) {

			return new ResponseEntity<>("Usuario creado con éxito", HttpStatus.CREATED);

		} else if (status == 1) {

			return new ResponseEntity<>("Usuario ya existente", HttpStatus.NOT_ACCEPTABLE);

		} else if (status == 2) {

			return new ResponseEntity<>("Correo ya registrado", HttpStatus.NOT_ACCEPTABLE);

		} else {

			return new ResponseEntity<>("Error al crear el usuario", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/verificar")
	public ResponseEntity<String> verificarCuenta(@RequestParam int token) {

		boolean verificado = userSer.verificarUsuarioPorToken(token);

		if (verificado) {

			return new ResponseEntity<>("Cuenta verificada correctamente", HttpStatus.OK);

		} else {

			return new ResponseEntity<>("Token inválido", HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping(path = "/createjson", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createNewWithJSON(@RequestBody UsuarioDTO newUser) {

		newUser.setRol(Role.CLIENTE);

		int status = userSer.create(newUser);

		if (status == 0) {

			return new ResponseEntity<>("Usuario creado correctamente", HttpStatus.CREATED);

		} else if (status == 1) {

			return new ResponseEntity<>("Usuario ya existente", HttpStatus.NOT_ACCEPTABLE);

		} else if (status == 2) {

			return new ResponseEntity<>("Correo ya registrado", HttpStatus.NOT_ACCEPTABLE);

		} else {

			return new ResponseEntity<>("Error al crear el usuario", HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(path = "/checklogin")
	public ResponseEntity<String> checkLogIn(@RequestParam String correo, @RequestParam String password) {

		int status = userSer.validateCredentials(correo, password);

		if (status == 0) {

			return new ResponseEntity<>("Credenciales correctas", HttpStatus.ACCEPTED);

		} else {

			return new ResponseEntity<>("Correo o contraseña incorrectos", HttpStatus.UNAUTHORIZED);
		}
	}

	@PostMapping(path = "/login")
	public ResponseEntity<String> login(@RequestBody UsuarioDTO loginRequest) {

		int status = userSer.validateCredentials(loginRequest.getCorreo(), loginRequest.getContrasenia());

		if (status == 0) {

			return new ResponseEntity<>("Inicio de sesión exitoso", HttpStatus.OK);

		} else {

			return new ResponseEntity<>("Credenciales inválidas", HttpStatus.UNAUTHORIZED);
		}
	}

	@GetMapping("/getall")
	public ResponseEntity<List<UsuarioDTO>> getAll() {

		List<UsuarioDTO> usuarios = userSer.getAll();

		if (usuarios.isEmpty()) {

			return new ResponseEntity<>(usuarios, HttpStatus.NO_CONTENT);

		} else {

			return new ResponseEntity<>(usuarios, HttpStatus.OK);
		}
	}

	@GetMapping("/getbynombre/{nombre}")
	public ResponseEntity<UsuarioDTO> getByNombre(@PathVariable String nombre) {

		UsuarioDTO usuario = userSer.obtenerPorNombre(nombre);

		return new ResponseEntity<>(usuario, HttpStatus.OK);
	}

	@DeleteMapping("/deletebyid/{id}")
	public ResponseEntity<String> deleteById(@PathVariable Long id) {

		int status = userSer.deleteByID(id);

		if (status == 0) {

			return new ResponseEntity<>("Usuario eliminado correctamente", HttpStatus.OK);

		} else {

			return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
		}
	}
}
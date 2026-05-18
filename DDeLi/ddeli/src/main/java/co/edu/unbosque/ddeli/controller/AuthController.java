package co.edu.unbosque.ddeli.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.ddeli.dto.UsuarioDTO;
import co.edu.unbosque.ddeli.entity.Usuario;
import co.edu.unbosque.ddeli.security.JwtUtil;
import co.edu.unbosque.ddeli.service.UsuarioService;

/**
 * Controlador REST para la autenticación de usuarios. Maneja las operaciones de
 * inicio de sesión y registro de usuarios.
 */
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Autenticación", description = "API para autenticación de usuarios (login y registro)")
@SecurityRequirement(name = "bearerAuth")
public class AuthController {

	/** Gestor de autenticación para validar credenciales de usuario. */
	private final AuthenticationManager authenticationManager;

	/** Utilidad para operaciones con tokens JWT. */
	private final JwtUtil jwtUtil;

	/** Servicio para operaciones relacionadas con usuarios. */
	private final UsuarioService userService;

	/**
	 * Constructor que inicializa las dependencias necesarias para el controlador.
	 *
	 * @param authenticationManager Gestor de autenticación
	 * @param jwtUtil               Utilidad para tokens JWT
	 * @param userService           Servicio de usuarios
	 */
	public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UsuarioService userService) {
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
		this.userService = userService;
	}

	/**
	 * Maneja las solicitudes de inicio de sesión. Autentica al usuario y genera un
	 * token JWT si las credenciales son válidas.
	 *
	 * @param loginRequest DTO con las credenciales de inicio de sesión (nombre de
	 *                     usuario y contraseña)
	 * @return ResponseEntity con el token JWT y el rol del usuario si la
	 *         autenticación es exitosa, o un mensaje de error si falla
	 */
	@Operation(summary = "Iniciar sesión de usuario", description = """
			    Este endpoint permite a los usuarios iniciar sesión en el sistema proporcionando sus credenciales.

			    **¿Qué hace?** Verifica las credenciales del usuario y, si son correctas, genera un token JWT
			    que se utilizará para autenticar solicitudes posteriores.

			    **Paso a paso:**

			    1. Envía tu nombre de usuario y contraseña en formato JSON
			    2. Si las credenciales son correctas, recibirás un token JWT
			    3. Guarda este token para usarlo en futuras peticiones
			    4. Para usar el token, inclúyelo en el encabezado de autorización: `Authorization: Bearer tu_token_jwt`

			    **Nota:** El token tiene un tiempo de expiración limitado. Si expira, necesitarás iniciar sesión nuevamente.
			""")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Inicio de sesión exitoso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class), examples = @ExampleObject(value = """
					    {
					      "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
					      "role": "ADMIN"
					    }
					"""))),
			@ApiResponse(responseCode = "401", description = "Credenciales inválidas", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "Nombre de usuario o contraseña inválidos o usuario no"
					+ " encontrado"))) })
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UsuarioDTO loginRequest) {
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getCorreo(), loginRequest.getContrasenia()));

			UserDetails userDetails = (UserDetails) authentication.getPrincipal();

			if (userDetails instanceof Usuario) {
				Usuario user = (Usuario) userDetails;

				if (!user.isVerificado()) {
					return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Cuenta no verificada. Revisa tu correo.");
				}

				String jwt = jwtUtil.generateToken(userDetails);
				String role = user.getRol().name();
				String nombre = user.getNombre();

				return ResponseEntity.ok(new AuthResponse(jwt, role, nombre));
			}

			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error de autenticación");

		} catch (AuthenticationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Correo o contraseña inválidos");
		}
	}

	/**
	 * Maneja las solicitudes de registro de nuevos usuarios. Verifica si el nombre
	 * de usuario ya existe y crea un nuevo usuario si está disponible.
	 *
	 * @param registerRequest DTO con la información del nuevo usuario
	 * @return ResponseEntity con un mensaje de éxito si el registro es exitoso, o
	 *         un mensaje de error si falla
	 */
	@Operation(summary = "Registrar un nuevo usuario", description = """
			    Este endpoint permite crear una nueva cuenta de usuario en el sistema.

			    **¿Qué hace?** Registra un nuevo usuario con el nombre de usuario y contraseña proporcionados.
			    Por defecto, los usuarios creados mediante este endpoint tendrán el rol USER.

			    **Paso a paso:**

			    1. Envía el nombre de usuario y contraseña deseados en formato JSON
			    2. El sistema verificará si el nombre de usuario ya existe
			    3. Si el nombre está disponible, se creará la cuenta y recibirás un mensaje de éxito
			    4. Después de registrarte, puedes usar el endpoint de login para obtener un token JWT

			    **Requisitos para la contraseña:** La contraseña debe tener al menos 8 caracteres.

			    **Nota:** Este endpoint es público y no requiere autenticación.
			""")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Usuario registrado exitosamente", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "User registered successfully"))),
			@ApiResponse(responseCode = "409", description = "El correo ya existe", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "Username already exists"))),
			@ApiResponse(responseCode = "400", description = "Error al registrar el usuario", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "Error registering user"))) })
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody UsuarioDTO registerRequest) {

		int result = userService.create(registerRequest);

		if (result == 0) {
			return ResponseEntity.status(HttpStatus.CREATED).body("Usuario creado con éxito");
		}

		return ResponseEntity.status(HttpStatus.CONFLICT).body("El correo ya existe");
	}

	@GetMapping("/actual")
	public ResponseEntity<UsuarioDTO> obtenerUsuarioActual() {

		String correo = SecurityContextHolder.getContext().getAuthentication().getName();

		if (correo == null || correo.isBlank()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		UsuarioDTO usuario = userService.obtenerPorCorreo(correo);

		return ResponseEntity.ok(usuario);
	}

	/**
	 * Clase interna para representar la respuesta de autenticación. Contiene el
	 * token JWT y el rol del usuario autenticado.
	 */
	private static class AuthResponse {
		/** Token JWT generado para el usuario autenticado. */
		private final String token;

		/** Rol del usuario autenticado. */
		private final String role;
		private final String nombre;

		/**
		 * Constructor con token y rol.
		 *
		 * @param token Token JWT generado
		 * @param role  Rol del usuario
		 */
		public AuthResponse(String token, String role, String nombre) {
			super();
			this.token = token;
			this.role = role;
			this.nombre = nombre;
		}

		/**
		 * Obtiene el token JWT.
		 *
		 * @return Token JWT
		 */
		public String getToken() {
			return token;
		}

		/**
		 * @return the nombre
		 */
		public String getNombre() {
			return nombre;
		}

		/**
		 * Obtiene el rol del usuario.
		 *
		 * @return Rol del usuario
		 */
		public String getRole() {
			return role;
		}
	}
}

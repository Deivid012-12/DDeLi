package co.edu.unbosque.ddeli.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import co.edu.unbosque.ddeli.dto.UsuarioDTO;
import co.edu.unbosque.ddeli.entity.Usuario;
import co.edu.unbosque.ddeli.entity.Usuario.Role;
import co.edu.unbosque.ddeli.repository.UsuarioRepository;

@Service
public class UsuarioService implements CRUDOperation<UsuarioDTO> {

	@Autowired
	private UsuarioRepository userRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private EnvioCorreoService envioCorreo;

	public UsuarioService() {

	}

	@Override
	public int create(UsuarioDTO newData) {
		Usuario user = modelMapper.map(newData, Usuario.class);
		Random tokenR = new Random();
		int token = 10000 + tokenR.nextInt(90000);
		if (findCorreoAlreadyTaken(user.getCorreo())) {

			return 1;
		}
		user.setContrasenia(passwordEncoder.encode(newData.getContrasenia()));
		if (newData.getRol() != null) {
			user.setRol(newData.getRol());
		} else {
			user.setRol(Role.CLIENTE);
		}
		user.setVerificado(false);
		user.setToken(token);
		userRepo.save(user);

		envioCorreo.enviarCorreoVerificacion(user.getCorreo(), token);

		return 0;
	}

	@Override
	public List<UsuarioDTO> getAll() {

		List<Usuario> entityList = userRepo.findAll();

		List<UsuarioDTO> dtoList = new ArrayList<>();

		entityList.forEach(entity -> {

			UsuarioDTO dto = modelMapper.map(entity, UsuarioDTO.class);

			dtoList.add(dto);
		});

		return dtoList;
	}

	@Override
	public int deleteByID(Long id) {

		if (userRepo.findById(id).isPresent()) {

			userRepo.deleteById(id);
			return 0;
		}
		return 1;
	}

	@Override
	public int updateByID(Long id, UsuarioDTO newData) {

		Optional<Usuario> found = userRepo.findById(id);

		if (found.isPresent()) {

			Usuario temp = found.get();

			temp.setNombre(newData.getNombre());

			temp.setCorreo(newData.getCorreo());

			temp.setTelefono(newData.getTelefono());

			temp.setContrasenia(passwordEncoder.encode(newData.getContrasenia()));

			userRepo.save(temp);

			return 0;
		}

		return 1;
	}

	public boolean findCorreoAlreadyTaken(String correo) {

		Optional<Usuario> found = userRepo.findByCorreo(correo);

		return found.isPresent();
	}

	public int validateCredentials(String correo, String password) {

		Optional<Usuario> userOpt = userRepo.findByCorreo(correo);

		if (userOpt.isPresent()) {

			Usuario user = userOpt.get();

			if (passwordEncoder.matches(password, user.getContrasenia())) {

				return 0;
			}
		}

		return 1;
	}

	public boolean existByCorreo(String correo) {

		return userRepo.findByCorreo(correo).isPresent();
	}

	public long count() {

		return userRepo.count();
	}

	public boolean exist(Long id) {

		return userRepo.existsById(id);
	}

	public int deleteByCorreo(String correo) {

		Optional<Usuario> found = userRepo.findByCorreo(correo);

		if (found.isPresent()) {

			userRepo.delete(found.get());

			return 0;
		}

		return 1;
	}

	public boolean verificarUsuarioPorToken(int token) {

		Optional<Usuario> userOpt = userRepo.findByToken(token);

		if (userOpt.isPresent()) {

			Usuario user = userOpt.get();

			user.setVerificado(true);

			user.setToken(0);

			userRepo.save(user);

			return true;
		}

		return false;
	}

	public UsuarioDTO obtenerPorCorreo(String correo) {

		Usuario usuario = userRepo.findByCorreo(correo)
				.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

		return modelMapper.map(usuario, UsuarioDTO.class);
	}

	public List<UsuarioDTO> obtenerPorNombre(String nombre) {

		List<Usuario> usuarios = userRepo.findAllByNombre(nombre);

		List<UsuarioDTO> dtoList = new ArrayList<>();

		usuarios.forEach(usuario -> {
			dtoList.add(modelMapper.map(usuario, UsuarioDTO.class));
		});

		return dtoList;
	}
	public void reenviarCodigo(String correo) {
	    Usuario usuario = userRepo.findByCorreo(correo)
	        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

	    if (usuario.isVerificado()) {
	        throw new RuntimeException("La cuenta ya está verificada");
	    }

	    Random tokenR = new Random();
	    int nuevoToken = 10000 + tokenR.nextInt(90000);
	    usuario.setToken(nuevoToken);
	    userRepo.save(usuario);

	    envioCorreo.enviarCorreoVerificacion(correo, nuevoToken);
	}
}
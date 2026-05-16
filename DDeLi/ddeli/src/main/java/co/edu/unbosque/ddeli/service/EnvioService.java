package co.edu.unbosque.ddeli.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.ddeli.dto.EnvioDTO;
import co.edu.unbosque.ddeli.entity.Envio;
import co.edu.unbosque.ddeli.entity.Pedido;
import co.edu.unbosque.ddeli.repository.DireccionRepository;
import co.edu.unbosque.ddeli.repository.EnvioRepository;
import co.edu.unbosque.ddeli.repository.PedidoRepository;

@Service
public class EnvioService implements CRUDOperation<EnvioDTO> {

	@Autowired
	private EnvioRepository envioRepository;

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private DireccionRepository direccionRepository;

	public EnvioService() {
	}

	@Override
	public int create(EnvioDTO newData) {
		Pedido pedido = pedidoRepository.findById(newData.getIdPedido()).orElse(null);
		if (pedido == null)
			return 1;

		if (envioRepository.findByPedidoIdPedido(newData.getIdPedido()).isPresent())
			return 2;

		Envio envio = new Envio();
		envio.setPedido(pedido);
		envio.setTipoEntrega(newData.getTipoEntrega());
		envio.setEstado("PENDIENTE");
		envio.setFechaEnvio(java.time.LocalDate.now());

		if (newData.getIdDireccion() != null) {
			direccionRepository.findById(newData.getIdDireccion()).ifPresent(envio::setDireccion);
		}

		envioRepository.save(envio);
		return 0;
	}

	@Override
	public List<EnvioDTO> getAll() {
		List<Envio> entityList = envioRepository.findAll();
		List<EnvioDTO> dtoList = new ArrayList<>();

		entityList.forEach(entity -> {
			EnvioDTO dto = modelMapper.map(entity, EnvioDTO.class);
			dto.setIdPedido(entity.getPedido().getIdPedido());
			dtoList.add(dto);
		});

		return dtoList;
	}

	@Override
	public int deleteByID(Long id) {
		if (envioRepository.findById(id).isPresent()) {
			envioRepository.deleteById(id);
			return 0;
		}
		return 1;
	}

	@Override
	public int updateByID(Long id, EnvioDTO newData) {
		Optional<Envio> found = envioRepository.findById(id);

		if (found.isPresent()) {
			Envio temp = found.get();
			temp.setEstado(newData.getEstado());
			temp.setTipoEntrega(newData.getTipoEntrega());
			temp.setFechaEnvio(newData.getFechaEnvio());
			envioRepository.save(temp);
			return 0;
		}
		return 1;
	}

	public Optional<EnvioDTO> obtenerPorPedido(Long idPedido) {
		return envioRepository.findByPedidoIdPedido(idPedido).map(e -> {
			EnvioDTO dto = modelMapper.map(e, EnvioDTO.class);
			dto.setIdPedido(e.getPedido().getIdPedido());
			return dto;
		});
	}

	public List<EnvioDTO> obtenerPorEstado(String estado) {
		List<Envio> entityList = envioRepository.findByEstado(estado);
		List<EnvioDTO> dtoList = new ArrayList<>();

		entityList.forEach(entity -> {
			EnvioDTO dto = modelMapper.map(entity, EnvioDTO.class);
			dto.setIdPedido(entity.getPedido().getIdPedido());
			dtoList.add(dto);
		});

		return dtoList;
	}

	public List<EnvioDTO> obtenerPorTipoEntrega(String tipoEntrega) {
		List<Envio> entityList = envioRepository.findByTipoEntrega(tipoEntrega);
		List<EnvioDTO> dtoList = new ArrayList<>();

		entityList.forEach(entity -> {
			EnvioDTO dto = modelMapper.map(entity, EnvioDTO.class);
			dto.setIdPedido(entity.getPedido().getIdPedido());
			dtoList.add(dto);
		});

		return dtoList;
	}

	public boolean exist(Long id) {
		return envioRepository.existsById(id);
	}

	public long count() {
		return envioRepository.count();
	}
}
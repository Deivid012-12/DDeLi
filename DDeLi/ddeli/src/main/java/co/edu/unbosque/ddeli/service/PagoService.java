package co.edu.unbosque.ddeli.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.ddeli.dto.PagoDTO;
import co.edu.unbosque.ddeli.entity.Pago;
import co.edu.unbosque.ddeli.entity.Pedido;
import co.edu.unbosque.ddeli.repository.PagoRepository;
import co.edu.unbosque.ddeli.repository.PedidoRepository;

@Service
public class PagoService implements CRUDOperation<PagoDTO> {

	@Autowired
	private PagoRepository pagoRepository;

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private ModelMapper modelMapper;

	public PagoService() {
	}

	@Override
	public int create(PagoDTO newData) {
		Pedido pedido = pedidoRepository.findById(newData.getIdPedido()).orElse(null);

		if (pedido == null) {
			return 1;
		}

		if (pagoRepository.findByPedidoIdPedido(newData.getIdPedido()).isPresent()) {
			return 2;
		}

		Pago pago = modelMapper.map(newData, Pago.class);
		pago.setPedido(pedido);
		pagoRepository.save(pago);
		return 0;
	}

	@Override
	public List<PagoDTO> getAll() {
		List<Pago> entityList = pagoRepository.findAll();
		List<PagoDTO> dtoList = new ArrayList<>();

		entityList.forEach(entity -> {
			PagoDTO dto = modelMapper.map(entity, PagoDTO.class);
			dto.setIdPedido(entity.getPedido().getIdPedido());
			dtoList.add(dto);
		});

		return dtoList;
	}

	@Override
	public int deleteByID(Long id) {
		if (pagoRepository.findById(id).isPresent()) {
			pagoRepository.deleteById(id);
			return 0;
		}
		return 1;
	}

	@Override
	public int updateByID(Long id, PagoDTO newData) {
		Optional<Pago> found = pagoRepository.findById(id);

		if (found.isPresent()) {
			Pago temp = found.get();
			temp.setCantidadPago(newData.getCantidadPago());
			temp.setMetodoPago(newData.getMetodoPago());
			temp.setEstadoTransaccion(newData.getEstadoTransaccion());
			temp.setFechaPago(newData.getFechaPago());
			pagoRepository.save(temp);
			return 0;
		}
		return 1;
	}

	public Optional<PagoDTO> obtenerPorPedido(Long idPedido) {
		return pagoRepository.findByPedidoIdPedido(idPedido).map(e -> {
			PagoDTO dto = modelMapper.map(e, PagoDTO.class);
			dto.setIdPedido(e.getPedido().getIdPedido());
			return dto;
		});
	}

	public List<PagoDTO> obtenerPorEstado(String estadoTransaccion) {
		List<Pago> entityList = pagoRepository.findByEstadoTransaccion(estadoTransaccion);
		List<PagoDTO> dtoList = new ArrayList<>();

		entityList.forEach(entity -> {
			PagoDTO dto = modelMapper.map(entity, PagoDTO.class);
			dto.setIdPedido(entity.getPedido().getIdPedido());
			dtoList.add(dto);
		});

		return dtoList;
	}

	public List<PagoDTO> obtenerPorMetodo(String metodoPago) {
		List<Pago> entityList = pagoRepository.findByMetodoPago(metodoPago);
		List<PagoDTO> dtoList = new ArrayList<>();

		entityList.forEach(entity -> {
			PagoDTO dto = modelMapper.map(entity, PagoDTO.class);
			dto.setIdPedido(entity.getPedido().getIdPedido());
			dtoList.add(dto);
		});

		return dtoList;
	}

	public boolean exist(Long id) {
		return pagoRepository.existsById(id);
	}

	public long count() {
		return pagoRepository.count();
	}
}
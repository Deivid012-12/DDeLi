package co.edu.unbosque.ddeli.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.ddeli.dto.DetallePedidoDTO;
import co.edu.unbosque.ddeli.entity.DetallePedido;
import co.edu.unbosque.ddeli.repository.DetallePedidoRepository;

@Service
public class DetallePedidoService implements CRUDOperation<DetallePedidoDTO> {

	@Autowired
	private DetallePedidoRepository detallePedidoRepository;

	@Autowired
	private ModelMapper modelMapper;

	public DetallePedidoService() {

	}

	@Override
	public int create(DetallePedidoDTO newData) {
		DetallePedido detalle = modelMapper.map(newData, DetallePedido.class);
		detallePedidoRepository.save(detalle);
		return 0;
	}

	@Override
	public List<DetallePedidoDTO> getAll() {
		List<DetallePedido> entityList = detallePedidoRepository.findAll();
		List<DetallePedidoDTO> dtoList = new ArrayList<>();

		entityList.forEach(entity -> {
			DetallePedidoDTO dto = mapToDTO(entity);
			dtoList.add(dto);
		});

		return dtoList;
	}

	@Override
	public int deleteByID(Long id) {
		if (detallePedidoRepository.findById(id).isPresent()) {
			detallePedidoRepository.deleteById(id);
			return 0;
		}
		return 1;
	}

	@Override
	public int updateByID(Long id, DetallePedidoDTO newData) {
		Optional<DetallePedido> found = detallePedidoRepository.findById(id);

		if (found.isPresent()) {
			DetallePedido temp = found.get();
			// Actualizar campos específicos
			temp.setCantidad(newData.getCantidad());
			temp.setPrecioUnitario(newData.getPrecioUnitario());
			temp.setSubtotal(newData.getSubtotal());
			detallePedidoRepository.save(temp);
			return 0;
		}
		return 1;
	}

	public boolean exist(Long id) {
		return detallePedidoRepository.existsById(id);
	}

	public long count() {
		return detallePedidoRepository.count();
	}

	public int deleteById(Long id) {
		Optional<DetallePedido> found = detallePedidoRepository.findById(id);
		if (found.isPresent()) {
			detallePedidoRepository.delete(found.get());
			return 0;
		}
		return 1;
	}

	public List<DetallePedidoDTO> obtenerPorPedido(Long idPedido) {
		return detallePedidoRepository.findByPedidoIdPedido(idPedido).stream().map(this::mapToDTO)
				.collect(Collectors.toList());
	}

	public Optional<DetallePedidoDTO> obtenerPorId(Long id) {
		return detallePedidoRepository.findById(id).map(this::mapToDTO);
	}

	public List<DetallePedidoDTO> obtenerPorProducto(Long idProducto) {
		return detallePedidoRepository.findByProductoIdProducto(idProducto).stream().map(this::mapToDTO)
				.collect(Collectors.toList());
	}

	private DetallePedidoDTO mapToDTO(DetallePedido d) {
		DetallePedidoDTO dto = modelMapper.map(d, DetallePedidoDTO.class);

		if (d.getOpciones() != null && !d.getOpciones().isEmpty()) {
			dto.setNombresOpciones(d.getOpciones().stream().map(o -> o.getNombre()).collect(Collectors.toList()));
		}

		dto.setIdPedido(d.getPedido().getIdPedido());
		dto.setNombreProducto(d.getProducto().getNombre());
		dto.setImagenProducto(d.getProducto().getImagenURL());

		return dto;
	}
}
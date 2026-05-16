package co.edu.unbosque.ddeli.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.ddeli.entity.Pago;

public interface PagoRepository extends JpaRepository<Pago, Long> {

	Optional<Pago> findByPedidoIdPedido(Long idPedido);

	List<Pago> findByEstadoTransaccion(String estadoTransaccion);

	List<Pago> findByMetodoPago(String metodoPago);
}
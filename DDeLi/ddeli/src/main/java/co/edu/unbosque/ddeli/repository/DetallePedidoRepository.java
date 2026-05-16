package co.edu.unbosque.ddeli.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.ddeli.entity.DetallePedido;

public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long> {

	List<DetallePedido> findByPedidoIdPedido(Long idPedido);

	List<DetallePedido> findByProductoIdProducto(Long idProducto);
}
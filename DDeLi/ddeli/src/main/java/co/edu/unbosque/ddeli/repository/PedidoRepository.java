package co.edu.unbosque.ddeli.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.ddeli.entity.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

	List<Pedido> findByUsuarioIdUsuario(Long idUsuario);

	List<Pedido> findByUsuarioIdUsuarioAndFechaPedidoBetween(Long idUsuario, LocalDate inicio, LocalDate fin);

	List<Pedido> findByPromocionIdPromocion(Long idPromocion);

	List<Pedido> findByEventoIdEvento(Long idEvento);

	List<Pedido> findByUsuarioCorreo(String correo);
}
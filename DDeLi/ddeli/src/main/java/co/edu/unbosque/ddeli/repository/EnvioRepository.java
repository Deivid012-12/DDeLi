package co.edu.unbosque.ddeli.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.ddeli.entity.Envio;

public interface EnvioRepository extends JpaRepository<Envio, Long> {

	Optional<Envio> findByPedidoIdPedido(Long idPedido);

	List<Envio> findByEstado(String estado);

	List<Envio> findByTipoEntrega(String tipoEntrega);
}
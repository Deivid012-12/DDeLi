package co.edu.unbosque.ddeli.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idPedido;

	private LocalDate fechaPedido;
	private double valorTotal;

	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;

	@ManyToOne
	@JoinColumn(name = "id_evento")
	private Evento evento; // nullable, opcional

	@ManyToOne
	@JoinColumn(name = "id_promocion")
	private Promocion promocion; // nullable, opcional

	@JsonIgnore
	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
	private List<DetallePedido> detalles;

	@JsonIgnore
	@OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL)
	private Pago pago;

	@JsonIgnore
	@OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL)
	private Envio envio;

	public Pedido() {
	}

	public Pedido(Long idPedido, LocalDate fechaPedido, double valorTotal, Usuario usuario, Evento evento,
			Promocion promocion, List<DetallePedido> detalles, Pago pago, Envio envio) {
		this.idPedido = idPedido;
		this.fechaPedido = fechaPedido;
		this.valorTotal = valorTotal;
		this.usuario = usuario;
		this.evento = evento;
		this.promocion = promocion;
		this.detalles = detalles;
		this.pago = pago;
		this.envio = envio;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idPedido);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pedido other = (Pedido) obj;
		return Objects.equals(idPedido, other.idPedido);
	}

	public Long getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(Long idPedido) {
		this.idPedido = idPedido;
	}

	public LocalDate getFechaPedido() {
		return fechaPedido;
	}

	public void setFechaPedido(LocalDate fechaPedido) {
		this.fechaPedido = fechaPedido;
	}

	public double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public Promocion getPromocion() {
		return promocion;
	}

	public void setPromocion(Promocion promocion) {
		this.promocion = promocion;
	}

	public List<DetallePedido> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<DetallePedido> detalles) {
		this.detalles = detalles;
	}

	public Pago getPago() {
		return pago;
	}

	public void setPago(Pago pago) {
		this.pago = pago;
	}

	public Envio getEnvio() {
		return envio;
	}

	public void setEnvio(Envio envio) {
		this.envio = envio;
	}

	@Override
	public String toString() {
		return "Pedido [idPedido=" + idPedido + ", fechaPedido=" + fechaPedido + ", valorTotal=" + valorTotal
				+ ", usuario=" + usuario + ", evento=" + evento + ", promocion=" + promocion + "]";
	}
}
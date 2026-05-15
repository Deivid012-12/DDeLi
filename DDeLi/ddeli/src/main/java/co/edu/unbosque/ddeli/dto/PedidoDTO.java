package co.edu.unbosque.ddeli.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class PedidoDTO {

	private Long idPedido;
	private LocalDate fechaPedido;
	private double valorTotal;
	private Long idUsuario;
	private String nombreUsuario;
	private Long idEvento;
	private Long idPromocion;
	private String nombrePromocion;
	private List<DetallePedidoDTO> detalles;

	public PedidoDTO() {
	}

	@Override
	public int hashCode() {
		return Objects.hash(detalles, fechaPedido, idEvento, idPedido, idPromocion, idUsuario, nombrePromocion,
				nombreUsuario, valorTotal);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PedidoDTO other = (PedidoDTO) obj;
		return Objects.equals(detalles, other.detalles) && Objects.equals(fechaPedido, other.fechaPedido)
				&& Objects.equals(idEvento, other.idEvento) && Objects.equals(idPedido, other.idPedido)
				&& Objects.equals(idPromocion, other.idPromocion) && Objects.equals(idUsuario, other.idUsuario)
				&& Objects.equals(nombrePromocion, other.nombrePromocion)
				&& Objects.equals(nombreUsuario, other.nombreUsuario)
				&& Double.doubleToLongBits(valorTotal) == Double.doubleToLongBits(other.valorTotal);
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

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public Long getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(Long idEvento) {
		this.idEvento = idEvento;
	}

	public Long getIdPromocion() {
		return idPromocion;
	}

	public void setIdPromocion(Long idPromocion) {
		this.idPromocion = idPromocion;
	}

	public String getNombrePromocion() {
		return nombrePromocion;
	}

	public void setNombrePromocion(String nombrePromocion) {
		this.nombrePromocion = nombrePromocion;
	}

	public List<DetallePedidoDTO> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<DetallePedidoDTO> detalles) {
		this.detalles = detalles;
	}

	@Override
	public String toString() {
		return "PedidoDTO [idPedido=" + idPedido + ", fechaPedido=" + fechaPedido + ", valorTotal=" + valorTotal
				+ ", nombreUsuario=" + nombreUsuario + "]";
	}
}
package co.edu.unbosque.ddeli.dto;

import java.time.LocalDate;
import java.util.Objects;

public class PagoDTO {

	private Long idPago; // falta determinar la secuencia para generar automaticamente el id

	private double cantidadPago;
	private String metodoPago;
	private String estadoTransaccion;
	private LocalDate fechaPago;

	private PedidoDTO pedido;

	public PagoDTO() {
		// TODO Auto-generated constructor stub
	}

	public PagoDTO(Long idPago, double cantidadPago, String metodoPago, String estadoTransaccion, LocalDate fechaPago,
			PedidoDTO pedido) {
		super();
		this.idPago = idPago;
		this.cantidadPago = cantidadPago;
		this.metodoPago = metodoPago;
		this.estadoTransaccion = estadoTransaccion;
		this.fechaPago = fechaPago;
		this.pedido = pedido;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cantidadPago, estadoTransaccion, fechaPago, idPago, metodoPago, pedido);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PagoDTO other = (PagoDTO) obj;
		return Double.doubleToLongBits(cantidadPago) == Double.doubleToLongBits(other.cantidadPago)
				&& Objects.equals(estadoTransaccion, other.estadoTransaccion)
				&& Objects.equals(fechaPago, other.fechaPago) && Objects.equals(idPago, other.idPago)
				&& Objects.equals(metodoPago, other.metodoPago) && Objects.equals(pedido, other.pedido);
	}

	/**
	 * @return the idPago
	 */
	public Long getIdPago() {
		return idPago;
	}

	/**
	 * @param idPago the idPago to set
	 */
	public void setIdPago(Long idPago) {
		this.idPago = idPago;
	}

	/**
	 * @return the cantidadPago
	 */
	public double getCantidadPago() {
		return cantidadPago;
	}

	/**
	 * @param cantidadPago the cantidadPago to set
	 */
	public void setCantidadPago(double cantidadPago) {
		this.cantidadPago = cantidadPago;
	}

	/**
	 * @return the metodoPago
	 */
	public String getMetodoPago() {
		return metodoPago;
	}

	/**
	 * @param metodoPago the metodoPago to set
	 */
	public void setMetodoPago(String metodoPago) {
		this.metodoPago = metodoPago;
	}

	/**
	 * @return the estadoTransaccion
	 */
	public String getEstadoTransaccion() {
		return estadoTransaccion;
	}

	/**
	 * @param estadoTransaccion the estadoTransaccion to set
	 */
	public void setEstadoTransaccion(String estadoTransaccion) {
		this.estadoTransaccion = estadoTransaccion;
	}

	/**
	 * @return the fechaPago
	 */
	public LocalDate getFechaPago() {
		return fechaPago;
	}

	/**
	 * @param fechaPago the fechaPago to set
	 */
	public void setFechaPago(LocalDate fechaPago) {
		this.fechaPago = fechaPago;
	}

	/**
	 * @return the pedido
	 */
	public PedidoDTO getPedido() {
		return pedido;
	}

	/**
	 * @param pedido the pedido to set
	 */
	public void setPedido(PedidoDTO pedido) {
		this.pedido = pedido;
	}

	@Override
	public String toString() {
		return "Pago [idPago=" + idPago + ", cantidadPago=" + cantidadPago + ", metodoPago=" + metodoPago
				+ ", estadoTransaccion=" + estadoTransaccion + ", fechaPago=" + fechaPago + ", pedido=" + pedido + "]";
	}

}

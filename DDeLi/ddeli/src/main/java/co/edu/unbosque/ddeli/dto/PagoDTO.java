// PagoDTO.java
package co.edu.unbosque.ddeli.dto;

import java.time.LocalDate;

public class PagoDTO {

	private Long idPago;
	private double cantidadPago;
	private String metodoPago;
	private String estadoTransaccion;
	private LocalDate fechaPago;
	private Long idPedido;

	public PagoDTO() {
	}

	public PagoDTO(Long idPago, double cantidadPago, String metodoPago, String estadoTransaccion, LocalDate fechaPago,
			Long idPedido) {
		this.idPago = idPago;
		this.cantidadPago = cantidadPago;
		this.metodoPago = metodoPago;
		this.estadoTransaccion = estadoTransaccion;
		this.fechaPago = fechaPago;
		this.idPedido = idPedido;
	}

	public Long getIdPago() {
		return idPago;
	}

	public void setIdPago(Long idPago) {
		this.idPago = idPago;
	}

	public double getCantidadPago() {
		return cantidadPago;
	}

	public void setCantidadPago(double cantidadPago) {
		this.cantidadPago = cantidadPago;
	}

	public String getMetodoPago() {
		return metodoPago;
	}

	public void setMetodoPago(String metodoPago) {
		this.metodoPago = metodoPago;
	}

	public String getEstadoTransaccion() {
		return estadoTransaccion;
	}

	public void setEstadoTransaccion(String estadoTransaccion) {
		this.estadoTransaccion = estadoTransaccion;
	}

	public LocalDate getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(LocalDate fechaPago) {
		this.fechaPago = fechaPago;
	}

	public Long getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(Long idPedido) {
		this.idPedido = idPedido;
	}

	@Override
	public String toString() {
		return "PagoDTO [idPago=" + idPago + ", cantidadPago=" + cantidadPago + ", metodoPago=" + metodoPago
				+ ", estadoTransaccion=" + estadoTransaccion + ", fechaPago=" + fechaPago + ", idPedido=" + idPedido
				+ "]";
	}
}
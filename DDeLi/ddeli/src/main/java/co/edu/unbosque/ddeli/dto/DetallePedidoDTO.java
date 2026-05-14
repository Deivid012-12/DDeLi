// DetallePedidoDTO.java
package co.edu.unbosque.ddeli.dto;

public class DetallePedidoDTO {

	private Long idDetalle;
	private String nombreProducto;
	private int cantidad;
	private double precioUnitario;
	private double subtotal;

	public DetallePedidoDTO() {
	}

	public Long getIdDetalle() {
		return idDetalle;
	}

	public void setIdDetalle(Long idDetalle) {
		this.idDetalle = idDetalle;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public double getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	@Override
	public String toString() {
		return "DetallePedidoDTO [idDetalle=" + idDetalle + ", nombreProducto=" + nombreProducto + ", cantidad="
				+ cantidad + ", subtotal=" + subtotal + "]";
	}
}
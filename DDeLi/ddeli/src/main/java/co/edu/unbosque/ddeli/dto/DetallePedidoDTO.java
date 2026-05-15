
package co.edu.unbosque.ddeli.dto;

import java.util.List;

public class DetallePedidoDTO {

	private Long idDetalle;
	private Long idPedido;
	private String nombreProducto;
	private String imagenProducto;
	private int cantidad;
	private double precioUnitario;
	private double subtotal;
	private List<String> nombresOpciones;

	public DetallePedidoDTO() {
	}

	public Long getIdDetalle() {
		return idDetalle;
	}

	public void setIdDetalle(Long idDetalle) {
		this.idDetalle = idDetalle;
	}

	public Long getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(Long idPedido) {
		this.idPedido = idPedido;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public String getImagenProducto() {
		return imagenProducto;
	}

	public void setImagenProducto(String imagenProducto) {
		this.imagenProducto = imagenProducto;
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

	public List<String> getNombresOpciones() {
		return nombresOpciones;
	}

	public void setNombresOpciones(List<String> nombresOpciones) {
		this.nombresOpciones = nombresOpciones;
	}

	@Override
	public String toString() {
		return "DetallePedidoDTO [idDetalle=" + idDetalle + ", nombreProducto=" + nombreProducto + ", cantidad="
				+ cantidad + ", subtotal=" + subtotal + "]";
	}
}
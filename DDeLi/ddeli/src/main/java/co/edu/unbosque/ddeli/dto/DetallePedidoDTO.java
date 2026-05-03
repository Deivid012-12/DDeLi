package co.edu.unbosque.ddeli.dto;

import java.util.ArrayList;
import java.util.Objects;

import co.edu.unbosque.ddeli.entity.OpcionPersonalizacion;

public class DetallePedidoDTO {

	private Long idDetalle;
	private int cantidad;
	private double precioUnitario;
	private double subtotal;
	private PedidoDTO pedido;
	private ProductoDTO producto;
	private ArrayList<OpcionPersonalizacion> opciones;

	public DetallePedidoDTO() {
		// TODO Auto-generated constructor stub
	}

	public DetallePedidoDTO(Long idDetalle, int cantidad, double precioUnitario, double subtotal, PedidoDTO pedido,
			ProductoDTO producto, ArrayList<OpcionPersonalizacion> opciones) {
		super();
		this.idDetalle = idDetalle;
		this.cantidad = cantidad;
		this.precioUnitario = precioUnitario;
		this.subtotal = subtotal;
		this.pedido = pedido;
		this.producto = producto;
		this.opciones = opciones;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cantidad, idDetalle, opciones, pedido, precioUnitario, producto, subtotal);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DetallePedidoDTO other = (DetallePedidoDTO) obj;
		return cantidad == other.cantidad && Objects.equals(idDetalle, other.idDetalle)
				&& Objects.equals(opciones, other.opciones) && Objects.equals(pedido, other.pedido)
				&& Double.doubleToLongBits(precioUnitario) == Double.doubleToLongBits(other.precioUnitario)
				&& Objects.equals(producto, other.producto)
				&& Double.doubleToLongBits(subtotal) == Double.doubleToLongBits(other.subtotal);
	}

	/**
	 * @return the idDetalle
	 */
	public Long getIdDetalle() {
		return idDetalle;
	}

	/**
	 * @param idDetalle the idDetalle to set
	 */
	public void setIdDetalle(Long idDetalle) {
		this.idDetalle = idDetalle;
	}

	/**
	 * @return the cantidad
	 */
	public int getCantidad() {
		return cantidad;
	}

	/**
	 * @param cantidad the cantidad to set
	 */
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * @return the precioUnitario
	 */
	public double getPrecioUnitario() {
		return precioUnitario;
	}

	/**
	 * @param precioUnitario the precioUnitario to set
	 */
	public void setPrecioUnitario(double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	/**
	 * @return the subtotal
	 */
	public double getSubtotal() {
		return subtotal;
	}

	/**
	 * @param subtotal the subtotal to set
	 */
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
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

	/**
	 * @return the producto
	 */
	public ProductoDTO getProducto() {
		return producto;
	}

	/**
	 * @param producto the producto to set
	 */
	public void setProducto(ProductoDTO producto) {
		this.producto = producto;
	}

	/**
	 * @return the opciones
	 */
	public ArrayList<OpcionPersonalizacion> getOpciones() {
		return opciones;
	}

	/**
	 * @param opciones the opciones to set
	 */
	public void setOpciones(ArrayList<OpcionPersonalizacion> opciones) {
		this.opciones = opciones;
	}

	@Override
	public String toString() {
		return "DetallePedido [idDetalle=" + idDetalle + ", cantidad=" + cantidad + ", precioUnitario=" + precioUnitario
				+ ", subtotal=" + subtotal + ", pedido=" + pedido + ", producto=" + producto + ", opciones=" + opciones
				+ "]";
	}

}

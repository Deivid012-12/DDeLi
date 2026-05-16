package co.edu.unbosque.ddeli.dto;

import java.util.List;
import java.util.Objects;

public class ItemCarritoDTO {

	private Long idItem;

	private int cantidad;

	private double precioUnitario;

	private double subtotal;

	private Long idCarrito;

	private Long idProducto;
	private ProductoDTO producto;

	private List<Long> idOpciones;
	private List<OpcionPersonalizacionDTO> opciones;

	public ItemCarritoDTO() {

	}

	public ItemCarritoDTO(Long idItem, int cantidad, double precioUnitario, double subtotal, Long idCarrito,
			Long idProducto, ProductoDTO producto) {
		super();
		this.idItem = idItem;
		this.cantidad = cantidad;
		this.precioUnitario = precioUnitario;
		this.subtotal = subtotal;
		this.idCarrito = idCarrito;
		this.idProducto = idProducto;
		this.producto = producto;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idItem, cantidad, precioUnitario, subtotal, idCarrito, idProducto);
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (getClass() != obj.getClass())
			return false;

		ItemCarritoDTO other = (ItemCarritoDTO) obj;

		return Objects.equals(idItem, other.idItem);
	}

	public Long getIdItem() {
		return idItem;
	}

	public void setIdItem(Long idItem) {
		this.idItem = idItem;
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

	public Long getIdCarrito() {
		return idCarrito;
	}

	public void setIdCarrito(Long idCarrito) {
		this.idCarrito = idCarrito;
	}

	public Long getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Long idProducto) {
		this.idProducto = idProducto;
	}

	public ProductoDTO getProducto() {
		return producto;
	}

	public void setProducto(ProductoDTO producto) {
		this.producto = producto;
	}

	public List<Long> getIdOpciones() {
		return idOpciones;
	}

	public void setIdOpciones(List<Long> idOpciones) {
		this.idOpciones = idOpciones;
	}

	public List<OpcionPersonalizacionDTO> getOpciones() {
		return opciones;
	}

	public void setOpciones(List<OpcionPersonalizacionDTO> opciones) {
		this.opciones = opciones;
	}

	@Override
	public String toString() {
		return "ItemCarritoDTO [idItem=" + idItem + ", cantidad=" + cantidad + ", precioUnitario=" + precioUnitario
				+ ", subtotal=" + subtotal + ", idCarrito=" + idCarrito + ", idProducto=" + idProducto + "]";
	}
}
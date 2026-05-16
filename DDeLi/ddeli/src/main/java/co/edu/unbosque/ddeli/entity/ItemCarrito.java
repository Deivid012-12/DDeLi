package co.edu.unbosque.ddeli.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class ItemCarrito {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idItem;

	private int cantidad;
	private double precioUnitario;
	private double subtotal;

	@ManyToOne
	@JoinColumn(name = "id_carrito")
	private Carrito carrito;

	@ManyToOne
	@JoinColumn(name = "id_producto")
	private Producto producto;

	@ManyToMany
	@JoinTable(name = "item_opcion", joinColumns = @JoinColumn(name = "id_item"), inverseJoinColumns = @JoinColumn(name = "id_opcion"))
	private List<OpcionPersonalizacion> opciones = new ArrayList<>();

	public ItemCarrito() {
	}

	public ItemCarrito(Long idItem, int cantidad, double precioUnitario, double subtotal, Carrito carrito,
			Producto producto) {
		this.idItem = idItem;
		this.cantidad = cantidad;
		this.precioUnitario = precioUnitario;
		this.subtotal = subtotal;
		this.carrito = carrito;
		this.producto = producto;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idItem, cantidad, precioUnitario, subtotal, carrito, producto);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemCarrito other = (ItemCarrito) obj;
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

	public Carrito getCarrito() {
		return carrito;
	}

	public void setCarrito(Carrito carrito) {
		this.carrito = carrito;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public List<OpcionPersonalizacion> getOpciones() {
		return opciones;
	}

	public void setOpciones(List<OpcionPersonalizacion> opciones) {
		this.opciones = opciones;
	}

	@Override
	public String toString() {
		return "ItemCarrito [idItem=" + idItem + ", cantidad=" + cantidad + ", precioUnitario=" + precioUnitario
				+ ", subtotal=" + subtotal + ", producto=" + producto + "]";
	}
}
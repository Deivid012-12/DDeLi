package co.edu.unbosque.ddeli.entity;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class DetallePedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idDetalle;

	private int cantidad;
	private double precioUnitario;
	private double subtotal;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "id_pedido")
	private Pedido pedido;

	@ManyToOne
	@JoinColumn(name = "id_producto")
	private Producto producto;

	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "detalle_opcion", joinColumns = @JoinColumn(name = "id_detalle"), inverseJoinColumns = @JoinColumn(name = "id_opcion"))
	private List<OpcionPersonalizacion> opciones;

	public DetallePedido() {
	}

	public DetallePedido(Long idDetalle, int cantidad, double precioUnitario, double subtotal, Pedido pedido,
			Producto producto, List<OpcionPersonalizacion> opciones) {
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
		return Objects.hash(idDetalle);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DetallePedido other = (DetallePedido) obj;
		return Objects.equals(idDetalle, other.idDetalle);
	}

	public Long getIdDetalle() {
		return idDetalle;
	}

	public void setIdDetalle(Long idDetalle) {
		this.idDetalle = idDetalle;
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

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
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
		return "DetallePedido [idDetalle=" + idDetalle + ", cantidad=" + cantidad + ", precioUnitario=" + precioUnitario
				+ ", subtotal=" + subtotal + ", producto=" + producto + "]";
	}
}
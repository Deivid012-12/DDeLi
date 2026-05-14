package co.edu.unbosque.ddeli.entity;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Producto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idProducto;

	private String nombre;
	private String descripcion;
	private double precioBase;
	private boolean disponibilidad;
	private String tipo;
	private String imagenURL;

	@ManyToOne
	@JoinColumn(name = "id_categoria")
	private Categoria categoria;

	@JsonIgnore
	@OneToMany(mappedBy = "producto")
	private List<ItemCarrito> itemsCarrito;

	public Producto() {
	}

	public Producto(Long idProducto, String nombre, String descripcion, double precioBase, boolean disponibilidad,
			String tipo, String imagenURL, Categoria categoria, List<ItemCarrito> itemsCarrito) {
		super();
		this.idProducto = idProducto;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precioBase = precioBase;
		this.disponibilidad = disponibilidad;
		this.tipo = tipo;
		this.imagenURL = imagenURL;
		this.categoria = categoria;
		this.itemsCarrito = itemsCarrito;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idProducto, nombre, descripcion, precioBase, disponibilidad, tipo, categoria);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Producto other = (Producto) obj;
		return Objects.equals(idProducto, other.idProducto);
	}

	public Long getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Long idProducto) {
		this.idProducto = idProducto;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public double getPrecioBase() {
		return precioBase;
	}

	public void setPrecioBase(double precioBase) {
		this.precioBase = precioBase;
	}

	public boolean isDisponibilidad() {
		return disponibilidad;
	}

	public void setDisponibilidad(boolean disponibilidad) {
		this.disponibilidad = disponibilidad;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public List<ItemCarrito> getItemsCarrito() {
		return itemsCarrito;
	}

	public void setItemsCarrito(List<ItemCarrito> itemsCarrito) {
		this.itemsCarrito = itemsCarrito;
	}

	public String getImagenURL() {
		return imagenURL;
	}

	public void setImagenURL(String imagenURL) {
		this.imagenURL = imagenURL;
	}

	@Override
	public String toString() {
		return "Producto [idProducto=" + idProducto + ", nombre=" + nombre + ", descripcion=" + descripcion
				+ ", precioBase=" + precioBase + ", disponibilidad=" + disponibilidad + ", tipo=" + tipo
				+ ", imagenURL=" + imagenURL + ", categoria=" + categoria + ", itemsCarrito=" + itemsCarrito + "]";
	}


}
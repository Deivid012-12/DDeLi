package co.edu.unbosque.ddeli.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Producto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idProducto;

	private String nombre;
	private String descripcion;
	private double precioBase;
	private String imagenURL;
	private boolean disponibilidad;

	@ManyToOne
	@JoinColumn(name = "id_categoria")
	private Categoria categoria;

	@JsonIgnore
	@OneToMany(mappedBy = "producto")
	private List<ItemCarrito> itemsCarrito;

	public Producto() {
	}

	public Producto(Long idProducto, String nombre, String descripcion, double precioBase, String imagenURL,
			boolean disponibilidad, Categoria categoria, List<ItemCarrito> itemsCarrito) {
		super();
		this.idProducto = idProducto;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precioBase = precioBase;
		this.imagenURL = imagenURL;
		this.disponibilidad = disponibilidad;
		this.categoria = categoria;
		this.itemsCarrito = itemsCarrito;
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

	public String getImagenURL() {
		return imagenURL;
	}

	public void setImagenURL(String imagenURL) {
		this.imagenURL = imagenURL;
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

	public boolean isDisponibilidad() {
		return disponibilidad;
	}

	public void setDisponibilidad(boolean disponibilidad) {
		this.disponibilidad = disponibilidad;
	}

}
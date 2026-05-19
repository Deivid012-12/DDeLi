package co.edu.unbosque.ddeli.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
public class PostrePersonalizado extends Producto {

	private int maximoOpciones;

	@ManyToMany
	@JoinTable(name = "producto_opcion", joinColumns = @JoinColumn(name = "id_producto"), inverseJoinColumns = @JoinColumn(name = "id_opcion"))
	private List<OpcionPersonalizacion> opciones = new ArrayList<>();

	public PostrePersonalizado() {

	}

	public PostrePersonalizado(Long idProducto, String nombre, String descripcion, double precioBase, String imagenURL,
			boolean disponibilidad, Categoria categoria, List<ItemCarrito> itemsCarrito, int maximoOpciones,
			List<OpcionPersonalizacion> opciones) {
		super(idProducto, nombre, descripcion, precioBase, imagenURL, disponibilidad, categoria, itemsCarrito);
		this.maximoOpciones = maximoOpciones;
		this.opciones = opciones;
	}

	public PostrePersonalizado(Long idProducto, String nombre, String descripcion, double precioBase, String imagenURL,
			boolean disponibilidad, Categoria categoria, List<ItemCarrito> itemsCarrito) {
		super(idProducto, nombre, descripcion, precioBase, imagenURL, disponibilidad, categoria, itemsCarrito);
		// TODO Auto-generated constructor stub
	}

	public PostrePersonalizado(int maximoOpciones, List<OpcionPersonalizacion> opciones) {
		super();
		this.maximoOpciones = maximoOpciones;
		this.opciones = opciones;
	}

	public int getMaximoOpciones() {
		return maximoOpciones;
	}

	public void setMaximoOpciones(int maximoOpciones) {
		this.maximoOpciones = maximoOpciones;
	}

	public List<OpcionPersonalizacion> getOpciones() {
		return opciones;
	}

	public void setOpciones(List<OpcionPersonalizacion> opciones) {
		this.opciones = opciones;
	}
}
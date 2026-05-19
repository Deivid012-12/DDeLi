package co.edu.unbosque.ddeli.entity;

import java.util.List;

import jakarta.persistence.Entity;

@Entity
public class PostrePredeterminado extends Producto {

	private String estiloBase;

	public PostrePredeterminado() {

	}

	public PostrePredeterminado(Long idProducto, String nombre, String descripcion, double precioBase, String imagenURL,
			boolean disponibilidad, Categoria categoria, List<ItemCarrito> itemsCarrito, String estiloBase) {
		super(idProducto, nombre, descripcion, precioBase, imagenURL, disponibilidad, categoria, itemsCarrito);
		this.estiloBase = estiloBase;
	}

	public PostrePredeterminado(Long idProducto, String nombre, String descripcion, double precioBase, String imagenURL,
			boolean disponibilidad, Categoria categoria, List<ItemCarrito> itemsCarrito) {
		super(idProducto, nombre, descripcion, precioBase, imagenURL, disponibilidad, categoria, itemsCarrito);

	}

	public PostrePredeterminado(String estiloBase) {
		super();
		this.estiloBase = estiloBase;
	}

	public String getEstiloBase() {
		return estiloBase;
	}

	public void setEstiloBase(String estiloBase) {
		this.estiloBase = estiloBase;
	}
}
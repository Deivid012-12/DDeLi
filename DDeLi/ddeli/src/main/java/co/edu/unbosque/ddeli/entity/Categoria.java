package co.edu.unbosque.ddeli.entity;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Categoria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idCategoria;

	private String nombre;
	private String descripcion;

	@JsonIgnore
	@OneToMany(mappedBy = "categoria")
	private List<Producto> productos;

	public Categoria() {
	}

	public Categoria(Long idCategoria, String nombre, String descripcion) {
		this.idCategoria = idCategoria;
		this.nombre = nombre;
		this.descripcion = descripcion;
	}

	@Override
	public int hashCode() {
		return Objects.hash(descripcion, idCategoria, nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Categoria))
			return false;

		Categoria other = (Categoria) obj;

		return Objects.equals(idCategoria, other.idCategoria);
	}

	public Long getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
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

	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}

	@Override
	public String toString() {
		return "Categoria [idCategoria=" + idCategoria + ", nombre=" + nombre + ", descripcion=" + descripcion + "]";
	}
}
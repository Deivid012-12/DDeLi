// CategoriaDTO.java
package co.edu.unbosque.ddeli.dto;

import java.util.Objects;

public class CategoriaDTO {

	private Long idCategoria;
	private String nombre;
	private String descripcion;

	public CategoriaDTO() {
	}

	public CategoriaDTO(Long idCategoria, String nombre, String descripcion) {
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
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CategoriaDTO other = (CategoriaDTO) obj;
		return Objects.equals(descripcion, other.descripcion) && Objects.equals(idCategoria, other.idCategoria)
				&& Objects.equals(nombre, other.nombre);
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

	@Override
	public String toString() {
		return "CategoriaDTO [idCategoria=" + idCategoria + ", nombre=" + nombre + ", descripcion=" + descripcion + "]";
	}
}
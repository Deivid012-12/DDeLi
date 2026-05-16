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
public class TipoPersonalizacion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idTipo;

	private String nombre;

	@JsonIgnore
	@OneToMany(mappedBy = "tipoPersonalizacion")
	private List<OpcionPersonalizacion> opciones;

	public TipoPersonalizacion() {
	}

	public TipoPersonalizacion(Long idTipo, String nombre) {
		this.idTipo = idTipo;
		this.nombre = nombre;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idTipo, nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TipoPersonalizacion other = (TipoPersonalizacion) obj;
		return Objects.equals(idTipo, other.idTipo);
	}

	public Long getIdTipo() {
		return idTipo;
	}

	public void setIdTipo(Long idTipo) {
		this.idTipo = idTipo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<OpcionPersonalizacion> getOpciones() {
		return opciones;
	}

	public void setOpciones(List<OpcionPersonalizacion> opciones) {
		this.opciones = opciones;
	}

	@Override
	public String toString() {
		return "TipoPersonalizacion [idTipo=" + idTipo + ", nombre=" + nombre + "]";
	}
}
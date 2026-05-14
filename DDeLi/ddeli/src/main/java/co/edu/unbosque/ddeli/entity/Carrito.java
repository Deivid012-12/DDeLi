package co.edu.unbosque.ddeli.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Carrito {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idCarrito;

	private String estado;
	private LocalDate fechaCreacion;

	@OneToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;

	@JsonIgnore
	@OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ItemCarrito> items;

	public Carrito() {
	}

	public Carrito(Long idCarrito, String estado, LocalDate fechaCreacion, Usuario usuario) {
		this.idCarrito = idCarrito;
		this.estado = estado;
		this.fechaCreacion = fechaCreacion;
		this.usuario = usuario;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idCarrito, estado, fechaCreacion, usuario);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Carrito other = (Carrito) obj;
		return Objects.equals(idCarrito, other.idCarrito);
	}

	public Long getIdCarrito() {
		return idCarrito;
	}

	public void setIdCarrito(Long idCarrito) {
		this.idCarrito = idCarrito;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public LocalDate getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDate fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<ItemCarrito> getItems() {
		return items;
	}

	public void setItems(List<ItemCarrito> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "Carrito [idCarrito=" + idCarrito + ", estado=" + estado + ", fechaCreacion=" + fechaCreacion
				+ ", usuario=" + usuario + "]";
	}
}
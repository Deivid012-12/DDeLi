package co.edu.unbosque.ddeli.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class CarritoDTO {

	private Long idCarrito;

	private String estado;

	private LocalDate fechaCreacion;

	private Long idUsuario;

	private List<ItemCarritoDTO> items;


	public CarritoDTO() {

	}

	public CarritoDTO(Long idCarrito, String estado, LocalDate fechaCreacion, Long idUsuario,
			List<ItemCarritoDTO> items, ProductoDTO producto) {
		super();
		this.idCarrito = idCarrito;
		this.estado = estado;
		this.fechaCreacion = fechaCreacion;
		this.idUsuario = idUsuario;
		this.items = items;
	
	}

	@Override
	public int hashCode() {
		return Objects.hash(idCarrito, estado, fechaCreacion, idUsuario, items);
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (getClass() != obj.getClass())
			return false;

		CarritoDTO other = (CarritoDTO) obj;

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

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public List<ItemCarritoDTO> getItems() {
		return items;
	}

	public void setItems(List<ItemCarritoDTO> items) {
		this.items = items;
	}


	@Override
	public String toString() {
		return "CarritoDTO [idCarrito=" + idCarrito + ", estado=" + estado + ", fechaCreacion=" + fechaCreacion
				+ ", idUsuario=" + idUsuario + ", items=" + items + "]";
	}
}
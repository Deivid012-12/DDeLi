package co.edu.unbosque.ddeli.dto;

public class TipoPersonalizacionDTO {

	private Long idTipo;
	private String nombre;

	public TipoPersonalizacionDTO() {
	}

	public TipoPersonalizacionDTO(Long idTipo, String nombre) {
		this.idTipo = idTipo;
		this.nombre = nombre;
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

	@Override
	public String toString() {
		return "TipoPersonalizacionDTO [idTipo=" + idTipo + ", nombre=" + nombre + "]";
	}
}
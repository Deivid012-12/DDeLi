package co.edu.unbosque.ddeli.dto;

public class OpcionPersonalizacionDTO {

	private Long idOpcion;
	private String nombre;
	private double costoAdicional;
	private Long idTipo;
	private String nombreTipo;

	public OpcionPersonalizacionDTO() {
	}

	public OpcionPersonalizacionDTO(Long idOpcion, String nombre, double costoAdicional, Long idTipo,
			String nombreTipo) {
		this.idOpcion = idOpcion;
		this.nombre = nombre;
		this.costoAdicional = costoAdicional;
		this.idTipo = idTipo;
		this.nombreTipo = nombreTipo;
	}

	public Long getIdOpcion() {
		return idOpcion;
	}

	public void setIdOpcion(Long idOpcion) {
		this.idOpcion = idOpcion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getCostoAdicional() {
		return costoAdicional;
	}

	public void setCostoAdicional(double costoAdicional) {
		this.costoAdicional = costoAdicional;
	}

	public Long getIdTipo() {
		return idTipo;
	}

	public void setIdTipo(Long idTipo) {
		this.idTipo = idTipo;
	}

	public String getNombreTipo() {
		return nombreTipo;
	}

	public void setNombreTipo(String nombreTipo) {
		this.nombreTipo = nombreTipo;
	}

	@Override
	public String toString() {
		return "OpcionPersonalizacionDTO [idOpcion=" + idOpcion + ", nombre=" + nombre + ", costoAdicional="
				+ costoAdicional + ", nombreTipo=" + nombreTipo + "]";
	}
}
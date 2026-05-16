package co.edu.unbosque.ddeli.dto;

public class DireccionDTO {

	private Long idDireccion;
	private String calle;
	private String ciudad;
	private String codigoPostal;
	private String departamento;
	private String indicaciones;
	private Long idUsuario;

	public DireccionDTO() {
	}

	public DireccionDTO(Long idDireccion, String calle, String ciudad, String codigoPostal, String departamento,
			String indicaciones, Long idUsuario) {
		this.idDireccion = idDireccion;
		this.calle = calle;
		this.ciudad = ciudad;
		this.codigoPostal = codigoPostal;
		this.departamento = departamento;
		this.indicaciones = indicaciones;
		this.idUsuario = idUsuario;
	}

	public Long getIdDireccion() {
		return idDireccion;
	}

	public void setIdDireccion(Long idDireccion) {
		this.idDireccion = idDireccion;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getIndicaciones() {
		return indicaciones;
	}

	public void setIndicaciones(String indicaciones) {
		this.indicaciones = indicaciones;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	@Override
	public String toString() {
		return "DireccionDTO [idDireccion=" + idDireccion + ", calle=" + calle + ", ciudad=" + ciudad
				+ ", departamento=" + departamento + ", indicaciones=" + indicaciones + "]";
	}
}
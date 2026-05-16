package co.edu.unbosque.ddeli.dto;

import java.time.LocalDate;

public class EnvioDTO {

	private Long idEnvio;
	private String estado;
	private String tipoEntrega;
	private LocalDate fechaEnvio;
	private Long idPedido;
	private Long idDireccion;
	
	public EnvioDTO() {
	}

	public EnvioDTO(Long idEnvio, String estado, String tipoEntrega, LocalDate fechaEnvio, Long idPedido) {
		this.idEnvio = idEnvio;
		this.estado = estado;
		this.tipoEntrega = tipoEntrega;
		this.fechaEnvio = fechaEnvio;
		this.idPedido = idPedido;
	}

	public Long getIdEnvio() {
		return idEnvio;
	}

	public void setIdEnvio(Long idEnvio) {
		this.idEnvio = idEnvio;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getTipoEntrega() {
		return tipoEntrega;
	}

	public void setTipoEntrega(String tipoEntrega) {
		this.tipoEntrega = tipoEntrega;
	}

	public LocalDate getFechaEnvio() {
		return fechaEnvio;
	}

	public void setFechaEnvio(LocalDate fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	public Long getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(Long idPedido) {
		this.idPedido = idPedido;
	}

	public Long getIdDireccion() {
		return idDireccion;
	}

	public void setIdDireccion(Long idDireccion) {
		this.idDireccion = idDireccion;
	}

	@Override
	public String toString() {
		return "EnvioDTO [idEnvio=" + idEnvio + ", estado=" + estado + ", tipoEntrega=" + tipoEntrega + ", fechaEnvio="
				+ fechaEnvio + ", idPedido=" + idPedido + "]";
	}
}
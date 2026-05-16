package co.edu.unbosque.ddeli.dto;

import java.time.LocalDate;

public class EventoDTO {

	private Long idEvento;
	private LocalDate fechaEvento;
	private int numeroPersonas;
	private String tipoEvento;
	private Long idUsuario;

	public EventoDTO() {
	}

	public EventoDTO(Long idEvento, LocalDate fechaEvento, int numeroPersonas, String tipoEvento, Long idUsuario) {
		this.idEvento = idEvento;
		this.fechaEvento = fechaEvento;
		this.numeroPersonas = numeroPersonas;
		this.tipoEvento = tipoEvento;
		this.idUsuario = idUsuario;
	}

	public Long getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(Long idEvento) {
		this.idEvento = idEvento;
	}

	public LocalDate getFechaEvento() {
		return fechaEvento;
	}

	public void setFechaEvento(LocalDate fechaEvento) {
		this.fechaEvento = fechaEvento;
	}

	public int getNumeroPersonas() {
		return numeroPersonas;
	}

	public void setNumeroPersonas(int numeroPersonas) {
		this.numeroPersonas = numeroPersonas;
	}

	public String getTipoEvento() {
		return tipoEvento;
	}

	public void setTipoEvento(String tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	@Override
	public String toString() {
		return "EventoDTO [idEvento=" + idEvento + ", fechaEvento=" + fechaEvento + ", numeroPersonas=" + numeroPersonas
				+ ", tipoEvento=" + tipoEvento + ", idUsuario=" + idUsuario + "]";
	}
}
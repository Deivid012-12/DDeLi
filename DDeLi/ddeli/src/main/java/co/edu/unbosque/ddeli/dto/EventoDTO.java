package co.edu.unbosque.ddeli.dto;

import java.time.LocalDate;
import java.util.Objects;

public class EventoDTO {

	private Long idEvento;

	private LocalDate fechaEvento;
	private int numeroPersonas;
	private String tipoEvento;
	private UsuarioDTO usuario;

	public EventoDTO() {
		// TODO Auto-generated constructor stub
	}

	public EventoDTO(Long idEvento, LocalDate fechaEvento, int numeroPersonas, String tipoEvento, UsuarioDTO cliente) {
		super();
		this.idEvento = idEvento;
		this.fechaEvento = fechaEvento;
		this.numeroPersonas = numeroPersonas;
		this.tipoEvento = tipoEvento;
		this.usuario = cliente;
	}

	@Override
	public int hashCode() {
		return Objects.hash(usuario, fechaEvento, idEvento, numeroPersonas, tipoEvento);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EventoDTO other = (EventoDTO) obj;
		return Objects.equals(usuario, other.usuario) && Objects.equals(fechaEvento, other.fechaEvento)
				&& Objects.equals(idEvento, other.idEvento) && numeroPersonas == other.numeroPersonas
				&& Objects.equals(tipoEvento, other.tipoEvento);
	}

	/**
	 * @return the idEvento
	 */
	public Long getIdEvento() {
		return idEvento;
	}

	/**
	 * @param idEvento the idEvento to set
	 */
	public void setIdEvento(Long idEvento) {
		this.idEvento = idEvento;
	}

	/**
	 * @return the fechaEvento
	 */
	public LocalDate getFechaEvento() {
		return fechaEvento;
	}

	/**
	 * @param fechaEvento the fechaEvento to set
	 */
	public void setFechaEvento(LocalDate fechaEvento) {
		this.fechaEvento = fechaEvento;
	}

	/**
	 * @return the numeroPersonas
	 */
	public int getNumeroPersonas() {
		return numeroPersonas;
	}

	/**
	 * @param numeroPersonas the numeroPersonas to set
	 */
	public void setNumeroPersonas(int numeroPersonas) {
		this.numeroPersonas = numeroPersonas;
	}

	/**
	 * @return the tipoEvento
	 */
	public String getTipoEvento() {
		return tipoEvento;
	}

	/**
	 * @param tipoEvento the tipoEvento to set
	 */
	public void setTipoEvento(String tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	/**
	 * @return the cliente
	 */
	public UsuarioDTO getUsuario() {
		return usuario;
	}

	/**
	 * @param cliente the cliente to set
	 */
	public void setUsuario(UsuarioDTO cliente) {
		this.usuario = cliente;
	}

	@Override
	public String toString() {
		return "Evento [idEvento=" + idEvento + ", fechaEvento=" + fechaEvento + ", numeroPersonas=" + numeroPersonas
				+ ", tipoEvento=" + tipoEvento + ", cliente=" + usuario + "]";
	}

}

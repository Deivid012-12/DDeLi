package co.edu.unbosque.ddeli.entity;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Evento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idEvento;
	private LocalDate fechaEvento;
	private int numeroPersonas;
	private String tipoEvento;

	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;

	public Evento() {
		// TODO Auto-generated constructor stub
	}

	public Evento(Long idEvento, LocalDate fechaEvento, int numeroPersonas, String tipoEvento, Usuario cliente) {
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
		Evento other = (Evento) obj;
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
	 * @return the usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * @param cliente the usuario to set
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		return "Evento [idEvento=" + idEvento + ", fechaEvento=" + fechaEvento + ", numeroPersonas=" + numeroPersonas
				+ ", tipoEvento=" + tipoEvento + ", usuario=" + usuario + "]";
	}

}

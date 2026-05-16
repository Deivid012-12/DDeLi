package co.edu.unbosque.ddeli.entity;

import java.time.LocalDate;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Envio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idEnvio;

	private String estado;
	private String tipoEntrega;
	private LocalDate fechaEnvio;

	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "id_pedido")
	private Pedido pedido;

	@ManyToOne
	@JoinColumn(name = "id_direccion")
	private Direccion direccion;

	public Envio() {
		// TODO Auto-generated constructor stub
	}

	public Envio(Long idEnvio, String estado, String tipoEntrega, LocalDate fechaEnvio, Pedido pedido) {
		super();
		this.idEnvio = idEnvio;
		this.estado = estado;
		this.tipoEntrega = tipoEntrega;
		this.fechaEnvio = fechaEnvio;
		this.pedido = pedido;
	}

	@Override
	public int hashCode() {
		return Objects.hash(estado, fechaEnvio, idEnvio, pedido, tipoEntrega);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Envio other = (Envio) obj;
		return Objects.equals(estado, other.estado) && Objects.equals(fechaEnvio, other.fechaEnvio)
				&& Objects.equals(idEnvio, other.idEnvio) && Objects.equals(pedido, other.pedido)
				&& Objects.equals(tipoEntrega, other.tipoEntrega);
	}

	/**
	 * @return the idEnvio
	 */
	public Long getIdEnvio() {
		return idEnvio;
	}

	/**
	 * @param idEnvio the idEnvio to set
	 */
	public void setIdEnvio(Long idEnvio) {
		this.idEnvio = idEnvio;
	}

	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * @return the tipoEntrega
	 */
	public String getTipoEntrega() {
		return tipoEntrega;
	}

	/**
	 * @param tipoEntrega the tipoEntrega to set
	 */
	public void setTipoEntrega(String tipoEntrega) {
		this.tipoEntrega = tipoEntrega;
	}

	/**
	 * @return the fechaEnvio
	 */
	public LocalDate getFechaEnvio() {
		return fechaEnvio;
	}

	/**
	 * @param fechaEnvio the fechaEnvio to set
	 */
	public void setFechaEnvio(LocalDate fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	/**
	 * @return the pedido
	 */
	public Pedido getPedido() {
		return pedido;
	}

	/**
	 * @param pedido the pedido to set
	 */
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	

	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	@Override
	public String toString() {
		return "Envio [idEnvio=" + idEnvio + ", estado=" + estado + ", tipoEntrega=" + tipoEntrega + ", fechaEnvio="
				+ fechaEnvio + ", pedido=" + pedido + "]";
	}

}
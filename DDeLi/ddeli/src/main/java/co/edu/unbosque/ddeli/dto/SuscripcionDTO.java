package co.edu.unbosque.ddeli.dto;

import java.time.LocalDate;
import java.util.Objects;

public class SuscripcionDTO {

	private Long idSuscripcion;

	private LocalDate fechaInicio;
	private String estado;
	private PlanSuscripcionDTO plan;

	public SuscripcionDTO() {
		// TODO Auto-generated constructor stub
	}

	public SuscripcionDTO(Long idSuscripcion, LocalDate fechaInicio, String estado, PlanSuscripcionDTO plan) {
		super();
		this.idSuscripcion = idSuscripcion;
		this.fechaInicio = fechaInicio;
		this.estado = estado;
		this.plan = plan;
	}

	@Override
	public int hashCode() {
		return Objects.hash(estado, fechaInicio, idSuscripcion, plan);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SuscripcionDTO other = (SuscripcionDTO) obj;
		return Objects.equals(estado, other.estado) && Objects.equals(fechaInicio, other.fechaInicio)
				&& Objects.equals(idSuscripcion, other.idSuscripcion) && Objects.equals(plan, other.plan);
	}

	/**
	 * @return the idSuscripcion
	 */
	public Long getIdSuscripcion() {
		return idSuscripcion;
	}

	/**
	 * @param idSuscripcion the idSuscripcion to set
	 */
	public void setIdSuscripcion(Long idSuscripcion) {
		this.idSuscripcion = idSuscripcion;
	}

	/**
	 * @return the fechaInicio
	 */
	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * @param fechaInicio the fechaInicio to set
	 */
	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
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
	 * @return the plan
	 */
	public PlanSuscripcionDTO getPlan() {
		return plan;
	}

	/**
	 * @param plan the plan to set
	 */
	public void setPlan(PlanSuscripcionDTO plan) {
		this.plan = plan;
	}

	@Override
	public String toString() {
		return "Suscripcion [idSuscripcion=" + idSuscripcion + ", fechaInicio=" + fechaInicio + ", estado=" + estado
				+ ", plan=" + plan + "]";
	}

}
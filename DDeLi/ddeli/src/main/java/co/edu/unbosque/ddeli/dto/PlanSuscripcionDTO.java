// PlanSuscripcionDTO.java
package co.edu.unbosque.ddeli.dto;

public class PlanSuscripcionDTO {

	private Long idPlan;
	private String nombre;
	private double precioMensual;
	private double costoAdicional;

	public PlanSuscripcionDTO() {
	}

	public PlanSuscripcionDTO(Long idPlan, String nombre, double precioMensual, double costoAdicional) {
		this.idPlan = idPlan;
		this.nombre = nombre;
		this.precioMensual = precioMensual;
		this.costoAdicional = costoAdicional;
	}

	public Long getIdPlan() {
		return idPlan;
	}

	public void setIdPlan(Long idPlan) {
		this.idPlan = idPlan;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getPrecioMensual() {
		return precioMensual;
	}

	public void setPrecioMensual(double precioMensual) {
		this.precioMensual = precioMensual;
	}

	public double getCostoAdicional() {
		return costoAdicional;
	}

	public void setCostoAdicional(double costoAdicional) {
		this.costoAdicional = costoAdicional;
	}

	@Override
	public String toString() {
		return "PlanSuscripcionDTO [idPlan=" + idPlan + ", nombre=" + nombre + ", precioMensual=" + precioMensual
				+ ", costoAdicional=" + costoAdicional + "]";
	}
}
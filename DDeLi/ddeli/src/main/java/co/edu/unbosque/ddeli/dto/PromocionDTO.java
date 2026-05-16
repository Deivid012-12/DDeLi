package co.edu.unbosque.ddeli.dto;

import java.time.LocalDate;

public class PromocionDTO {

	private Long idPromocion;
	private String nombre;
	private double porcentajeDescuento;
	private LocalDate fechaInicio;
	private LocalDate fechaFin;

	public PromocionDTO() {
	}

	public PromocionDTO(Long idPromocion, String nombre, double porcentajeDescuento, LocalDate fechaInicio,
			LocalDate fechaFin) {
		this.idPromocion = idPromocion;
		this.nombre = nombre;
		this.porcentajeDescuento = porcentajeDescuento;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
	}

	public Long getIdPromocion() {
		return idPromocion;
	}

	public void setIdPromocion(Long idPromocion) {
		this.idPromocion = idPromocion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getPorcentajeDescuento() {
		return porcentajeDescuento;
	}

	public void setPorcentajeDescuento(double porcentajeDescuento) {
		this.porcentajeDescuento = porcentajeDescuento;
	}

	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDate getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDate fechaFin) {
		this.fechaFin = fechaFin;
	}

	@Override
	public String toString() {
		return "PromocionDTO [idPromocion=" + idPromocion + ", nombre=" + nombre + ", porcentajeDescuento="
				+ porcentajeDescuento + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + "]";
	}
}
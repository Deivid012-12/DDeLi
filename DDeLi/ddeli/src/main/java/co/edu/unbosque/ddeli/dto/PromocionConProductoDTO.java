package co.edu.unbosque.ddeli.dto;

import java.time.LocalDate;
import java.util.List;

public class PromocionConProductoDTO {
	private Long idPromocion;
	private String nombre;
	private double porcentajeDescuento;
	private LocalDate fechaInicio;
	private LocalDate fechaFin;
	private List<ProductoDTO> producto;

	public PromocionConProductoDTO() {
		// TODO Auto-generated constructor stub
	}

	public PromocionConProductoDTO(Long idPromocion, String nombre, double porcentajeDescuento, LocalDate fechaInicio,
			LocalDate fechaFin, List<ProductoDTO> producto) {
		super();
		this.idPromocion = idPromocion;
		this.nombre = nombre;
		this.porcentajeDescuento = porcentajeDescuento;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.producto = producto;
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

	public List<ProductoDTO> getProducto() {
		return producto;
	}

	public void setProducto(List<ProductoDTO> producto) {
		this.producto = producto;
	}

}

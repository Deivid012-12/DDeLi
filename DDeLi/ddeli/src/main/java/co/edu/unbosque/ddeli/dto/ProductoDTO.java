// ProductoDTO.java
package co.edu.unbosque.ddeli.dto;

public class ProductoDTO {

	private Long idProducto;
	private String nombre;
	private String descripcion;
	private double precioBase;
	private boolean disponibilidad;
	private String tipo;
	private String nombreCategoria;
	private String imagenURL;

	public ProductoDTO() {
	}

	public ProductoDTO(Long idProducto, String nombre, String descripcion, double precioBase, boolean disponibilidad,
			String tipo, String nombreCategoria, String imagenURL) {
		super();
		this.idProducto = idProducto;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precioBase = precioBase;
		this.disponibilidad = disponibilidad;
		this.tipo = tipo;
		this.nombreCategoria = nombreCategoria;
		this.imagenURL = imagenURL;
	}

	public Long getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Long idProducto) {
		this.idProducto = idProducto;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public double getPrecioBase() {
		return precioBase;
	}

	public void setPrecioBase(double precioBase) {
		this.precioBase = precioBase;
	}

	public boolean isDisponibilidad() {
		return disponibilidad;
	}

	public void setDisponibilidad(boolean disponibilidad) {
		this.disponibilidad = disponibilidad;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNombreCategoria() {
		return nombreCategoria;
	}

	public void setNombreCategoria(String nombreCategoria) {
		this.nombreCategoria = nombreCategoria;
	}

	public String getImagenURL() {
		return imagenURL;
	}

	public void setImagenURL(String imagenURL) {
		this.imagenURL = imagenURL;
	}

	@Override
	public String toString() {
		return "ProductoDTO [idProducto=" + idProducto + ", nombre=" + nombre + ", descripcion=" + descripcion
				+ ", precioBase=" + precioBase + ", disponibilidad=" + disponibilidad + ", tipo=" + tipo
				+ ", nombreCategoria=" + nombreCategoria + ", imagenURL=" + imagenURL + "]";
	}

}
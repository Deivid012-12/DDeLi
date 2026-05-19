package co.edu.unbosque.ddeli.dto;

public class ProductoDTO {

	private Long idProducto;
	private String nombre;
	private String descripcion;
	private double precioBase;
	private String tipoProducto;
	private String estiloBase;
	private int maximoOpciones;

	private boolean disponibilidad;

	private Long idCategoria;
	private String nombreCategoria;
	private String imagenURL;

	public ProductoDTO() {
	}

	public ProductoDTO(Long idProducto, String nombre, String descripcion, double precioBase, String tipoProducto,
			String estiloBase, int maximoOpciones, boolean disponibilidad, Long idCategoria, String nombreCategoria,
			String imagenURL) {
		this.idProducto = idProducto;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precioBase = precioBase;
		this.tipoProducto = tipoProducto;
		this.estiloBase = estiloBase;
		this.maximoOpciones = maximoOpciones;
		this.disponibilidad = disponibilidad;
		this.idCategoria = idCategoria;
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

	public String getTipoProducto() {
		return tipoProducto;
	}

	public void setTipoProducto(String tipoProducto) {
		this.tipoProducto = tipoProducto;
	}

	public String getEstiloBase() {
		return estiloBase;
	}

	public void setEstiloBase(String estiloBase) {
		this.estiloBase = estiloBase;
	}

	public int getMaximoOpciones() {
		return maximoOpciones;
	}

	public void setMaximoOpciones(int maximoOpciones) {
		this.maximoOpciones = maximoOpciones;
	}

	public boolean isDisponibilidad() {
		return disponibilidad;
	}

	public void setDisponibilidad(boolean disponibilidad) {
		this.disponibilidad = disponibilidad;
	}

	public Long getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
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
				+ ", precioBase=" + precioBase + ", tipoProducto=" + tipoProducto + ", estiloBase=" + estiloBase
				+ ", maximoOpciones=" + maximoOpciones + ", disponibilidad=" + disponibilidad + ", idCategoria="
				+ idCategoria + ", nombreCategoria=" + nombreCategoria + ", imagenURL=" + imagenURL + "]";
	}
}
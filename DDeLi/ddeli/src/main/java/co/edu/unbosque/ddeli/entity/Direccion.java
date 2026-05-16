package co.edu.unbosque.ddeli.entity;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Direccion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idDireccion;

	private String calle;
	private String ciudad;
	private String codigoPostal;
	private String departamento;
	private String indicaciones;

	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;

	public Direccion() {
		// TODO Auto-generated constructor stub
	}

	public Direccion(Long idDireccion, String calle, String ciudad, String codigoPostal, String departamento,
			String indicaciones, Usuario usuario) {
		super();
		this.idDireccion = idDireccion;
		this.calle = calle;
		this.ciudad = ciudad;
		this.codigoPostal = codigoPostal;
		this.departamento = departamento;
		this.indicaciones = indicaciones;
		this.usuario = usuario;
	}

	@Override
	public int hashCode() {
		return Objects.hash(calle, ciudad, codigoPostal, departamento, idDireccion, usuario);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Direccion other = (Direccion) obj;
		return Objects.equals(calle, other.calle) && Objects.equals(ciudad, other.ciudad)
				&& Objects.equals(codigoPostal, other.codigoPostal) && Objects.equals(departamento, other.departamento)
				&& Objects.equals(idDireccion, other.idDireccion) && Objects.equals(usuario, other.usuario);
	}

	public String getIndicaciones() {
		return indicaciones;
	}

	public void setIndicaciones(String indicaciones) {
		this.indicaciones = indicaciones;
	}

	/**
	 * @return the idDireccion
	 */
	public Long getIdDireccion() {
		return idDireccion;
	}

	/**
	 * @param idDireccion the idDireccion to set
	 */
	public void setIdDireccion(Long idDireccion) {
		this.idDireccion = idDireccion;
	}

	/**
	 * @return the calle
	 */
	public String getCalle() {
		return calle;
	}

	/**
	 * @param calle the calle to set
	 */
	public void setCalle(String calle) {
		this.calle = calle;
	}

	/**
	 * @return the ciudad
	 */
	public String getCiudad() {
		return ciudad;
	}

	/**
	 * @param ciudad the ciudad to set
	 */
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	/**
	 * @return the codigoPostal
	 */
	public String getCodigoPostal() {
		return codigoPostal;
	}

	/**
	 * @param codigoPostal the codigoPostal to set
	 */
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	/**
	 * @return the departamento
	 */
	public String getDepartamento() {
		return departamento;
	}

	/**
	 * @param departamento the departamento to set
	 */
	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	/**
	 * @return the cliente
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * @param cliente the cliente to set
	 */
	public void setUsuario(Usuario cliente) {
		this.usuario = cliente;
	}

	@Override
	public String toString() {
		return "Direccion [idDireccion=" + idDireccion + ", calle=" + calle + ", ciudad=" + ciudad + ", codigoPostal="
				+ codigoPostal + ", departamento=" + departamento + ", usuario=" + usuario + "]";
	}

}
package co.edu.unbosque.ddeli.dto;

import java.util.ArrayList;
import java.util.Objects;

import co.edu.unbosque.ddeli.entity.Direccion;
import co.edu.unbosque.ddeli.entity.Evento;
import co.edu.unbosque.ddeli.entity.Pedido;
import co.edu.unbosque.ddeli.entity.Suscripcion;
import co.edu.unbosque.ddeli.entity.Usuario.Role;
import io.swagger.v3.oas.annotations.media.Schema;

public class UsuarioDTO {

	private Long idUsuario;
	@Schema(hidden = true)
	private String nombre;
	private String correo;
	private String contrasenia;
	@Schema(hidden = true)
	private String telefono;
	@Schema(hidden = true)
	private Role rol;
	@Schema(hidden = true)
	private boolean verificado;
	@Schema(hidden = true)
	private int token;
	@Schema(hidden = true)
	private ArrayList<Direccion> direcciones;
	@Schema(hidden = true)
	private ArrayList<Evento> eventos;
	@Schema(hidden = true)
	private ArrayList<Pedido> pedidos;
	@Schema(hidden = true)
	private ArrayList<Suscripcion> suscripciones;

	public UsuarioDTO() {
	}

	public UsuarioDTO(Long id, String nombreUsuario, String correo, String contrasenia, String telefono, Role rol,
			boolean verificado, int token, ArrayList<Direccion> direcciones, ArrayList<Evento> eventos,
			ArrayList<Pedido> pedidos, ArrayList<Suscripcion> suscripciones) {

		this.idUsuario = id;
		this.nombre = nombreUsuario;
		this.correo = correo;
		this.contrasenia = contrasenia;
		this.telefono = telefono;
		this.rol = rol;
		this.verificado = verificado;
		this.token = token;
		this.direcciones = direcciones;
		this.eventos = eventos;
		this.pedidos = pedidos;
		this.suscripciones = suscripciones;
	}

	@Override
	public int hashCode() {
		return Objects.hash(correo, idUsuario, nombre, telefono);
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (getClass() != obj.getClass())
			return false;

		UsuarioDTO other = (UsuarioDTO) obj;

		return Objects.equals(correo, other.correo) && Objects.equals(idUsuario, other.idUsuario)
				&& Objects.equals(nombre, other.nombre) && Objects.equals(telefono, other.telefono);
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Role getRol() {
		return rol;
	}

	public void setRol(Role rol) {
		this.rol = rol;
	}

	public boolean isVerificado() {
		return verificado;
	}

	public void setVerificado(boolean verificado) {
		this.verificado = verificado;
	}

	public int getToken() {
		return token;
	}

	public void setToken(int token) {
		this.token = token;
	}

	public ArrayList<Direccion> getDirecciones() {
		return direcciones;
	}

	public void setDirecciones(ArrayList<Direccion> direcciones) {
		this.direcciones = direcciones;
	}

	public ArrayList<Evento> getEventos() {
		return eventos;
	}

	public void setEventos(ArrayList<Evento> eventos) {
		this.eventos = eventos;
	}

	public ArrayList<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(ArrayList<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	public ArrayList<Suscripcion> getSuscripciones() {
		return suscripciones;
	}

	public void setSuscripciones(ArrayList<Suscripcion> suscripciones) {
		this.suscripciones = suscripciones;
	}

	@Override
	public String toString() {
		return "UsuarioDTO [id=" + idUsuario + ", nombreUsuario=" + nombre + ", correo=" + correo + ", telefono="
				+ telefono + ", role=" + rol + "]";
	}

}
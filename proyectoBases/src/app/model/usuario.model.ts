export interface Usuario {
  correo: string;
  nombre: string;
  contrasenia: string;
  telefono:string;
}

export interface UsuarioDTO {
  correo: string;
  nombre: string;
  telefono: string;
  contrasenia: string;
}

export interface UsuarioLogin {
  correo: string;
  contrasenia?: string;
}

export interface AuthResponse {
  token: string;
  role: string;
}

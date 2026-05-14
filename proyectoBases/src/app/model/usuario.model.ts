export interface Usuario {
  correo: string;
  nombreUsuario: string;
  contrasenia: string;
  telefono:string;
}

export interface UsuarioDTO {
  correo: string;
  nombreUsuario: string;
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

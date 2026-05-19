import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import {UsuarioLogin, AuthResponse, Usuario} from '../model/usuario.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8081/auth';

  constructor(private http: HttpClient) {}

  login(user: UsuarioLogin): Observable<AuthResponse> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post<AuthResponse>(`${this.apiUrl}/login`, user, { headers }).pipe(
      tap(response => {


        localStorage.setItem('token', response.token);

        localStorage.setItem('userRole', response.role);

        localStorage.setItem('nombre', response.nombre);

        localStorage.setItem(
          'usuario',
          JSON.stringify(response)
        );

        console.log('Token guardado:', response.token);
      })
    );
  }

  getUserRole(): string | null {
    return localStorage.getItem('userRole');
  }

  hasRole(role: string): boolean {
    const userRole = this.getUserRole();
    return userRole === role;
  }

  hasAnyRole(roles: string[]): boolean {
    const userRole = this.getUserRole();
    return roles.includes(userRole || '');
  }

  logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('userRole');

    console.log('Sesión cerrada - datos limpiados');
  }
  register(user: UsuarioLogin): Observable<any> {
    return this.http.post(`${this.apiUrl}/register`, user, {
      responseType: 'text'
    });
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  isLoggedIn(): boolean {
    const token = this.getToken();
    const isLogged = !!token;
    console.log('¿Está logueado?:', isLogged);
    return isLogged;
  }

  getUserInfo() {
    return {
      token: this.getToken(),
      role: this.getUserRole(),
      isLoggedIn: this.isLoggedIn()
    };
  }
  getUsuario(): any {
    const usuario = localStorage.getItem('usuario');
    return usuario ? JSON.parse(usuario) : null;
  }

  isTokenValid(): boolean {
    const token = this.getToken();
    if (!token) return false;
    return token.length > 10;
  }
  getUserName(): string | null {
    return localStorage.getItem('nombre')
  }


}

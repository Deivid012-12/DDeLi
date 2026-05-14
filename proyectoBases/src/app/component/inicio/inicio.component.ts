import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../service/auth.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { PasswordModule } from 'primeng/password';

@Component({
  selector: 'app-inicio',
  standalone: true,
  imports: [RouterModule, FormsModule, CommonModule, PasswordModule],
  templateUrl: './inicio.component.html',
  styleUrls: ['./inicio.component.css']
})
export class InicioComponent {
  loginData = {
    email: '',
    password: ''
  };
  isLoading = false;
  errorMessage = '';

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  onSubmit() {
    if (!this.loginData.email || !this.loginData.password) {
      alert('Por favor, completa todos los campos');
      return;
    }

    if (this.isLoading) return;
    this.isLoading = true;
    this.errorMessage = '';

    const loginRequest = {
      correo: this.loginData.email,
      contrasenia: this.loginData.password
    };

    this.authService.login(loginRequest).subscribe({
      next: (response) => {
        this.isLoading = false;
        console.log('Login exitoso:', response);
        this.router.navigate(['/principal']);
      },
      error: (error) => {
        this.isLoading = false;
        console.error('Error en login:', error);
        if (error.status === 401) {
          this.errorMessage = 'Credenciales incorrectas';
        } else if (error.status === 0) {
          this.errorMessage = 'Error de conexión con el servidor';
        } else {
          this.errorMessage = error.error || 'Error al iniciar sesión';
        }
      }
    });
  }
}

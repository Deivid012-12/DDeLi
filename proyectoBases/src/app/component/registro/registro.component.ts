import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../service/auth.service';
import { CommonModule } from '@angular/common';
import { PasswordModule } from 'primeng/password';

@Component({
  selector: 'app-registro',
  standalone: true,
  imports: [FormsModule, RouterModule, CommonModule, PasswordModule],
  templateUrl: './registro.component.html',
  styleUrls: ['./registro.component.css']
})
export class RegistroComponent {
  registerData = {
    username: '',
    password: '',
    email: '',
    telefono: ''
  };

  errorMessage: string = '';
  successMessage: string = '';
  isLoading: boolean = false;

  constructor(private authService: AuthService, private router: Router) {}

  onSubmit() {
    this.errorMessage = '';
    this.successMessage = '';

    if (!this.registerData.username || !this.registerData.password || !this.registerData.email || !this.registerData.telefono) {
      this.errorMessage = 'Todos los campos son obligatorios';
      return;
    }

    if (this.registerData.username.includes('>') || this.registerData.username.includes('<') || this.registerData.username.includes('/') || this.registerData.username.includes('*')) {
      this.errorMessage = 'El nombre de usuario no puede contener caracteres especiales';
      return;
    }

    if (!this.registerData.email.match('^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$')) {
      this.errorMessage = 'Correo electrónico inválido';
      return;
    }

    if (!this.registerData.telefono.match('^[0-9]{7,15}$')) {
      this.errorMessage = 'El teléfono debe contener solo números (7 a 15 dígitos)';
      return;
    }

    this.isLoading = true;

    const userToRegister = {
      nombreUsuario: this.registerData.username,
      contrasenia: this.registerData.password,
      correo: this.registerData.email,
      telefono: this.registerData.telefono
    };

    this.authService.register(userToRegister).subscribe({
      next: (response) => {
        this.isLoading = false;
        this.successMessage = 'Usuario registrado exitosamente 🎉';
        setTimeout(() => {
          this.router.navigate(['/inicio']);
        }, 100);
      },
      error: (error) => {
        this.isLoading = false;
        if (error.status === 409) {
          this.errorMessage = 'El nombre de usuario o correo ya existe';
        } else if (error.status === 400) {
          this.errorMessage = 'Datos inválidos. Verifica la información';
        } else if (error.status === 401) {
          this.errorMessage = 'No autorizado. Intenta de nuevo';
        } else {
          this.errorMessage = 'Error al registrar el usuario: ' + this.getErrorMessage(error);
        }
      }
    });
  }

  private getErrorMessage(error: any): string {
    if (typeof error === 'string') return error;
    if (error.error instanceof ProgressEvent) return 'Error de conexión con el servidor';
    return error.error || error.message || 'Error desconocido';
  }
}

import { Component, ChangeDetectorRef } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../service/auth.service';
import { CommonModule } from '@angular/common';
import { PasswordModule } from 'primeng/password';
import { HttpClient } from '@angular/common/http';

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

  // Estado de verificación
  mostrarVerificacion = false;
  codigoVerificacion = '';
  correoRegistrado = '';
  mensajeVerificacion = '';
  errorVerificacion = '';
  verificando = false;
  verificado = false;

  errorMessage = '';
  successMessage = '';
  isLoading = false;

  constructor(
    private authService: AuthService,
    private router: Router,
    private http: HttpClient,
    private cdr: ChangeDetectorRef
  ) {}

  onSubmit() {
    this.errorMessage = '';
    this.successMessage = '';

    if (!this.registerData.username || !this.registerData.password ||
      !this.registerData.email || !this.registerData.telefono) {
      this.errorMessage = 'Todos los campos son obligatorios';
      return;
    }

    if (!this.registerData.username.match(/^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]+$/)) {
      this.errorMessage = 'El nombre no debe contener caracteres especiales';
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
      nombre: this.registerData.username,
      contrasenia: this.registerData.password,
      correo: this.registerData.email,
      telefono: this.registerData.telefono
    };

    this.authService.register(userToRegister).subscribe({
      next: (response) => {
        console.log('✅ Respuesta recibida:', response);
        this.isLoading = false;
        this.correoRegistrado = this.registerData.email;
        this.mostrarVerificacion = true;
        this.cdr.detectChanges();
      },
      error: (error) => {
        this.isLoading = false;
        this.cdr.detectChanges();
        if (error.status === 409) {
          this.errorMessage = 'Ese correo ya está registrado. Intenta con otro.';
        } else if (error.status === 400) {
          this.errorMessage = 'Datos inválidos. Verifica la información.';
        } else if (error.status === 401) {
          this.errorMessage = 'No autorizado.';
        } else {
          this.errorMessage = 'Error al registrar el usuario. Intenta de nuevo.';
        }
      }
    });
  }

  verificarCuenta(): void {
    if (!this.codigoVerificacion) {
      this.errorVerificacion = 'Ingresa el código de verificación';
      return;
    }

    this.verificando = true;
    this.errorVerificacion = '';
    this.mensajeVerificacion = '';

    this.http.get(
      `http://localhost:8081/usuario/verificar?token=${this.codigoVerificacion}`,
      { responseType: 'text' }
    ).subscribe({
      next: () => {
        this.verificando = false;
        this.verificado = true;
        this.mensajeVerificacion = '¡Cuenta verificada! Redirigiendo...';
        this.cdr.detectChanges();
        setTimeout(() => this.router.navigate(['/inicio']), 2000);
      },
      error: (err) => {
        this.verificando = false;
        if (err.status === 404) {
          this.errorVerificacion = 'Código incorrecto. Verifica tu correo.';
        } else {
          this.errorVerificacion = 'Error al verificar. Intenta de nuevo.';
        }
        this.cdr.detectChanges();
      }
    });
  }
  reenviarCodigo(): void {
    this.errorVerificacion = '';
    this.mensajeVerificacion = '';
    this.verificando = true;

    this.http.post(
      `http://localhost:8081/usuario/reenviarCodigo?correo=${this.correoRegistrado}`,
      {},
      { responseType: 'text' }
    ).subscribe({
      next: () => {
        this.verificando = false;
        this.mensajeVerificacion = '✅ Código reenviado a ' + this.correoRegistrado;
        this.cdr.detectChanges();
      },
      error: (err) => {
        this.verificando = false;
        this.errorVerificacion = err.error || 'Error al reenviar el código.';
        this.cdr.detectChanges();
      }
    });
  }
}

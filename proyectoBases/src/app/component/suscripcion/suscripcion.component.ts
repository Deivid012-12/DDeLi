import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Router, RouterModule } from '@angular/router';

import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ConfirmationService } from 'primeng/api';
import { ButtonModule } from 'primeng/button';

export interface PlanSuscripcion {
  idPlan: number;
  nombre: string;
  precioMensual: number;
  costoAdicional: number;
}

export interface Suscripcion {
  idSuscripcion: number;
  fechaInicio: string;
  estado: string;
  plan: PlanSuscripcion;
}

@Component({
  selector: 'app-suscripcion',
  standalone: true,

  imports: [
    CommonModule,
    RouterModule,
    ConfirmDialogModule,
    ButtonModule
  ],

  providers: [ConfirmationService],

  templateUrl: './suscripcion.component.html',
  styleUrl: './suscripcion.component.css'
})
export class SuscripcionComponent implements OnInit {

  planes: PlanSuscripcion[] = [];
  suscripcionActiva: Suscripcion | null = null;

  cargando = true;
  procesando = false;

  error = '';
  mensaje = '';

  constructor(
    private http: HttpClient,
    private router: Router,
    private cdr: ChangeDetectorRef,
    private confirmationService: ConfirmationService
  ) {}

  ngOnInit(): void {
    this.cargarDatos();
  }

  cargarDatos(): void {

    this.http.get<PlanSuscripcion[]>(
      'http://localhost:8081/plan/getall'
    ).subscribe({

      next: (planes) => {
        this.planes = planes ?? [];
        this.cargarSuscripcionActiva();
      },

      error: (err) => {
        this.error = 'Error cargando planes.';
        this.cargando = false;

        console.error(err);

        this.cdr.detectChanges();
      }

    });

  }

  cargarSuscripcionActiva(): void {

    this.http.get<Suscripcion>(
      'http://localhost:8081/suscripcion/miSuscripcion'
    ).subscribe({

      next: (data) => {
        this.suscripcionActiva = data;
        this.cargando = false;

        this.cdr.detectChanges();
      },

      error: (err) => {

        if (err.status === 204) {
          this.suscripcionActiva = null;
        }

        this.cargando = false;

        this.cdr.detectChanges();
      }

    });

  }

  suscribirse(idPlan: number): void {

    this.error = '';
    this.mensaje = '';

    if (this.suscripcionActiva) {
      this.mensaje = 'Ya tienes una suscripción activa.';
      return;
    }

    this.procesando = true;

    this.http.post(
      `http://localhost:8081/suscripcion/suscribirse/${idPlan}`,
      {},
      { responseType: 'text' }
    ).subscribe({

      next: () => {

        this.mensaje = '¡Suscripción activada con éxito! 🎉';

        this.procesando = false;

        this.cargarSuscripcionActiva();
      },

      error: (err) => {

        this.error = err.error || 'Error al suscribirse.';

        this.procesando = false;

        this.cdr.detectChanges();
      }

    });

  }

  cancelarSuscripcion(): void {

    this.confirmationService.confirm({

      header: 'Cancelar suscripción',

      message: '¿Estás segura de que deseas cancelar tu suscripción?',

      icon: 'pi pi-exclamation-triangle',

      acceptLabel: 'Sí, cancelar',
      rejectLabel: 'No',

      acceptButtonStyleClass: 'p-button-danger',
      rejectButtonStyleClass: 'p-button-text',

      accept: () => {

        this.error = '';
        this.mensaje = '';

        this.procesando = true;

        this.http.put(
          'http://localhost:8081/suscripcion/cancelar',
          {},
          { responseType: 'text' }
        ).subscribe({

          next: () => {

            this.mensaje = 'Suscripción cancelada correctamente.';

            this.suscripcionActiva = null;

            this.procesando = false;

            this.cdr.detectChanges();
          },

          error: (err) => {

            this.error = err.error || 'Error al cancelar la suscripción.';

            this.procesando = false;

            this.cdr.detectChanges();
          }

        });

      }

    });

  }

  esPlanActivo(idPlan: number): boolean {
    return this.suscripcionActiva?.plan?.idPlan === idPlan;
  }

}

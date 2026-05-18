import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router, RouterModule } from '@angular/router';
import { EventoService, EventoSeleccionado } from '../../service/evento.service';

export interface Evento {
  idEvento: number;
  fechaEvento: string;
  numeroPersonas: number;
  tipoEvento: string;
}

@Component({
  selector: 'app-evento',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './evento.component.html',
  styleUrl: './evento.component.css'
})
export class EventoComponent implements OnInit {

  eventos: Evento[] = [];

  cargando = true;
  procesando = false;

  error = '';
  mensaje = '';

  eventoActivo: EventoSeleccionado | null = null;

  mostrarFormulario = false;

  mostrarConfirmacion = false;
  eventoAEliminar: number | null = null;

  hoy: string = new Date().toISOString().split('T')[0];

  nuevoEvento = {
    tipoEvento: '',
    fechaEvento: '',
    numeroPersonas: 1
  };

  tiposEvento = [
    'Cumpleaños',
    'Matrimonio',
    'Bautizo',
    'Graduación',
    'Aniversario',
    'Empresarial',
    'Otro'
  ];

  constructor(
    private http: HttpClient,
    private router: Router,
    private eventoService: EventoService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.eventoActivo = this.eventoService.obtenerEvento();
    this.cargarEventos();
  }

  cargarEventos(): void {

    this.cargando = true;

    this.http.get<Evento[]>(
      'http://localhost:8081/evento/obtenerPorUsuario'
    ).subscribe({

      next: (data) => {
        this.eventos = data ?? [];
        this.cargando = false;
        this.cdr.detectChanges();
      },

      error: (err) => {
        console.log('ERROR CARGANDO EVENTOS:', err);

        if (err.status === 204) {
          this.eventos = [];
        } else {
          this.error = 'Error al cargar eventos';
        }

        this.cargando = false;
        this.cdr.detectChanges();
      },

      complete: () => {
        this.cargando = false;
      }
    });
  }


  crearEvento(): void {

    this.error = '';
    this.mensaje = '';

    if (
      !this.nuevoEvento.tipoEvento ||
      !this.nuevoEvento.fechaEvento ||
      !this.nuevoEvento.numeroPersonas
    ) {
      this.error = 'Completa todos los campos del evento';
      return;
    }

    this.procesando = true;

    this.http.post(
      'http://localhost:8081/evento/crear',
      this.nuevoEvento,
      { responseType: 'text' }
    ).subscribe({

      next: () => {

        this.mensaje = '¡Evento creado con éxito! 🎉';

        this.mostrarFormulario = false;

        this.nuevoEvento = {
          tipoEvento: '',
          fechaEvento: '',
          numeroPersonas: 1
        };

        this.cargarEventos();
      },

      error: (err) => {
        console.log('ERROR CREANDO EVENTO:', err);
        this.error = 'Error al crear el evento';
      },

      complete: () => {
        this.procesando = false;
      }
    });
  }

  usarEvento(evento: Evento): void {

    this.eventoService.seleccionar({
      idEvento: evento.idEvento,
      tipoEvento: evento.tipoEvento,
      fechaEvento: evento.fechaEvento,
      numeroPersonas: evento.numeroPersonas
    });

    this.eventoActivo = this.eventoService.obtenerEvento();

    this.mensaje = `Evento "${evento.tipoEvento}" seleccionado para tu pedido.`;

    this.cdr.detectChanges();
  }

  quitarEvento(): void {
    this.eventoService.limpiar();
    this.eventoActivo = null;
    this.mensaje = 'Evento removido del pedido';
    this.cdr.detectChanges();
  }

  eliminarEvento(idEvento: number): void {
    this.eventoAEliminar = idEvento;
    this.mostrarConfirmacion = true;
  }

  confirmarEliminar(): void {

    if (!this.eventoAEliminar) return;

    this.procesando = true;
    this.error = '';
    this.mensaje = '';

    this.http.delete(
      `http://localhost:8081/evento/deletebyid/${this.eventoAEliminar}`,
      { responseType: 'text' }
    ).subscribe({

      next: () => {

        this.eventos = this.eventos.filter(
          e => e.idEvento !== this.eventoAEliminar
        );

        if (this.eventoActivo?.idEvento === this.eventoAEliminar) {
          this.quitarEvento();
        }

        this.mensaje = '🗑️ Evento eliminado correctamente';

        this.mostrarConfirmacion = false;
        this.eventoAEliminar = null;
      },

      error: (err) => {
        console.log('ERROR ELIMINANDO:', err);
        this.error = 'Error al eliminar el evento';
      },

      complete: () => {
        this.procesando = false;
      }
    });
  }

  cancelarEliminacion(): void {
    this.mostrarConfirmacion = false;
    this.eventoAEliminar = null;
  }


  irAlCarrito(): void {
    this.router.navigate(['/carrito']);
  }
}

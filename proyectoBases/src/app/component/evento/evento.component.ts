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

  mostrarFormulario = false;

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
    this.http.get<Evento[]>('http://localhost:8081/evento/misEventos').subscribe({
      next: (data) => {
        this.eventos = data ?? [];
        this.cargando = false;
        this.cdr.detectChanges();
      },
      error: (err) => {
        if (err.status === 204) this.eventos = [];
        this.cargando = false;
        this.cdr.detectChanges();
      }
    });
  }

  crearEvento(): void {
    if (!this.nuevoEvento.tipoEvento || !this.nuevoEvento.fechaEvento || !this.nuevoEvento.numeroPersonas) {
      this.error = 'Completa todos los campos del evento';
      return;
    }

    this.procesando = true;
    this.error = '';

    this.http.post(
      'http://localhost:8081/evento/crearMio',
      this.nuevoEvento,
      { responseType: 'text' }
    ).subscribe({
      next: () => {
        this.mensaje = '¡Evento creado con éxito!';
        this.procesando = false;
        this.mostrarFormulario = false;
        this.nuevoEvento = { tipoEvento: '', fechaEvento: '', numeroPersonas: 1 };
        this.cargarEventos();
        this.cdr.detectChanges();
      },
      error: (err) => {
        this.error = err.error || 'Error al crear el evento';
        this.procesando = false;
        this.cdr.detectChanges();
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
    this.mensaje = `Evento "${evento.tipoEvento}" seleccionado para tu próximo pedido.`;
    this.cdr.detectChanges();
  }

  quitarEvento(): void {
    this.eventoService.limpiar();
    this.eventoActivo = null;
    this.cdr.detectChanges();
  }

  eliminarEvento(idEvento: number): void {
    if (!confirm('¿Eliminar este evento?')) return;
    this.http.delete(
      `http://localhost:8081/evento/deletebyid/${idEvento}`,
      { responseType: 'text' }
    ).subscribe({
      next: () => {
        this.eventos = this.eventos.filter(e => e.idEvento !== idEvento);
        if (this.eventoActivo?.idEvento === idEvento) this.quitarEvento();
        this.cdr.detectChanges();
      },
      error: (err) => console.error(err)
    });
  }

  irAlCarrito(): void {
    this.router.navigate(['/carrito']);
  }
}

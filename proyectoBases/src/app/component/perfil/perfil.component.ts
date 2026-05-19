import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../../service/auth.service';
import { EventoService, EventoSeleccionado } from '../../service/evento.service';

export interface UsuarioPerfil {
  idUsuario: number;
  nombre: string;
  correo: string;
  telefono: string;
  rol: string;
  verificado: boolean;
}

export interface PlanSuscripcion {
  idPlan: number;
  nombre: string;
  precioMensual: number;
  costoAdicional: number;
}

export interface SuscripcionActiva {
  idSuscripcion: number;
  fechaInicio: string;
  estado: string;
  plan: PlanSuscripcion;
}

export interface Evento {
  idEvento: number;
  fechaEvento: string;
  numeroPersonas: number;
  tipoEvento: string;
}

@Component({
  selector: 'app-perfil',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './perfil.component.html',
  styleUrl: './perfil.component.css'
})
export class PerfilComponent implements OnInit {

  usuario: UsuarioPerfil | null = null;
  pedidos: any[] = [];
  suscripcion: SuscripcionActiva | null = null;
  eventos: Evento[] = [];
  eventoActivo: EventoSeleccionado | null = null;
  loading = true;
  loadingEventos = true;
  pedidoExpandido: number | null = null;

  constructor(
    private authService: AuthService,
    private router: Router,
    private http: HttpClient,
    private cdr: ChangeDetectorRef,
    private eventoService: EventoService
  ) {}

  ngOnInit(): void {
    this.eventoActivo = this.eventoService.obtenerEvento();
    this.cargarPerfil();
    this.cargarPedidos();
    this.cargarSuscripcion();
    this.cargarEventos();
  }

  cargarPerfil(): void {
    this.http.get<UsuarioPerfil>('http://localhost:8081/usuario/miPerfil').subscribe({
      next: (data) => {
        this.usuario = data;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Error cargando perfil:', err);
        this.usuario = {
          idUsuario: 0,
          nombre: this.authService.getUserName() || '',
          correo: '',
          telefono: '',
          rol: this.authService.getUserRole() || '',
          verificado: false
        };
        this.cdr.detectChanges();
      }
    });
  }

  cargarPedidos(): void {
    this.http.get<any[]>('http://localhost:8081/pedido/misPedidos').subscribe({
      next: (pedidos) => {
        this.pedidos = pedidos ?? [];
        this.loading = false;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Error cargando pedidos:', err);
        this.pedidos = [];
        this.loading = false;
        this.cdr.detectChanges();
      }
    });
  }

  cargarSuscripcion(): void {
    this.http.get<SuscripcionActiva>('http://localhost:8081/suscripcion/miSuscripcion').subscribe({
      next: (data) => {
        this.suscripcion = data;
        this.cdr.detectChanges();
      },
      error: (err) => {
        if (err.status === 204) this.suscripcion = null;
        this.cdr.detectChanges();
      }
    });
  }

  cargarEventos(): void {
    this.http.get<Evento[]>('http://localhost:8081/evento/misEventos').subscribe({
      next: (data) => {
        this.eventos = data ?? [];
        this.loadingEventos = false;
        this.cdr.detectChanges();
      },
      error: (err) => {
        if (err.status === 204) this.eventos = [];
        this.loadingEventos = false;
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

  togglePedido(idPedido: number): void {
    this.pedidoExpandido = this.pedidoExpandido === idPedido ? null : idPedido;
  }

  calcularTotalPedido(detalles: any[]): number {
    return detalles?.reduce((acc, d) => acc + d.subtotal, 0) ?? 0;
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/inicio']);
  }
}

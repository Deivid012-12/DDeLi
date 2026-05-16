import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../../service/auth.service';

@Component({
  selector: 'app-perfil',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './perfil.component.html',
  styleUrl: './perfil.component.css'
})
export class PerfilComponent implements OnInit {

  usuario: any;
  pedidos: any[] = [];
  loading = true;
  pedidoExpandido: number | null = null;

  constructor(
    private authService: AuthService,
    private router: Router,
    private http: HttpClient,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.usuario = {
      nombre: this.authService.getUserName()
    };
    this.cargarPedidos();
  }

  cargarPedidos(): void {
    this.http.get<any[]>('http://localhost:8081/pedido/misPedidos').subscribe({
      next: (pedidos) => {
        this.pedidos = pedidos;
        this.loading = false;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Error cargando pedidos:', err);
        this.loading = false;
      }
    });
  }

  togglePedido(idPedido: number): void {
    this.pedidoExpandido = this.pedidoExpandido === idPedido ? null : idPedido;
  }

  logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('userRole');
    localStorage.removeItem('nombre');
    localStorage.removeItem('usuario');
    this.authService.logout();
    this.router.navigate(['/principal']);
  }
}

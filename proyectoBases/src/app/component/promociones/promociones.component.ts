import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Producto } from '../../model/carrito.model';
import { RouterModule} from '@angular/router';

export interface Promocion {
  idPromocion: number;
  nombre: string;
  porcentajeDescuento: number;
  fechaInicio: string;
  fechaFin: string;
}

@Component({
  selector: 'app-promociones',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './promociones.component.html',
  styleUrl: './promociones.component.css',
})
export class PromocionesComponent implements OnInit {

  promociones: Promocion[] = [];
  productos: Producto[] = [];

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.cargarPromociones();
    this.cargarProductos();
  }

  cargarPromociones(): void {
    this.http.get<Promocion[]>('http://localhost:8081/promocion/vigentes').subscribe({
      next: (data) => this.promociones = data,
      error: (err) => console.error('Error cargando promociones', err)
    });
  }

  cargarProductos(): void {
    this.http.get<Producto[]>('http://localhost:8081/producto/disponibles').subscribe({
      next: (data) => this.productos = data,
      error: (err) => console.error('Error cargando productos', err)
    });
  }

  precioConDescuento(precio: number, porcentaje: number): number {
    return precio - (precio * porcentaje / 100);
  }
}

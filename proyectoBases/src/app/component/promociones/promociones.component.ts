import { Component, OnInit, DestroyRef, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Router, RouterModule } from '@angular/router';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { PromocionService } from '../../service/promocion.service';

export interface Producto {
  idProducto: number;
  nombre: string;
  descripcion: string;
  precioBase: number;
  disponibilidad: boolean;
  tipo: string;
  imagenURL: string;
}

export interface PromocionConProductos {
  idPromocion: number;
  nombre: string;
  porcentajeDescuento: number;
  fechaInicio: string;
  fechaFin: string;
  productos: Producto[];
}

@Component({
  selector: 'app-promociones',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './promociones.component.html',
  styleUrl: './promociones.component.css',
})
export class PromocionesComponent implements OnInit {

  private destroyRef = inject(DestroyRef);
  private http = inject(HttpClient);
  private cdr = inject(ChangeDetectorRef);

  promociones: PromocionConProductos[] = [];
  cargando = true;
  error = '';

  ngOnInit(): void {
    this.cargarPromocionesConProductos();
  }

  cargarPromocionesConProductos(): void {
    this.http.get<PromocionConProductos[]>(
      'http://localhost:8081/promocion/vigentes-con-productos'
    )
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: (data) => {
          this.promociones = data ?? [];
          this.cargando = false;
          this.cdr.detectChanges(); // ← fuerza la actualización de la vista
        },
        error: (err) => {
          this.error = 'No se pudieron cargar las promociones.';
          this.cargando = false;
          this.cdr.detectChanges();
          console.error('Error:', err);
        }
      });
  }

  precioConDescuento(precio: number, porcentaje: number): number {
    return precio - (precio * porcentaje / 100);
  }
  private promocionService = inject(PromocionService);
  private router = inject(Router);

  usarPromo(promo: PromocionConProductos): void {
    this.promocionService.seleccionar({
      idPromocion: promo.idPromocion,
      nombre: promo.nombre,
      porcentajeDescuento: promo.porcentajeDescuento
    });
    this.router.navigate(['/carrito']);
  }

}

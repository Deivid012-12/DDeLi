import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, Router } from '@angular/router';
import { Carrito, ItemCarrito } from '../../model/carrito.model';
import { CarritoService } from '../../service/carrito.service';
import { PromocionService, PromocionSeleccionada } from '../../service/promocion.service';
import { EventoService, EventoSeleccionado } from '../../service/evento.service';


@Component({
  selector: 'app-carrito',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './carrito.component.html',
  styleUrl: './carrito.component.css',
})
export class CarritoComponent implements OnInit {
  carrito: Carrito | null = null;
  items: ItemCarrito[] = [];
  loading = true;
  promoActiva: PromocionSeleccionada | null = null;
  eventoActivo: EventoSeleccionado | null = null;


  constructor(
    private router: Router,
    private carritoService: CarritoService,
    private cdr: ChangeDetectorRef,
    private promocionService: PromocionService,
  private eventoService: EventoService
  ) {}

  ngOnInit(): void {
    // Lee la promo activa
    this.promocionService.promo$.subscribe(promo => {
      this.promoActiva = promo;
      this.cdr.detectChanges();
    });

    // Lee el evento activo
    this.eventoService.evento$.subscribe(evento => {
      this.eventoActivo = evento;
      this.cdr.detectChanges();
    });

    // Carga el carrito
    this.carritoService.obtenerOCrearCarrito().subscribe({
      next: (carrito) => {
        this.carrito = carrito;
        this.carritoService.obtenerItems(carrito.idCarrito).subscribe({
          next: (items) => {
            this.items = items;
            this.loading = false;
            this.cdr.detectChanges();
          },
          error: (err) => {
            console.error(err);
            this.loading = false;
          }
        });
      },
      error: (err) => {
        console.error(err);
        this.loading = false;
      }
    });
  }

  quitarEvento(): void {
    this.eventoService.limpiar();
  }


  irAPagar(): void {
    this.router.navigate(['/pagar']);
  }

  aumentarCantidad(item: ItemCarrito): void {
    this.carritoService.actualizarCantidad(item.idItem!, item.cantidad + 1).subscribe({
      next: () => {
        item.cantidad++;
        item.subtotal = item.cantidad * item.precioUnitario;
        this.cdr.detectChanges();
      }
    });
  }

  disminuirCantidad(item: ItemCarrito): void {
    if (item.cantidad > 1) {
      this.carritoService.actualizarCantidad(item.idItem!, item.cantidad - 1).subscribe({
        next: () => {
          item.cantidad--;
          item.subtotal = item.cantidad * item.precioUnitario;
          this.cdr.detectChanges();
        }
      });
    }
  }

  calcularSubtotal(): number {
    return this.items.reduce((acc, item) => acc + item.subtotal, 0);
  }

  calcularDescuento(): number {
    if (!this.promoActiva) return 0;
    const subtotal = this.calcularSubtotal();
    return subtotal * this.promoActiva.porcentajeDescuento / 100;
  }

  calcularTotal(): number {
    return this.calcularSubtotal() - this.calcularDescuento();
  }

  quitarPromo(): void {
    this.promocionService.limpiar();
  }

  eliminarItem(idItem: number): void {
    this.carritoService.eliminarItem(idItem).subscribe({
      next: () => {
        this.items = this.items.filter(item => item.idItem !== idItem);
        this.cdr.detectChanges();
      }
    });
  }

  onImageError(event: Event): void {
    const img = event.target as HTMLImageElement;
    img.src = 'https://placehold.co/300x200?text=Sin+imagen';
  }
}

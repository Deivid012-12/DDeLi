import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, Router } from '@angular/router';
import { Carrito, ItemCarrito } from '../../model/carrito.model';
import { CarritoService } from '../../service/carrito.service';

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

  constructor(
    private router: Router,
    private carritoService: CarritoService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.carritoService.obtenerOCrearCarrito().subscribe({
      next: (carrito) => {
        this.carrito = carrito;
        this.carritoService.obtenerItems(carrito.idCarrito).subscribe({
          next: (items) => {
            this.items = items;
            this.loading = false;
            this.cdr.detectChanges();
            console.log('Items en componente:', this.items.length);
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

  calcularTotal(): number {
    return this.items.reduce((acc, item) => acc + item.subtotal, 0);
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

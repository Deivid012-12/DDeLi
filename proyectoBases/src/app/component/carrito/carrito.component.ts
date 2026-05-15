import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, Router, ActivatedRoute } from '@angular/router';
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
  carrito: Carrito| null = null;
  loading = true;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private carritoService: CarritoService
  ) {}

  ngOnInit(): void {
    this.cargarCarrito();
  }

  cargarCarrito(): void {
    this.carritoService.obtenerOCrearCarrito().subscribe({
      next: (carrito) => {
        this.carrito = carrito;
        this.cargarItems(carrito.idCarrito);
      },
      error: (error) => {
        console.error('Error cargando carrito:', error);
        this.loading = false;
      }
    });
  }

  cargarItems(idCarrito: number): void {
    this.carritoService.obtenerItems(idCarrito).subscribe({
      next: (items) => {
        if (this.carrito) {
          this.carrito.items = items;
        }
        this.loading = false;
      },
      error: (error) => {
        console.error('Error cargando items:', error);
        this.loading = false;
      }
    });
  }

  get items(): ItemCarrito[] {
    return this.carrito?.items ?? [];
  }

  irAPagar(): void {
    this.router.navigate(['/pagar']);
  }

  aumentarCantidad(item: ItemCarrito): void {
    this.carritoService.actualizarCantidad(item.idItem!, item.cantidad + 1).subscribe({
      next: () => {
        item.cantidad++;
        item.subtotal = item.cantidad * item.precioUnitario;
      }
    });
  }

  disminuirCantidad(item: ItemCarrito): void {
    if (item.cantidad > 1) {
      this.carritoService.actualizarCantidad(item.idItem!, item.cantidad - 1).subscribe({
        next: () => {
          item.cantidad--;
          item.subtotal = item.cantidad * item.precioUnitario;
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
        if (this.carrito) {
          this.carrito.items = (this.carrito.items ?? []).filter(
            item => item.idItem !== idItem
          );
        }
      }
    });
  }
}

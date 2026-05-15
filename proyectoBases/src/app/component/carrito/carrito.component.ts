import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Carrito, ItemCarrito } from '../../model/carrito.model';
import { RouterLink, Router } from '@angular/router';
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

  constructor(
    private router: Router,
    private carritoService: CarritoService
  ) {}

  get items(): ItemCarrito[] {
    return this.carrito?.items ?? [];
  }

  ngOnInit(): void {

    this.carrito = {
      idCarrito: 1,
      estado: 'activo',
      fechaCreacion: new Date().toISOString(),
      items: this.carritoService.obtenerItems()
    };
  }

  irAPagar(): void {
    this.router.navigate(['/pagar']);
  }

  aumentarCantidad(item: ItemCarrito): void {
    item.cantidad++;
    item.subtotal = item.cantidad * item.precioUnitario;
  }

  disminuirCantidad(item: ItemCarrito): void {

    if (item.cantidad > 1) {
      item.cantidad--;
      item.subtotal = item.cantidad * item.precioUnitario;
    }
  }

  calcularTotal(): number {

    return this.items.reduce(
      (acc, item) => acc + item.subtotal,
      0
    );
  }

  eliminarItem(idItem: number): void {

    this.carritoService.eliminarItem(idItem);

    if (this.carrito) {
      this.carrito.items =
        this.carritoService.obtenerItems();
    }
  }
}

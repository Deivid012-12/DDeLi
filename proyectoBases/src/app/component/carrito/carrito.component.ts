import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Carrito, ItemCarrito } from '../../model/carrito.model';

@Component({
  selector: 'app-carrito',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './carrito.component.html',
  styleUrl: './carrito.component.css',
})
export class CarritoComponent implements OnInit {
  carrito: Carrito | null = null;

  get items(): ItemCarrito[] {
    return this.carrito?.items ?? [];
  }

  ngOnInit(): void {
    this.carrito = {
      idCarrito: 1,
      estado: 'activo',
      fechaCreacion: new Date().toISOString(),
      items: [
        {
          idItem: 1,
          cantidad: 2,
          precioUnitario: 15000,
          subtotal: 30000,
          producto: {
            idProducto: 1,
            nombre: 'Brownie de chocolate',
            descripcion: 'Delicioso brownie artesanal',
            precio: 15000,
            imagen: 'assets/brownie.jpg'
          }
        },
        {
          idItem: 2,
          cantidad: 1,
          precioUnitario: 25000,
          subtotal: 25000,
          producto: {
            idProducto: 2,
            nombre: 'Cheesecake de frutos rojos',
            descripcion: 'Cheesecake cremoso con topping de frutos rojos',
            precio: 25000,
            imagen: 'assets/cheesecake.jpg'
          }
        }
      ]
    };
  }

  calcularTotal(): number {
    return this.items.reduce((acc, item) => acc + item.subtotal, 0);
  }

  eliminarItem(idItem: number): void {
    if (!this.carrito) return;
    this.carrito.items = this.carrito.items.filter(i => i.idItem !== idItem);
  }
}

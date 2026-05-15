import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink, Router } from '@angular/router';
import { ItemCarrito } from '../../model/carrito.model';

export interface Pago {
  cantidadPago: number;
  metodoPago: string;
  estadoTransaccion: string;
  fechaPago: string;
}

export interface Pedido {
  fechaPedido: string;
  valorTotal: number;
}

@Component({
  selector: 'app-pagar',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './pagar.component.html',
  styleUrls: ['./pagar.component.css'],
})
export class PagarComponent implements OnInit {

  pedido: Pedido = {
    fechaPedido: new Date().toISOString().split('T')[0],
    valorTotal: 0,
  };

  pago: Pago = {
    cantidadPago: 0,
    metodoPago: 'tarjeta',
    estadoTransaccion: 'pendiente',
    fechaPago: new Date().toISOString().split('T')[0],
  };

  direccion: string = '';
  ciudad: string = '';
  indicaciones: string = '';
  tipoEntrega: string = 'domicilio';

  numeroTarjeta: string = '';
  vencimiento: string = '';
  cvv: string = '';
  nombreTarjeta: string = '';

  items: ItemCarrito[] = [
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
  ];

  constructor(private router: Router) {}

  ngOnInit(): void {
    this.pedido.valorTotal = this.calcularTotal() + 5000;
    this.pago.cantidadPago = this.pedido.valorTotal;
  }

  calcularTotal(): number {
    return this.items.reduce((acc, item) => acc + item.subtotal, 0);
  }

  confirmarPago(): void {
    if (!this.direccion || !this.ciudad) {
      alert('Por favor completa la dirección de entrega.');
      return;
    }
    alert('¡Pedido confirmado! Gracias por tu compra 🎉');
    this.router.navigate(['/principal']);
  }
}

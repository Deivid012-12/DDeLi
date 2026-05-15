import { Injectable } from '@angular/core';
import { ItemCarrito, Producto } from '../model/carrito.model';

@Injectable({
  providedIn: 'root'
})
export class CarritoService {

  items: ItemCarrito[] = [];

  agregarProducto(producto: Producto): void {

    const itemExistente = this.items.find(
      i => i.producto.idProducto === producto.idProducto
    );

    if (itemExistente) {

      itemExistente.cantidad++;

      itemExistente.subtotal =
        itemExistente.cantidad * itemExistente.precioUnitario;

    } else {

      const nuevoItem: ItemCarrito = {
        idItem: Date.now(),
        cantidad: 1,
        precioUnitario: producto.precio,
        subtotal: producto.precio,
        producto: producto
      };

      this.items.push(nuevoItem);
    }
  }

  obtenerItems(): ItemCarrito[] {
    return this.items;
  }

  eliminarItem(idItem: number): void {
    this.items = this.items.filter(i => i.idItem !== idItem);
  }

  calcularTotal(): number {
    return this.items.reduce(
      (acc, item) => acc + item.subtotal,
      0
    );
  }
}

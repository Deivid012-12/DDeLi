import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Producto } from '../../model/carrito.model';
import { CarritoService } from '../../service/carrito.service';

@Component({
  selector: 'app-menu',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.css',
})
export class MenuComponent {

  constructor(private carritoService: CarritoService) {}
  categoriaActiva: string = 'Todos';

  categorias: string[] = ['Todos', 'Tortas', 'Brownies', 'Cheesecakes', 'Cupcakes', 'Postres Especiales'];

  productos: (Producto & { categoria: string })[] = [
    { idProducto: 1, nombre: 'Torta de Chocolate', descripcion: 'Deliciosa torta artesanal con cobertura de chocolate', precio: 65000, imagen: 'assets/torta1.jpg', categoria: 'Tortas' },
    { idProducto: 2, nombre: 'Torta Red Velvet', descripcion: 'Esponjosa torta red velvet con crema de queso', precio: 70000, imagen: 'assets/torta2.jpg', categoria: 'Tortas' },
    { idProducto: 3, nombre: 'Torta de Vainilla', descripcion: 'Torta clásica de vainilla con buttercream', precio: 60000, imagen: 'assets/torta3.jpg', categoria: 'Tortas' },
    { idProducto: 4, nombre: 'Brownie Clásico', descripcion: 'Brownie esponjoso con el mejor chocolate', precio: 12000, imagen: 'assets/brownie.jpg', categoria: 'Brownies' },
    { idProducto: 5, nombre: 'Brownie con Nueces', descripcion: 'Brownie de chocolate con nueces tostadas', precio: 14000, imagen: 'assets/brownie2.jpg', categoria: 'Brownies' },
    { idProducto: 6, nombre: 'Brownie Oreo', descripcion: 'Brownie de chocolate con trozos de Oreo', precio: 15000, imagen: 'assets/brownie3.jpg', categoria: 'Brownies' },
    { idProducto: 7, nombre: 'Cheesecake de Frutos Rojos', descripcion: 'Cremoso cheesecake con topping de frutos rojos', precio: 25000, imagen: 'assets/cheesecake.jpg', categoria: 'Cheesecakes' },
    { idProducto: 8, nombre: 'Cheesecake de Maracuyá', descripcion: 'Cheesecake tropical con coulis de maracuyá', precio: 27000, imagen: 'assets/cheesecake2.jpg', categoria: 'Cheesecakes' },
    { idProducto: 9, nombre: 'Cheesecake de Oreo', descripcion: 'Cheesecake con base y topping de Oreo', precio: 26000, imagen: 'assets/cheesecake3.jpg', categoria: 'Cheesecakes' },
    { idProducto: 10, nombre: 'Cupcake de Vainilla', descripcion: 'Cupcake esponjoso con frosting de vainilla', precio: 8000, imagen: 'assets/cupcake1.jpg', categoria: 'Cupcakes' },
    { idProducto: 11, nombre: 'Cupcake de Chocolate', descripcion: 'Cupcake de chocolate con frosting de nutella', precio: 9000, imagen: 'assets/cupcake2.jpg', categoria: 'Cupcakes' },
    { idProducto: 12, nombre: 'Cupcake Red Velvet', descripcion: 'Cupcake red velvet con crema de queso', precio: 9500, imagen: 'assets/cupcake3.jpg', categoria: 'Cupcakes' },
    { idProducto: 13, nombre: 'Waffle Belga', descripcion: 'Waffle crujiente con frutas y crema chantilly', precio: 18000, imagen: 'assets/waffle.jpg', categoria: 'Postres Especiales' },
    { idProducto: 14, nombre: 'Tiramisú', descripcion: 'Tiramisú italiano con café y mascarpone', precio: 22000, imagen: 'assets/tiramisu.jpg', categoria: 'Postres Especiales' },
    { idProducto: 15, nombre: 'Mousse de Chocolate', descripcion: 'Mousse esponjoso de chocolate oscuro', precio: 16000, imagen: 'assets/mousse.jpg', categoria: 'Postres Especiales' },
  ];

  get productosFiltrados(): (Producto & { categoria: string })[] {

    if (this.categoriaActiva === 'Todos') {
      return this.productos;
    }

    return this.productos.filter(
      p => p.categoria === this.categoriaActiva
    );
  }

  agregarAlCarrito(producto: Producto): void {

    this.carritoService.agregarProducto(producto);

    alert(`¡${producto.nombre} añadido al carrito! 🛒`);
  }
}

import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { CarritoService } from '../../service/carrito.service';
import { Producto } from '../../model/carrito.model';

@Component({
  selector: 'app-menu',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.css',
})
export class MenuComponent implements OnInit {

  categoriaActiva: string = 'Todos';

  categorias: string[] = [
    'Todos',
    'Brownies',
    'Cheesecakes',
    'Tortas'
  ];

  productosFiltrados: Producto[] = [];
  productos: Producto[] = [];

  constructor(
    private http: HttpClient,
    private carritoService: CarritoService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.cargarProductos();
  }

  cargarProductos(): void {
    this.http.get<Producto[]>('http://localhost:8081/producto/getall').subscribe({
      next: (data) => {
        this.productos = data;
        this.productosFiltrados = data;
        this.cdr.detectChanges();
      },
      error: (error) => {
        console.error(error);
      }
    });
  }

  filtrarCategoria(categoria: string): void {
    this.categoriaActiva = categoria;

    if (categoria === 'Todos') {
      this.productosFiltrados = [...this.productos];
    } else {
      this.productosFiltrados = this.productos.filter(
        p => p.nombreCategoria === categoria
      );
    }
    this.cdr.detectChanges();
  }

  agregarAlCarrito(producto: Producto): void {
    this.carritoService.agregarDesdeMenu(producto).subscribe({
      next: () => {
        alert('Producto agregado al carrito 🛒');
      },
      error: (err) => {
        console.error(err);
        alert('Debes iniciar sesión');
      }
    });
  }
}

import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
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
  cargando: boolean = false;         // 1. estado de carga
  mensajeCarrito: string | null = null; // 2. reemplaza el alert()

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
    private cdr: ChangeDetectorRef,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      if (params['categoria']) {
        this.categoriaActiva = params['categoria'];
      }
      this.cargarProductos();
    });
  }

  cargarProductos(): void {
    this.cargando = true;  // 3. activa el spinner

    this.http.get<Producto[]>('http://localhost:8081/producto/getall').subscribe({
      next: (data) => {
        this.productos = data;
        this.aplicarFiltro();  // 4. lógica de filtro centralizada
        this.cargando = false;
        this.cdr.detectChanges();
      },
      error: (error) => {
        console.error(error);
        this.cargando = false;
        this.cdr.detectChanges();
      }
    });
  }

  // 5. filtro extraído a método privado — evita duplicar lógica
  private aplicarFiltro(): void {
    this.productosFiltrados = this.categoriaActiva === 'Todos'
      ? [...this.productos]
      : this.productos.filter(p => p.nombreCategoria === this.categoriaActiva);
  }

  filtrarCategoria(categoria: string): void {
    this.categoriaActiva = categoria;
    this.aplicarFiltro();  // 6. reutiliza el método privado
    this.cdr.detectChanges();
  }

  agregarAlCarrito(producto: Producto): void {
    this.carritoService.agregarDesdeMenu(producto).subscribe({
      next: () => {
        this.mostrarMensaje('✓ Producto agregado al carrito');  // 7. sin alert()
      },
      error: (err) => {
        console.error(err);
        this.mostrarMensaje('Debes iniciar sesión para agregar productos');
      }
    });
  }

  // 8. toast temporal que desaparece solo — mucho más profesional que alert()
  private mostrarMensaje(texto: string): void {
    this.mensajeCarrito = texto;
    this.cdr.detectChanges();
    setTimeout(() => {
      this.mensajeCarrito = null;
      this.cdr.detectChanges();
    }, 3000);
  }

  irAPersonalizar(): void {
    this.router.navigate(['/personalizacion']);
  }
}

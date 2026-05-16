import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { CarritoService } from '../../service/carrito.service';

interface OpcionDTO {
  idOpcion: number;
  nombre: string;
  costoAdicional: number;
  idTipo: number;
  nombreTipo: string;
}

interface TipoDTO {
  idTipo: number;
  nombre: string;
  opciones: OpcionDTO[];
}

@Component({
  selector: 'app-personalizacion',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './personalizacion.component.html',
  styleUrl: './personalizacion.component.css'
})
export class PersonalizacionComponent implements OnInit {

  tipos: TipoDTO[] = [];
  opcionesPorTipo: { [idTipo: number]: OpcionDTO[] } = {};
  seleccionadas: { [idTipo: number]: number } = {}; // una opcion por tipo
  notaPersonal: string = '';
  cantidad!: number ;
  loading = true;
  agregando = false;

  // Producto base para personalización (id fijo o puedes crear uno en la BD)
  readonly ID_PRODUCTO_PERSONALIZADO = 15; // cambia esto al id del producto base

  constructor(
    private http: HttpClient,
    private carritoService: CarritoService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.cantidad = 1;
    this.cargarTipos();
  }

  cargarTipos(): void {
    this.http.get<TipoDTO[]>('http://localhost:8081/tipo-personalizacion/getall').subscribe({
      next: (tipos) => {
        this.tipos = tipos;
        this.cargarOpciones(tipos);
      },
      error: (err) => {
        console.error('Error cargando tipos:', err);
        this.loading = false;
      }
    });
  }

  cargarOpciones(tipos: TipoDTO[]): void {
    let pendientes = tipos.length;

    if (pendientes === 0) {
      this.loading = false;
      return;
    }

    tipos.forEach(tipo => {
      this.http.get<OpcionDTO[]>(`http://localhost:8081/opcion/obtenerPorTipo/${tipo.idTipo}`).subscribe({
        next: (opciones) => {
          this.opcionesPorTipo[tipo.idTipo] = opciones ?? [];  // ← proteger contra null
          pendientes--;
          if (pendientes === 0) {
            this.loading = false;
            this.cdr.detectChanges();
          }
        },
        error: (err) => {
          console.error('Error cargando opciones:', err);
          this.opcionesPorTipo[tipo.idTipo] = [];
          pendientes--;
          if (pendientes === 0) {
            this.loading = false;
            this.cdr.detectChanges();
          }
        }
      });
    });
  }

  seleccionarOpcion(idTipo: number, idOpcion: number): void {
    this.seleccionadas[idTipo] = idOpcion;
  }

  estaSeleccionada(idTipo: number, idOpcion: number): boolean {
    return this.seleccionadas[idTipo] === idOpcion;
  }

  calcularCostoOpciones(): number {
    let costo = 0;
    Object.values(this.seleccionadas).forEach(idOpcion => {
      this.tipos.forEach(tipo => {
        const opciones = this.opcionesPorTipo[tipo.idTipo] || [];
        const opcion = opciones.find(o => o.idOpcion === idOpcion);
        if (opcion) costo += opcion.costoAdicional;
      });
    });
    return costo;
  }

  calcularTotal(): number {
    const precioBase = 25000; // precio base del postre personalizado
    return (precioBase + this.calcularCostoOpciones()) * this.cantidad;
  }

  getOpcionSeleccionada(idTipo: number): OpcionDTO | null {
    const idOpcion = this.seleccionadas[idTipo];
    if (!idOpcion) return null;
    const opciones = this.opcionesPorTipo[idTipo] || [];
    return opciones.find(o => o.idOpcion === idOpcion) || null;
  }

  agregarAlCarrito(): void {
    const idOpciones = Object.values(this.seleccionadas);

    if (idOpciones.length === 0) {
      alert('Por favor selecciona al menos una opción de personalización.');
      return;
    }

    this.agregando = true;

    this.carritoService.obtenerOCrearCarrito().subscribe({
      next: (carrito) => {
        const params = new URLSearchParams();
        params.set('idProducto', this.ID_PRODUCTO_PERSONALIZADO.toString());
        params.set('cantidad', this.cantidad.toString());
        idOpciones.forEach(id => params.append('idOpciones', id.toString()));

        this.http.post(
          `http://localhost:8081/api/carrito/agregarProducto/${carrito.idCarrito}?${params.toString()}`,
          {},
          { responseType: 'text' }
        ).subscribe({
          next: () => {
            this.agregando = false;
            alert('¡Postre personalizado agregado al carrito! 🎂');
            this.router.navigate(['/carrito']);
          },
          error: (err) => {
            console.error('Error agregando al carrito:', err);
            this.agregando = false;
            alert('Error al agregar al carrito.');
          }
        });
      },
      error: (err) => {
        console.error('Error obteniendo carrito:', err);
        this.agregando = false;
      }
    });
  }
}

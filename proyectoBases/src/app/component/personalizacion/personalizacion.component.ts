import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { CarritoService } from '../../service/carrito.service';
import Swal from 'sweetalert2';

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
  seleccionadas: { [idTipo: number]: number } = {};
  notaPersonal: string = '';
  cantidad!: number;
  loading = true;
  agregando = false;
  idProductoPersonalizado: number = 0;

  constructor(
    private http: HttpClient,
    private carritoService: CarritoService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.cantidad = 1;
    this.cargarProductoPersonalizado();
    this.cargarTipos();
  }

  cargarProductoPersonalizado(): void {
    this.http.get<any[]>('http://localhost:8081/producto/getall').subscribe({
      next: (productos) => {
        const personalizado = productos.find(p => p.tipo === 'PERSONALIZADO');
        if (personalizado) {
          this.idProductoPersonalizado = personalizado.idProducto;
        }
      },
      error: (err) => console.error('Error cargando producto personalizado:', err)
    });
  }

  cargarTipos(): void {
    this.http.get<TipoDTO[]>('http://localhost:8081/tipo-personalizacion/getall').subscribe({
      next: (tipos) => {
        this.tipos = tipos ?? [];
        this.cargarOpciones(this.tipos);
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
          this.opcionesPorTipo[tipo.idTipo] = opciones ?? [];
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
    const precioBase = 25000;
    return (precioBase + this.calcularCostoOpciones()) * this.cantidad;
  }

  getOpcionSeleccionada(idTipo: number): OpcionDTO | null {
    const idOpcion = this.seleccionadas[idTipo];
    if (!idOpcion) return null;
    const opciones = this.opcionesPorTipo[idTipo] || [];
    return opciones.find(o => o.idOpcion === idOpcion) || null;
  }
  todasSeleccionadas(): boolean {
    return this.tipos.length > 0 &&
      this.tipos.every(tipo => !!this.getOpcionSeleccionada(tipo.idTipo));
  }

  agregarAlCarrito(): void {
    const idOpciones = Object.values(this.seleccionadas);

    if (idOpciones.length === 0) {
      alert('Por favor selecciona al menos una opción de personalización.');
      return;
    }

    if (this.idProductoPersonalizado === 0) {
      alert('Error: no se encontró el producto personalizado. Intenta de nuevo.');
      return;
    }

    this.agregando = true;

    this.carritoService.obtenerOCrearCarrito().subscribe({
      next: (carrito) => {
        let url = `http://localhost:8081/api/carrito/agregarProducto/${carrito.idCarrito}?idProducto=${this.idProductoPersonalizado}&cantidad=${this.cantidad}`;
        idOpciones.forEach(id => {
          url += `&idOpciones=${id}`;
        });

        this.http.post(url, {}, { responseType: 'text' }).subscribe({
          next: () => {
            this.agregando = false;

            Swal.fire({
              toast: true,
              position: 'top-end',

              icon: 'success',

              title: 'Postre agregado al carrito 🧁',

              showConfirmButton: false,

              timer: 5000,

              timerProgressBar: true,

              background: '#fff5f7',

              color: '#7a3b4b'
            });

          },

          error: (err) => {
            console.error('Error agregando al carrito:', err);

            this.agregando = false;

            Swal.fire({
              icon: 'error',

              title: 'Oops...',

              text: 'No se pudo agregar al carrito.',

              confirmButtonColor: '#c87a8a',

              background: '#fff5f7',

              color: '#7a3b4b'
            });
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

import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { ItemCarrito } from '../../model/carrito.model';
import { Pago, Pedido } from '../../model/pagar.model';
import { CarritoService } from '../../service/carrito.service';
import { switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-pagar',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './pagar.component.html',
  styleUrl: './pagar.component.css',
})
export class PagarComponent implements OnInit {

  pedido: Pedido = {
    fechaPedido: new Date().toISOString().split('T')[0],
    valorTotal: 0,
  };

  pago: Pago = {
    idPedido: 0,
    cantidadPago: 0,
    metodoPago: 'tarjeta',
    estadoTransaccion: 'pendiente',
    fechaPago: new Date().toISOString().split('T')[0],
  };

  direccion: string = '';
  ciudad: string = '';
  indicaciones: string = '';
  tipoEntrega: string = 'domicilio';
  departamento: string = '';
  codigoPostal: string = '';

  numeroTarjeta: string = '';
  vencimiento: string = '';
  cvv: string = '';
  nombreTarjeta: string = '';

  items: ItemCarrito[] = [];
  idCarrito: number = 0;
  loading = true;
  procesando = false;

  constructor(
    private router: Router,
    private http: HttpClient,
    private carritoService: CarritoService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.carritoService.obtenerOCrearCarrito().subscribe({
      next: (carrito) => {
        this.idCarrito = carrito.idCarrito;
        this.carritoService.obtenerItems(carrito.idCarrito).subscribe({
          next: (items) => {
            this.items = items;
            this.pedido.valorTotal = this.calcularTotal() + 8000;
            this.pago.cantidadPago = this.pedido.valorTotal;
            this.loading = false;
            this.cdr.detectChanges();
          },
          error: (err) => {
            console.error('Error cargando items:', err);
            this.loading = false;
          }
        });
      },
      error: (err) => {
        console.error('Error cargando carrito:', err);
        this.loading = false;
      }
    });
  }

  calcularTotal(): number {
    return this.items.reduce((acc, item) => acc + item.subtotal, 0);
  }

  confirmarPago(): void {
    if (!this.direccion || !this.ciudad) {
      alert('Por favor completa la dirección de entrega.');
      return;
    }

    if (this.pago.metodoPago === 'tarjeta') {
      if (!this.numeroTarjeta || !this.vencimiento || !this.cvv || !this.nombreTarjeta) {
        alert('Por favor completa los datos de la tarjeta.');
        return;
      }
    }

    this.procesando = true;


    const direccionData = {
      calle: this.direccion,
      ciudad: this.ciudad,
      departamento: this.departamento,
      codigoPostal: this.codigoPostal,
      indicaciones: this.indicaciones
    };

    this.http.post(
      'http://localhost:8081/direccion/crearMia',
      direccionData,
      { responseType: 'text' }
    ).subscribe({
      next: (respDireccion) => {
        const idDireccion = Number(respDireccion.split('ID: ')[1]);

        // Paso 2: confirmar carrito → crea el pedido
        this.http.post(
          `http://localhost:8081/pedido/confirmarCarrito/${this.idCarrito}`,
          {},
          { responseType: 'text' }
        ).subscribe({
          next: (respPedido) => {
            const idPedido = Number(respPedido.split('ID: ')[1]);

            // Paso 3: crear envío
            const envioData = {
              idPedido,
              idDireccion,
              tipoEntrega: this.tipoEntrega
            };

            this.http.post(
              'http://localhost:8081/envio/crear',
              envioData,
              { responseType: 'text' }
            ).subscribe({
              next: () => {

                // Paso 4: crear pago
                const pagoData = {
                  idPedido,
                  cantidadPago: this.pago.cantidadPago,
                  metodoPago: this.pago.metodoPago,
                  estadoTransaccion: 'aprobado',
                  fechaPago: new Date().toISOString().split('T')[0],
                };

                this.http.post(
                  'http://localhost:8081/pago/crear',
                  pagoData,
                  { responseType: 'text' }
                ).subscribe({
                  next: () => {
                    this.procesando = false;
                    alert('¡Pedido confirmado! Gracias por tu compra 🎉');
                    this.router.navigate(['/principal']);
                  },
                  error: (err) => {
                    console.error('Error creando pago:', err);
                    this.procesando = false;
                    alert('Error al procesar el pago.');
                  }
                });
              },
              error: (err) => {
                console.error('Error creando envío:', err);
                this.procesando = false;
                alert('Error al crear el envío.');
              }
            });
          },
          error: (err) => {
            console.error('Error confirmando carrito:', err);
            this.procesando = false;
            alert('Error al confirmar el pedido.');
          }
        });
      },
      error: (err) => {
        console.error('Error creando dirección:', err);
        this.procesando = false;
        alert('Error al guardar la dirección.');
      }
    });
  }
}

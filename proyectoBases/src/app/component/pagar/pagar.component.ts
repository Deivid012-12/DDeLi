import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

import { ItemCarrito } from '../../model/carrito.model';
import { Pago, Pedido } from '../../model/pagar.model';

import { CarritoService } from '../../service/carrito.service';
import {
  PromocionService,
  PromocionSeleccionada
} from '../../service/promocion.service';

import {
  EventoService,
  EventoSeleccionado
} from '../../service/evento.service';

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

  promoActiva: PromocionSeleccionada | null = null;
  eventoActivo: EventoSeleccionado | null = null;

  // ✅ MODAL COMPRA EXITOSA
  mostrarModalCompra = false;

  // 🔔 TOAST ALERTAS
  tipoAlerta: 'error' | 'exito' | 'info' = 'info';
  mensajeAlerta: string = '';
  mostrarAlerta = false;

  constructor(
    private router: Router,
    private http: HttpClient,
    private carritoService: CarritoService,
    private cdr: ChangeDetectorRef,
    private promocionService: PromocionService,
    private eventoService: EventoService
  ) {}

  ngOnInit(): void {

    this.promoActiva = this.promocionService.obtenerPromo();
    this.eventoActivo = this.eventoService.obtenerEvento();

    this.carritoService.obtenerOCrearCarrito().subscribe({

      next: (carrito) => {

        this.idCarrito = carrito.idCarrito;

        this.carritoService.obtenerItems(carrito.idCarrito).subscribe({

          next: (items) => {

            this.items = items;

            this.pedido.valorTotal =
              this.calcularTotal() + 8000;

            this.pago.cantidadPago =
              this.pedido.valorTotal;

            this.loading = false;
            this.cdr.detectChanges();
          },

          error: (err) => {
            console.error('Error cargando items:', err);
            this.mostrarMensaje('error', 'Error cargando productos del carrito');
            this.loading = false;
          }

        });

      },

      error: (err) => {
        console.error('Error cargando carrito:', err);
        this.mostrarMensaje('error', 'Error cargando carrito');
        this.loading = false;
      }

    });

  }

  // 🔔 ALERTA UNIFICADA
  mostrarMensaje(tipo: 'error' | 'exito' | 'info', mensaje: string): void {
    this.tipoAlerta = tipo;
    this.mensajeAlerta = mensaje;
    this.mostrarAlerta = true;

    setTimeout(() => {
      this.mostrarAlerta = false;
    }, 2500);
  }

  calcularSubtotal(): number {
    return this.items.reduce((acc, item) => acc + item.subtotal, 0);
  }

  calcularDescuento(): number {
    if (!this.promoActiva) return 0;
    return this.calcularSubtotal() * this.promoActiva.porcentajeDescuento / 100;
  }

  calcularTotal(): number {
    return this.calcularSubtotal() - this.calcularDescuento();
  }

  confirmarPago(): void {

    if (!this.direccion || !this.ciudad) {
      this.mostrarMensaje('error', 'Por favor completa la dirección de entrega.');
      return;
    }

    if (this.pago.metodoPago === 'tarjeta') {
      if (!this.numeroTarjeta || !this.vencimiento || !this.cvv || !this.nombreTarjeta) {
        this.mostrarMensaje('error', 'Por favor completa los datos de la tarjeta.');
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

        let urlConfirmar =
          `http://localhost:8081/pedido/confirmarCarrito/${this.idCarrito}`;

        const params = [];

        if (this.promoActiva?.idPromocion) {
          params.push(`idPromocion=${this.promoActiva.idPromocion}`);
        }

        if (this.eventoActivo?.idEvento) {
          params.push(`idEvento=${this.eventoActivo.idEvento}`);
        }

        if (params.length > 0) {
          urlConfirmar += '?' + params.join('&');
        }

        this.http.post(urlConfirmar, {}, { responseType: 'text' }).subscribe({

          next: (respPedido) => {

            const idPedido = Number(respPedido.split('ID: ')[1]);

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

                const pagoData = {
                  idPedido,
                  cantidadPago: this.calcularTotal() + 8000,
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

                    this.promocionService.limpiar();
                    this.eventoService.limpiar();

                    this.procesando = false;

                    this.mostrarMensaje('exito', '¡Compra realizada con éxito! 🎉');

                    this.mostrarModalCompra = true;

                    this.cdr.detectChanges();
                  },

                  error: () => {
                    this.procesando = false;
                    this.mostrarMensaje('error', 'Error al procesar el pago.');
                  }

                });

              },

              error: () => {
                this.procesando = false;
                this.mostrarMensaje('error', 'Error al crear el envío.');
              }

            });

          },

          error: () => {
            this.procesando = false;
            this.mostrarMensaje('error', 'Error al confirmar el pedido.');
          }

        });

      },

      error: () => {
        this.procesando = false;
        this.mostrarMensaje('error', 'Error al guardar la dirección.');
      }

    });

  }

  cerrarModalCompra(): void {
    this.mostrarModalCompra = false;
    this.router.navigate(['/principal']);
  }
}

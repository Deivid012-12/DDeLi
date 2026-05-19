// src/app/service/promocion.service.ts
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

export interface PromocionSeleccionada {
  idPromocion: number;
  nombre: string;
  porcentajeDescuento: number;
}

@Injectable({ providedIn: 'root' })
export class PromocionService {

  private promoSubject = new BehaviorSubject<PromocionSeleccionada | null>(null);
  promo$ = this.promoSubject.asObservable();

  seleccionar(promo: PromocionSeleccionada): void {
    this.promoSubject.next(promo);
  }

  limpiar(): void {
    this.promoSubject.next(null);
  }

  obtenerPromo(): PromocionSeleccionada | null {
    return this.promoSubject.getValue();
  }
}

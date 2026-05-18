import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

export interface EventoSeleccionado {
  idEvento: number;
  tipoEvento: string;
  fechaEvento: string;
  numeroPersonas: number;
}

@Injectable({ providedIn: 'root' })
export class EventoService {

  private eventoSubject = new BehaviorSubject<EventoSeleccionado | null>(null);
  evento$ = this.eventoSubject.asObservable();

  seleccionar(evento: EventoSeleccionado): void {
    this.eventoSubject.next(evento);
  }

  limpiar(): void {
    this.eventoSubject.next(null);
  }

  obtenerEvento(): EventoSeleccionado | null {
    return this.eventoSubject.getValue();
  }
}

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Carrito } from '../model/carrito.model';

@Injectable({ providedIn: 'root' })
export class CarritoService {
  private url = 'http://localhost:8081/carrito';

  constructor(private http: HttpClient) {}

  getCarrito(idCarrito: number): Observable<Carrito> {
    return this.http.get<Carrito>(`${this.url}/${idCarrito}`);
  }
}

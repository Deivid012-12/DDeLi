import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { BehaviorSubject, Observable, switchMap } from 'rxjs';
import { Carrito, ItemCarrito, Producto } from '../model/carrito.model';

@Injectable({
  providedIn: 'root'
})
export class CarritoService {
  private apiUrl = 'http://localhost:8081/api/carrito';
  private carritoSubject = new BehaviorSubject<Carrito | null>(null);
  carrito$ = this.carritoSubject.asObservable();

  constructor(private http: HttpClient) {}

  obtenerOCrearCarrito(): Observable<Carrito> {

    return this.http.get<Carrito>(
      `${this.apiUrl}/verCarrito`
    );

  }


  obtenerItems(idCarrito: number): Observable<ItemCarrito[]> {
    return this.http.get<ItemCarrito[]>(`${this.apiUrl}/obtenerItems/${idCarrito}`);
  }

  // Agregar producto al carrito - VERSIÓN SIMPLIFICADA Y CORRECTA
  agregarProducto(idCarrito: number, idProducto: number, cantidad: number = 1): Observable<any> {
    const params = new HttpParams()
      .set('idProducto', idProducto.toString())
      .set('cantidad', cantidad.toString());

    return this.http.post(`${this.apiUrl}/agregarProducto/${idCarrito}`, {}, { params });
  }

  agregarDesdeMenu(producto: Producto): Observable<any> {
    return this.obtenerOCrearCarrito().pipe(
      switchMap(carrito =>
        this.http.post(
          `${this.apiUrl}/agregarProducto/${carrito.idCarrito}?idProducto=${producto.idProducto}&cantidad=1`,
          {},
          { responseType: 'text' }
        )
      )
    );
  }

  actualizarCantidad(idItem: number, cantidad: number): Observable<any> {
    const params = new HttpParams().set('cantidad', cantidad.toString());
    return this.http.put(
      `${this.apiUrl}/actualizarCantidad/${idItem}`,
      {},
      { params, responseType: 'text' }   // ← agregar esto
    );
  }

  eliminarItem(idItem: number): Observable<any> {
    return this.http.delete(
      `${this.apiUrl}/eliminarProducto/${idItem}`,
      { responseType: 'text' }           // ← agregar esto
    );
  }

  // Obtener total
  obtenerTotal(idCarrito: number): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/generarTotal/${idCarrito}`);
  }

  // Vaciar carrito
  vaciarCarrito(idCarrito: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/vaciarCarrito/${idCarrito}`);
  }
}

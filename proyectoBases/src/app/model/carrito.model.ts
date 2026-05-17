export interface Producto {
  idProducto: number;
  nombre: string;
  descripcion: string;
  precioBase: number;      // ← era precio, ahora es precioBase
  disponibilidad: boolean; // ← agregar
  tipo: string;            // ← agregar
  nombreCategoria: string; // ← agregar
  imagenURL: string;       // ← era imagen, ahora es imagenURL
}
export interface OpcionPersonalizacion {
  idOpcion: number;
  nombre: string;
  costoAdicional: number;
  idTipo: number;
  nombreTipo: string;
}

export interface ItemCarrito {
  idItem?: number;
  cantidad: number;
  precioUnitario: number;
  subtotal: number;
  idCarrito: number;
  idProducto: number;
  producto: Producto;
  opciones?: OpcionPersonalizacion[];
}

export interface Carrito {
  idCarrito: number;
  estado: string;
  fechaCreacion: string;
  idUsuario?: number;
  items?: ItemCarrito[];
}


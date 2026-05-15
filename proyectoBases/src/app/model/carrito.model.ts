export interface Producto {
  idProducto: number;
  nombre: string;
  descripcion: string;
  precioBase: number;
  imagenURL?: string;
  tipo?: string;
  nombreCategoria?: string;
}

export interface ItemCarrito {
  idItem?: number;
  cantidad: number;
  precioUnitario: number;
  subtotal: number;
  idCarrito: number;
  idProducto: number;
  producto: Producto;
}

export interface Carrito {
  idCarrito: number;
  estado: string;
  fechaCreacion: string;
  idUsuario?: number;
  items?: ItemCarrito[];
}

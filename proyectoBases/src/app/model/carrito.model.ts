export interface Producto {
  idProducto: number;
  nombre: string;
  descripcion: string;
  precio: number;
  imagen: string;
}

export interface ItemCarrito {
  idItem: number;
  cantidad: number;
  precioUnitario: number;
  subtotal: number;
  producto: Producto;
}

export interface Carrito {
  idCarrito: number;
  estado: string;
  fechaCreacion: string;
  items: ItemCarrito[];
}

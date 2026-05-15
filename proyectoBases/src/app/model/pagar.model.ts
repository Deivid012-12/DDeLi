export interface Pago {
  cantidadPago: number;
  metodoPago: string;
  estadoTransaccion: string;
  fechaPago: string;
}

export interface Pedido {
  fechaPedido: string;
  valorTotal: number;
}

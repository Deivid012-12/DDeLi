export interface Pago {
  idPedido: number;
  cantidadPago: number;
  metodoPago: string;
  estadoTransaccion: string;
  fechaPago: string;
}

export interface Pedido {
  idPedido?: number;
  fechaPedido: string;
  valorTotal: number;
}

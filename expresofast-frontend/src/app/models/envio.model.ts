export interface Envio {
  id?: number;
  codigoRastreo?: string;
  numeroRastreo?: string;
  destinatario: string;
  direccionDestino: string;
  montoFlete: number;
  estado: string;
}
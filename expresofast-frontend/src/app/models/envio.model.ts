export interface Envio {
<<<<<<< HEAD
  id?: number;
  codigoRastreo?: string;
  numeroRastreo?: string;
=======
  id: number;
  codigoRastreo: string;
>>>>>>> 1c90a2d51792e18d6e339edf0ebc941348ec1ce9
  destinatario: string;
  direccionDestino: string;
  montoFlete: number;
  estado: string;
<<<<<<< HEAD
}
=======
  fechaCreacion: string;
}

export interface CrearEnvioPayload {
  destinatario: string;
  direccionDestino: string;
  montoFlete: number;
}

export interface ActualizarEstadoPayload {
  estado: string;
}
>>>>>>> 1c90a2d51792e18d6e339edf0ebc941348ec1ce9

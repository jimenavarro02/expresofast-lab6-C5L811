<<<<<<< HEAD
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Envio } from '../models/envio.model';
=======
import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Envio, CrearEnvioPayload, ActualizarEstadoPayload } from '../models/envio.model';
>>>>>>> 1c90a2d51792e18d6e339edf0ebc941348ec1ce9

@Injectable({
  providedIn: 'root'
})
export class EnvioService {
<<<<<<< HEAD
  private apiUrl = 'http://localhost:8080/api/envios';

  constructor(private http: HttpClient) {}
=======
  private http = inject(HttpClient);
  private apiUrl = environment.API_URL + 'envios';
>>>>>>> 1c90a2d51792e18d6e339edf0ebc941348ec1ce9

  obtenerEnvios(): Observable<Envio[]> {
    return this.http.get<Envio[]>(this.apiUrl);
  }

  obtenerPorRastreo(codigo: string): Observable<Envio> {
<<<<<<< HEAD
    return this.http.get<Envio>(`${this.apiUrl}/rastreo/${codigo}`);
  }

  crearEnvio(envio: Envio): Observable<Envio> {
    return this.http.post<Envio>(this.apiUrl, envio);
  }

  actualizarEstado(id: number, estado: string): Observable<Envio> {
    return this.http.put<Envio>(`${this.apiUrl}/${id}/estado`, { estado });
  }
}
=======
    return this.http.get<Envio>(
      `${this.apiUrl}/rastreo/${encodeURIComponent(codigo)}`
    );
  }

  crearEnvio(payload: CrearEnvioPayload): Observable<Envio> {
    return this.http.post<Envio>(this.apiUrl, payload);
  }

  actualizarEstado(id: number, nuevoEstado: string): Observable<Envio> {
    const payload: ActualizarEstadoPayload = { estado: nuevoEstado };
    return this.http.patch<Envio>(`${this.apiUrl}/${id}/estado`, payload);
  }
}
>>>>>>> 1c90a2d51792e18d6e339edf0ebc941348ec1ce9

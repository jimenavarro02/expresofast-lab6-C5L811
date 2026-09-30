import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Envio } from '../models/envio.model';

@Injectable({
  providedIn: 'root'
})
export class EnvioService {
  private apiUrl = 'http://localhost:8080/api/envios';

  constructor(private http: HttpClient) {}

  obtenerEnvios(): Observable<Envio[]> {
    return this.http.get<Envio[]>(this.apiUrl);
  }

  obtenerPorRastreo(codigo: string): Observable<Envio> {
    return this.http.get<Envio>(`${this.apiUrl}/rastreo/${codigo}`);
  }

  crearEnvio(envio: Envio): Observable<Envio> {
    return this.http.post<Envio>(this.apiUrl, envio);
  }

  actualizarEstado(id: number, estado: string): Observable<Envio> {
    return this.http.put<Envio>(`${this.apiUrl}/${id}/estado`, { estado });
  }
}
import { CommonModule } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { EnvioService } from '../../services/envio.service';
import { Envio } from '../../models/envio.model';

@Component({
  selector: 'app-envio-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './envio-list.component.html',
  styleUrl: './envio-list.component.css'
})
export class EnvioListComponent implements OnInit {
  private envioService = inject(EnvioService);

  envios: Envio[] = [];
  cargando = true;
  error = '';

  estados = ['PENDIENTE', 'EN_TRANSITO', 'ENTREGADO', 'CANCELADO'];

  ngOnInit(): void {
    this.cargarEnvios();
  }

  cargarEnvios(): void {
    this.cargando = true;
    this.error = '';

    this.envioService.obtenerEnvios().subscribe({
      next: (datos: Envio[]) => {
        this.envios = datos;
        this.cargando = false;
      },
      error: () => {
        this.error = 'No se pudieron cargar los envíos.';
        this.cargando = false;
      }
    });
  }

  cambiarEstado(envio: Envio, nuevoEstado: string): void {
    if (envio.id === undefined) {
      this.error = 'El envío no tiene un ID válido.';
      return;
    }

    this.envioService.actualizarEstado(envio.id, nuevoEstado).subscribe({
      next: (actualizado: Envio) => {
        envio.estado = actualizado.estado;
      },
      error: () => {
        this.error = 'No se pudo actualizar el estado.';
        this.cargarEnvios();
      }
    });
  }

  claseEstado(estado: string): string {
    return estado ? estado.toLowerCase() : '';
  }
}
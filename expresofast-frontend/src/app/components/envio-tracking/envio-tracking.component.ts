import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { EnvioService } from '../../services/envio.service';
import { Envio } from '../../models/envio.model';

@Component({
  selector: 'app-envio-tracking',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './envio-tracking.component.html',
  styleUrl: './envio-tracking.component.css'
})
export class EnvioTrackingComponent {
  private envioService = inject(EnvioService);

  codigo = '';
  envio: Envio | null = null;
  error = '';

  buscar(): void {
    this.envio = null;
    this.error = '';

    if (!this.codigo.trim()) {
      this.error = 'Ingrese un código de rastreo.';
      return;
    }

    this.envioService.obtenerPorRastreo(this.codigo.trim()).subscribe({
      next: (datos) => this.envio = datos,
      error: () => this.error = 'No se encontró un envío con ese código.'
    });
  }

  progreso(): number {
    if (!this.envio) return 0;
    switch (this.envio.estado) {
      case 'PENDIENTE': return 25;
      case 'EN_TRANSITO': return 60;
      case 'ENTREGADO': return 100;
      case 'CANCELADO': return 100;
      default: return 0;
    }
  }
}

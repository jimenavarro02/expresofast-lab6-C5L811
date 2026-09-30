import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
<<<<<<< HEAD
import { ReactiveFormsModule, FormBuilder, FormGroup, FormArray, Validators } from '@angular/forms';
=======
import { FormsModule } from '@angular/forms';
>>>>>>> 1c90a2d51792e18d6e339edf0ebc941348ec1ce9
import { Router } from '@angular/router';
import { EnvioService } from '../../services/envio.service';

@Component({
  selector: 'app-envio-form',
  standalone: true,
<<<<<<< HEAD
  imports: [CommonModule, ReactiveFormsModule],
=======
  imports: [CommonModule, FormsModule],
>>>>>>> 1c90a2d51792e18d6e339edf0ebc941348ec1ce9
  templateUrl: './envio-form.component.html',
  styleUrl: './envio-form.component.css'
})
export class EnvioFormComponent {
<<<<<<< HEAD
  private fb = inject(FormBuilder);
  private envioService = inject(EnvioService);
  private router = inject(Router);

  // Definición del formulario reactivo con FormArray para los paquetes
  envioForm: FormGroup = this.fb.group({
    numeroRastreo: ['', Validators.required],
    paquetes: this.fb.array([this.crearPaqueteGroup()])
  });
=======
  private envioService = inject(EnvioService);
  private router = inject(Router);

  destinatario = '';
  direccionDestino = '';
  montoFlete: number | null = null;
>>>>>>> 1c90a2d51792e18d6e339edf0ebc941348ec1ce9

  mensaje = '';
  error = '';
  guardando = false;

<<<<<<< HEAD
  // Getter para acceder fácilmente al FormArray desde el HTML
  get paquetes(): FormArray {
    return this.envioForm.get('paquetes') as FormArray;
  }

  // Método para crear cada grupo de campos de un paquete individual
  crearPaqueteGroup(): FormGroup {
    return this.fb.group({
      descripcion: ['', Validators.required],
      peso: ['', [Validators.required, Validators.min(0.1)]]
    });
  }

  // Agregar un nuevo paquete dinámicamente al arreglo
  agregarPaquete(): void {
    this.paquetes.push(this.crearPaqueteGroup());
  }

  // Eliminar un paquete del arreglo según su índice
  eliminarPaquete(index: number): void {
    if (this.paquetes.length > 1) {
      this.paquetes.removeAt(index);
    }
  }

=======
>>>>>>> 1c90a2d51792e18d6e339edf0ebc941348ec1ce9
  registrar(): void {
    this.mensaje = '';
    this.error = '';

<<<<<<< HEAD
    if (this.envioForm.invalid) {
      this.error = 'Complete todos los campos correctamente.';
      this.envioForm.markAllAsTouched();
=======
    if (!this.destinatario.trim() || !this.direccionDestino.trim() ||
        this.montoFlete === null || this.montoFlete <= 0) {
      this.error = 'Complete todos los campos correctamente.';
>>>>>>> 1c90a2d51792e18d6e339edf0ebc941348ec1ce9
      return;
    }

    this.guardando = true;

<<<<<<< HEAD
    // Se envían los datos estructurados con el número de rastreo y la lista de paquetes
    this.envioService.crearEnvio(this.envioForm.value).subscribe({
      next: (envio) => {
        this.guardando = false;
        this.mensaje = `Envío registrado correctamente. Código: ${envio.numeroRastreo}`;
        this.envioForm.reset();
        // Reiniciar con al menos un paquete vacío en el arreglo
        this.paquetes.clear();
        this.agregarPaquete();
=======
    this.envioService.crearEnvio({
      destinatario: this.destinatario,
      direccionDestino: this.direccionDestino,
      montoFlete: this.montoFlete
    }).subscribe({
      next: (envio) => {
        this.guardando = false;
        this.mensaje = `Envío registrado correctamente. Código: ${envio.codigoRastreo}`;
        this.destinatario = '';
        this.direccionDestino = '';
        this.montoFlete = null;
>>>>>>> 1c90a2d51792e18d6e339edf0ebc941348ec1ce9
      },
      error: () => {
        this.guardando = false;
        this.error = 'No se pudo registrar el envío.';
      }
    });
  }

  volver(): void {
    this.router.navigate(['/envios']);
  }
<<<<<<< HEAD
}
=======
}
>>>>>>> 1c90a2d51792e18d6e339edf0ebc941348ec1ce9

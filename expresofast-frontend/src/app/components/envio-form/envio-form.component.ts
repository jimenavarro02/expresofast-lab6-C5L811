import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { ReactiveFormsModule, FormBuilder, FormGroup, FormArray, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { EnvioService } from '../../services/envio.service';

@Component({
  selector: 'app-envio-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './envio-form.component.html',
  styleUrl: './envio-form.component.css'
})
export class EnvioFormComponent {
  private fb = inject(FormBuilder);
  private envioService = inject(EnvioService);
  private router = inject(Router);

  // Definición del formulario reactivo con FormArray para los paquetes
  envioForm: FormGroup = this.fb.group({
    numeroRastreo: ['', Validators.required],
    paquetes: this.fb.array([this.crearPaqueteGroup()])
  });

  mensaje = '';
  error = '';
  guardando = false;

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

  registrar(): void {
    this.mensaje = '';
    this.error = '';

    if (this.envioForm.invalid) {
      this.error = 'Complete todos los campos correctamente.';
      this.envioForm.markAllAsTouched();
      return;
    }

    this.guardando = true;

    // Se envían los datos estructurados con el número de rastreo y la lista de paquetes
    this.envioService.crearEnvio(this.envioForm.value).subscribe({
      next: (envio) => {
        this.guardando = false;
        this.mensaje = `Envío registrado correctamente. Código: ${envio.numeroRastreo}`;
        this.envioForm.reset();
        // Reiniciar con al menos un paquete vacío en el arreglo
        this.paquetes.clear();
        this.agregarPaquete();
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
}
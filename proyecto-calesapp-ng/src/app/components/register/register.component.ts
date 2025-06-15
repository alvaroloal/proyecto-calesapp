import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import Swal from 'sweetalert2';
import { AuthService, UserRegistrationData, RegistrationResponse } from '../../services/auth/auth.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  registrationData: UserRegistrationData = {
    username: '',
    password: '',
    email: '',
    rol: 'ROLE_USER'
  };
  isLoading: boolean = false;
  passwordConfirm: string = '';

  constructor(private authService: AuthService, private router: Router) { }

  onSubmit(registerForm: NgForm): void {
    if (registerForm.invalid) {
      Swal.fire({
        title: 'Campos incompletos',
        text: 'Por favor, completa todos los campos requeridos.',
        icon: 'warning',
        confirmButtonText: 'Aceptar',
        confirmButtonColor: '#2b7fff'
      });
      return;
    }

    if (this.registrationData.password !== this.passwordConfirm) {
      Swal.fire({
        title: 'Error en contraseña',
        text: 'Las contraseñas no coinciden.',
        icon: 'error',
        confirmButtonText: 'Aceptar',
        confirmButtonColor: '#d33'
      });
      return;
    }

    this.isLoading = true;
    this.authService.register(this.registrationData).subscribe({
      next: (response: RegistrationResponse) => {
        this.isLoading = false;
        Swal.fire({
          title: '¡Registro completado!',
          text: `Tu cuenta para "${response.username}" ha sido creada. Serás redirigido para verificarla.`,
          icon: 'success',
          timer: 3000,
          showConfirmButton: false
        });
        this.router.navigate(['/verify-account'], {
          queryParams: {
            token: response.verificationToken,
            username: response.username
          }
        });
      },
      error: (err: Error) => {
        this.isLoading = false;
        Swal.fire({
          title: 'Error de registro',
          text: err.message || 'No se pudo completar el registro. Inténtalo de nuevo.',
          icon: 'error',
          confirmButtonText: 'Aceptar',
          confirmButtonColor: '#d33'
        });
      }
    });
  }
}
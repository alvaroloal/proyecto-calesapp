import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { AuthService } from '../../services/auth/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  username: string = '';
  password: string = '';
  isLoading: boolean = false;
  mostrarFormulario: boolean = false;

  constructor(private authService: AuthService, private router: Router) { }

  ngOnInit(): void {
    console.log('Mostrando formulario de login.');
    this.mostrarFormulario = true;
  }

  onSubmit(): void {
    if (!this.username || !this.password) {
      Swal.fire({
        title: 'Campos incompletos',
        text: 'Por favor, introduce tu nombre de usuario y contraseña.',
        icon: 'warning',
        confirmButtonText: 'Aceptar',
        confirmButtonColor: '#2b7fff'
      });
      return;
    }

    this.isLoading = true;
    this.authService.login(this.username, this.password).subscribe({
      next: (response: any) => {
        this.isLoading = false;

        // guardar el token del bakcend
        if (response && response.token) {
          this.authService.saveToken(response.token);
        }

        Swal.fire({
          title: '¡Éxito!',
          text: 'Has iniciado sesión correctamente.',
          icon: 'success',
          timer: 2600,
          showConfirmButton: false
        });
        this.router.navigate(['/']);
      },
      error: (err: any) => {
        this.isLoading = false;
        let errorMessage = 'Nombre de usuario o contraseña incorrectos.';
        if (err.error && typeof err.error.message === 'string') {
          errorMessage = err.error.message;
        } else if (typeof err.message === 'string') {
          errorMessage = err.message;
        }
        Swal.fire({
          title: 'Error',
          text: errorMessage,
          icon: 'error',
          confirmButtonText: 'Aceptar',
          confirmButtonColor: '#d33'
        });
      }
    });
  }
}
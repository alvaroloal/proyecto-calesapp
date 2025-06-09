import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import Swal from 'sweetalert2';
import { AuthService, LoginResponseData } from '../../services/auth/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  username: string = '';
  password: string = '';
  isLoading: boolean = false;

  constructor(private authService: AuthService, private router: Router) { }

  ngOnInit(): void {
    if (this.authService.isAuthenticated()) {
      console.log('Usuario ya autenticado, redirigiendo...');
      this.router.navigate(['/']);
    } else {
      console.log('Mostrando formulario de login.');
      this.router.navigate(['/login']);
    }
  }

  onSubmit(loginForm?: NgForm): void {
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
      next: (response: LoginResponseData) => {
        this.isLoading = false;

        Swal.fire({
          title: '¡Éxito!',
          text: `Has iniciado sesión correctamente como ${response.username}.`,
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
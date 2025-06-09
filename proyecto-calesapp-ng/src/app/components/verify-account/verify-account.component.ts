import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import Swal from 'sweetalert2';
import { AuthService, UserVerificationData, VerificationResponse } from '../../services/auth/auth.service';

@Component({
  selector: 'app-verify-account',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './verify-account.component.html',
  styleUrls: ['./verify-account.component.css']
})
export class VerifyAccountComponent implements OnInit {
  isLoading: boolean = false;
  usernameFromUrl: string | null = null;
  verificationTokenFromUrl: string | null = null;
  passwordForVerification: string = '';

  constructor(
    private authService: AuthService,
    private route: ActivatedRoute,
    public router: Router
  ) { }

  ngOnInit(): void {
    this.verificationTokenFromUrl = this.route.snapshot.queryParamMap.get('token');
    this.usernameFromUrl = this.route.snapshot.queryParamMap.get('username');

    if (!this.verificationTokenFromUrl || !this.usernameFromUrl) {
      Swal.fire({
        title: 'Error de verificación',
        text: 'No se encontró la información necesaria para verificar la cuenta. Por favor, intenta registrarte de nuevo o contacta al admin.',
        icon: 'error',
        confirmButtonText: 'Ir a registro',
        confirmButtonColor: '#d33'
      }).then(() => {
        this.router.navigate(['/register']);
      });
    }
  }

  onSubmit(verifyForm: NgForm): void {
    if (verifyForm.invalid) {
      Swal.fire({
        title: 'Contraseña Requerida',
        text: 'Por favor, ingresa tu contraseña para completar la verificación.',
        icon: 'warning',
        confirmButtonText: 'Aceptar',
        confirmButtonColor: '#2b7fff'
      });
      return;
    }

    if (this.verificationTokenFromUrl && this.usernameFromUrl) {
      this.isLoading = true;
      const verificationData: UserVerificationData = {
        username: this.usernameFromUrl,
        password: this.passwordForVerification
      };

      this.authService.verifyAccount(this.verificationTokenFromUrl, verificationData).subscribe({
        next: (response: VerificationResponse) => {
          this.isLoading = false;
          Swal.fire({
            title: '¡Cuenta verificada!',
            text: `Tu cuenta para "${response.username}" ha sido verificada exitosamente. Ahora puedes iniciar sesión.`,
            icon: 'success',
            timer: 3000,
            showConfirmButton: false
          });
          this.router.navigate(['/login']);
        },
        error: (err: Error) => {
          this.isLoading = false;
          Swal.fire({
            title: 'Error de verificación',
            text: err.message || 'No se pudo verificar la cuenta. Verifica tu contraseña o intenta de nuevo.',
            icon: 'error',
            confirmButtonText: 'Aceptar',
            confirmButtonColor: '#d33'
          });
        }
      });
    } else {
      Swal.fire('Error interno', 'Falta información para procesar la verificación.', 'error');
    }
  }
}
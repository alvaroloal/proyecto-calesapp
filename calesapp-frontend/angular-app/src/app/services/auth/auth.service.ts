import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { Router } from '@angular/router';

//hacer el login, guardar y obtener el token, gestionar el estado de autenticacion y cerrar sesion,

export interface AuthResponseData {
  token: string;
  userId: string;
  username: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl = 'http://localhost:8080';
  private tokenKey = 'authToken'; //  guardar el token en sessionStorage

  constructor(private http: HttpClient, private router: Router) { }

  /**
   * @param username
   * @param password
   * @returns
   */
  login(username: string, password: string): Observable<AuthResponseData> {
    const loginUrl = `${this.baseUrl}/auth/login`;
    return this.http.post<AuthResponseData>(loginUrl, { username, password })
      .pipe(
        tap(response => {
          if (response && response.token) {
            this.saveToken(response.token);
            console.log('Token guardado después del login.');
          }
        }),
        catchError(this.handleError)
      );
  }

  /**
   * guardar el token sessionStorage
   * @param token token JWT
   */
  saveToken(token: string): void {
    sessionStorage.setItem(this.tokenKey, token);
  }

  /**
   * get el token de autenticacion del sessionStorage.
   * @returns
   */
  getToken(): string | null {
    return sessionStorage.getItem(this.tokenKey);
  }


  logout(): void {
    sessionStorage.removeItem(this.tokenKey);
    console.log('Sesión cerrada y token eliminado.');
    this.router.navigate(['/login']);
  }


  isAuthenticated(): boolean {
    const token = this.getToken();
    return !!token;
  }

  /**
   * errores HTTP
   * @param error
   */
  private handleError(error: HttpErrorResponse) {
    let errorMessage = 'Ocurrió un error desconocido.';
    if (error.error instanceof ErrorEvent) {
      errorMessage = `Error: ${error.error.message}`;
    } else {
      if (error.status === 0) {
        errorMessage = 'No se pudo conectar con el servidor. Verifica la conexión o la URL del backend.';
      } else if (error.status === 401) {
        errorMessage = 'No autorizado. Verifica tus credenciales.';
      } else if (error.error && error.error.message) {
        errorMessage = error.error.message;
      } else {
        errorMessage = `Error ${error.status}: ${error.message}`;
      }
    }
    console.error(errorMessage);
    return throwError(() => new Error(errorMessage));
  }

  // validacion token
  validateToken(token: string): Observable<any> {
    const validateUrl = `${this.baseUrl}/auth/validate`;
    return this.http.post<any>(validateUrl, { token })
      .pipe(
        tap(response => {
          if (response && response.success) {
            this.saveToken(token);
          } else {
            this.logout();
          }
        }),
        catchError(err => {
          this.logout();
          return throwError(err);
        })
      );
  }

  getTokenFromUrl(): string | null {
    const params = new URLSearchParams(window.location.search);
    return params.get('token');
  }
}
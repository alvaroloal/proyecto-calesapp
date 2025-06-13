import { Injectable, Inject, PLATFORM_ID } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { BehaviorSubject, Observable, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { Router } from '@angular/router';
import { jwtDecode } from 'jwt-decode';
import { isPlatformBrowser } from '@angular/common';

// interfaces para datos de las peticiones
export interface UserRegistrationData {
  username: string;
  password: string;
  email: string;
  rol: string;
}

export interface UserVerificationData {
  username: string;
  password: string;
}

// interfaces para respuestas d API
export interface RegistrationResponse {
  id: string;
  username: string;
  verificationToken: string;
}

export interface VerificationResponse {
  id: string;
  username: string;
}

export interface LoginResponseData {
  id: string;
  username: string;
  token: string; // JWT
  refreshToken: string;
}

// interfaces para estado interno
export interface UserData {
  id: string;
  username: string;
  rol: string;

}

interface DecodedToken {
  exp: number;
  iat: number;
  sub?: string;
  username?: string;
  rol?: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl = '';
  private jwtTokenKey = 'authToken';
  private refreshTokenKey = 'refreshToken';

  private currentUserSubject: BehaviorSubject<UserData | null>;
  public currentUser: Observable<UserData | null>;

  constructor(private http: HttpClient, private router: Router, @Inject(PLATFORM_ID) private platformId: object) {
    if (isPlatformBrowser(this.platformId)) {
      this.currentUserSubject = new BehaviorSubject<UserData | null>(this.loadInitialUser());
    } else {
      this.currentUserSubject = new BehaviorSubject<UserData | null>(null);
    }
    this.currentUser = this.currentUserSubject.asObservable();
  }

  private loadInitialUser(): UserData | null {
    if (!isPlatformBrowser(this.platformId)) return null;

    const token = this.getJwtToken();
    if (token) {
      try {
        const decodedToken = jwtDecode<DecodedToken>(token);
        if (decodedToken.exp * 1000 > Date.now()) {
          return {
            id: decodedToken.sub || '',
            username: decodedToken.username || '',
            rol: decodedToken.rol || ''
          };
        } else {
          this.clearTokens();
        }
      } catch (error) {
        console.error("Error decodificando token al inicio:", error);
        this.clearTokens();
      }
    }
    return null;
  }

  public get currentUserValue(): UserData | null {
    return this.currentUserSubject.value;
  }

  register(userData: UserRegistrationData): Observable<RegistrationResponse> {
    const registerUrl = `${this.baseUrl}/auth/register`;
    console.log(registerUrl);
    return this.http.post<RegistrationResponse>(registerUrl, userData)
      .pipe(
        tap(response => console.log('Respuesta de registro:', response)),
        catchError(this.handleError)
      );
  }

  verifyAccount(verificationToken: string, verificationData: UserVerificationData): Observable<VerificationResponse> {
    const verifyUrl = `${this.baseUrl}/auth/user/verify`;
    const params = new HttpParams().set('token', verificationToken);
    return this.http.put<VerificationResponse>(verifyUrl, verificationData, { params })
      .pipe(
        tap(response => console.log('Respuesta de verificación:', response)),
        catchError(this.handleError)
      );
  }

  login(username: string, password: string): Observable<LoginResponseData> {
    const loginUrl = `${this.baseUrl}/auth/login`;
    return this.http.post<LoginResponseData>(loginUrl, { username, password })
      .pipe(
        tap(response => {
          if (response && response.token && response.refreshToken) {
            this.saveJwtToken(response.token);
            this.saveRefreshToken(response.refreshToken);

            let userData: UserData;

            try {
              const decodedToken = jwtDecode<DecodedToken>(response.token);
              userData = {
                id: response.id ?? decodedToken.sub ?? '',
                username: response.username ?? decodedToken.username ?? '',
                rol: decodedToken.rol ?? ''
              };
            } catch (error) {
              console.error("Error decodificando token tras login:", error);
              userData = {
                id: response.id ?? '',
                username: response.username ?? '',
                rol: ''
              };
            }
            this.currentUserSubject.next(userData);
          } else {
            this.logout(); // manejo de error si no hay token
          }
        }),
        catchError(this.handleError)
      );
  }


  saveJwtToken(token: string): void {
    if (isPlatformBrowser(this.platformId)) {
      sessionStorage.setItem(this.jwtTokenKey, token);
    }
  }

  getJwtToken(): string | null {
    return isPlatformBrowser(this.platformId) ? sessionStorage.getItem(this.jwtTokenKey) : null;
  }

  saveRefreshToken(token: string): void {
    if (isPlatformBrowser(this.platformId)) {
      sessionStorage.setItem(this.refreshTokenKey, token);
    }
  }

  getRefreshToken(): string | null {
    return isPlatformBrowser(this.platformId) ? sessionStorage.getItem(this.refreshTokenKey) : null;
  }

  logout(): void {
    this.clearTokens();
    this.currentUserSubject.next(null);
    this.router.navigate(['/login']);
  }

  private clearTokens(): void {
    if (isPlatformBrowser(this.platformId)) {
      sessionStorage.removeItem(this.jwtTokenKey);
      sessionStorage.removeItem(this.refreshTokenKey);
    }
  }

  isAuthenticated(): boolean {
    const token = this.getJwtToken();
    if (!token) return false;
    try {
      const decodedToken = jwtDecode<DecodedToken>(token);
      if (decodedToken.exp * 1000 < Date.now()) {
        this.logout();
        return false;
      }
      return true;
    } catch (error) {
      this.logout();
      return false;
    }
  }

  getTokenFromUrl(paramName: string = 'token'): string | null {
    const params = new URLSearchParams(window.location.search);
    return params.get(paramName);
  }

  private handleError = (error: HttpErrorResponse) => {
    let errorMessage = 'Error desconocido.';
    if (error.error instanceof ErrorEvent) {
      errorMessage = `Error: ${error.error.message}`;
    } else {
      if (error.status === 0) {
        errorMessage = 'No se pudo conectar con el servidor.';
      } else if (error.status === 401) {
        errorMessage = error.error?.message || 'No autorizado.';
      } else if (error.error && error.error.message) {
        errorMessage = error.error.message;
      } else {
        errorMessage = `Error ${error.status}: ${error.message || 'Error del servidor'}`;
      }
    }
    console.error(errorMessage, error);
    return throwError(() => new Error(errorMessage));
  }



}

import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { Parada } from '../../models/parada.model';
import { AuthService } from '../auth/auth.service';

@Injectable({
  providedIn: 'root'
})
export class ParadasService {

  private apiUrl = '/api/paradas';

  constructor(
    private http: HttpClient,
    private authService: AuthService
  ) { }

  getParadas(): Observable<Parada[]> {
    return this.http.get<{ count: number; items: Parada[] }>(this.apiUrl)
      .pipe(map(response => response.items));
  }

  getParadaById(id: number): Observable<Parada> {
    return this.http.get<Parada>(`${this.apiUrl}/${id}`);
  }

  eliminarParada(id: number): Observable<void> {
    const token = this.authService.getJwtToken();
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    return this.http.delete<void>(`${this.apiUrl}/${id}`, { headers });
  }

  crearParada(parada: Parada): Observable<Parada> {
    const token = this.authService.getJwtToken();
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    return this.http.post<Parada>(this.apiUrl, parada, { headers });
  }

  // PUT protegido con token
  actualizarParada(parada: Parada): Observable<Parada> {
    const token = this.authService.getJwtToken();
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    return this.http.put<Parada>(`${this.apiUrl}/${parada.id}`, parada, { headers });
  }
}

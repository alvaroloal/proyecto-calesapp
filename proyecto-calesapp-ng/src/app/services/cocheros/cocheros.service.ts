import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { Cochero } from '../../models/cochero.model';
import { AuthService } from '../auth/auth.service';

@Injectable({
  providedIn: 'root'
})
export class CocherosService {
  private apiUrl = '/api/cocheros';

  constructor(private http: HttpClient, private authService: AuthService) { }

  getCocheros(): Observable<Cochero[]> {
    return this.http.get<{ count: number; items: Cochero[] }>(this.apiUrl)
      .pipe(map(response => response.items));
  }

  getCocheroById(id: number): Observable<Cochero> {
    return this.http.get<Cochero>(`${this.apiUrl}/${id}`);
  }

  crearCochero(cochero: Cochero): Observable<Cochero> {
    const token = this.authService.getJwtToken();
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    return this.http.post<Cochero>(this.apiUrl, cochero, { headers });
  }

  editarCochero(cochero: Cochero): Observable<Cochero> {
    const token = this.authService.getJwtToken();
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    return this.http.put<Cochero>(`${this.apiUrl}/${cochero.id}`, cochero, { headers });
  }



  eliminarCochero(id: number): Observable<void> {
    const token = this.authService.getJwtToken();
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.delete<void>(`${this.apiUrl}/${id}`, { headers });
  }

  contactarCochero(id: number, parada: string, servicioId: number, fecha: string, mensaje: string) {
    const token = this.authService.getJwtToken();
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    const usuarioId = this.authService.getUserId();
    console.log({ parada, servicioId, fecha, mensaje, usuarioId });
    return this.http.post(`/api/contactos`, { servicioId, fecha, mensaje, usuarioId }, { headers });
  }
}


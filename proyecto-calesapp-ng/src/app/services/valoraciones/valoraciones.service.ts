import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Valoracion } from '../../models/valoracion.model';
import { AuthService } from '../auth/auth.service';

@Injectable({
  providedIn: 'root'
})
export class ValoracionesService {
  private apiUrl = '/api/valoraciones';

  constructor(private http: HttpClient, private authService: AuthService) { }

  getValoraciones(): Observable<Valoracion[]> {
    return this.http.get<Valoracion[]>(this.apiUrl);
  }


  getValoracionById(id: number): Observable<Valoracion> {
    return this.http.get<Valoracion>(`${this.apiUrl}/${id}`);
  }

  eliminarValoracion(id: number): Observable<void> {
    const token = this.authService.getJwtToken();
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    return this.http.delete<void>(`${this.apiUrl}/${id}`, { headers });
  }
}

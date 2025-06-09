import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { Parada } from '../../models/parada.model';

@Injectable({
  providedIn: 'root'
})
export class ParadasService {
  private apiUrl = 'http://localhost:8080/api/paradas';

  constructor(private http: HttpClient) { }

  getParadas(): Observable<Parada[]> {
    return this.http.get<{ count: number; items: Parada[] }>(this.apiUrl)
      .pipe(map(response => response.items));
  }

  getParadaById(id: number): Observable<Parada> {
    return this.http.get<Parada>(`${this.apiUrl}/${id}`);
  }
  eliminarParada(id: number): Observable<any> {
    //obtener token de localStorage
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.delete(`${this.apiUrl}/${id}`, { headers });
  }


}

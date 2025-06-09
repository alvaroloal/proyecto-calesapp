import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Valoracion } from '../../models/valoracion.model';

@Injectable({
  providedIn: 'root'
})
export class ValoracionesService {
  private apiUrl = '/api/valoraciones';

  constructor(private http: HttpClient) { }

  getValoraciones(): Observable<Valoracion[]> {
    return this.http.get<Valoracion[]>(this.apiUrl);
  }


  getValoracionById(id: number): Observable<Valoracion> {
    return this.http.get<Valoracion>(`${this.apiUrl}/${id}`);
  }
}

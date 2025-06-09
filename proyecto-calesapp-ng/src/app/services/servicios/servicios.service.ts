import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Servicio } from '../../models/servicio.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ServiciosService {

  private apiUrl = 'http://localhost:8080/api/servicios';

  constructor(private http: HttpClient) { }

  getServicios(): Observable<Servicio[]> {
    return this.http.get<Servicio[]>(this.apiUrl);
  }

  getServicioById(id: number): Observable<Servicio> {
    return this.http.get<Servicio>(`${this.apiUrl}/${id}`);
  }
}

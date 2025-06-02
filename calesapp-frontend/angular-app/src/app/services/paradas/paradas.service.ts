import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ParadasService {
  private apiUrl = 'http://localhost:8080/api/paradas';

  constructor(private http: HttpClient) { }

  getParadas(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  getParadaById(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }
}


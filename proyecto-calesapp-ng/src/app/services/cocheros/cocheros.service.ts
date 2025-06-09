import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { Cochero } from '../../models/cochero.model';

@Injectable({
  providedIn: 'root'
})
export class CocherosService {
  private apiUrl = '/api/cocheros';

  constructor(private http: HttpClient) { }

  getCocheros(): Observable<Cochero[]> {
    return this.http.get<{ count: number; items: Cochero[] }>(this.apiUrl)
      .pipe(map(response => response.items));
  }

  getCocheroById(id: number): Observable<Cochero> {
    return this.http.get<Cochero>(`${this.apiUrl}/${id}`);
  }
}


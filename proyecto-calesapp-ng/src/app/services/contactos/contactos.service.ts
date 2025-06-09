import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Contacto } from '../../models/contacto.model';

@Injectable({
  providedIn: 'root'
})
export class ContactosService {
  private apiUrl = 'http://localhost:8080/api/contactos';

  constructor(private http: HttpClient) { }

  getContactos(): Observable<Contacto[]> {
    return this.http.get<Contacto[]>(this.apiUrl);
  }

  getContactoById(id: number): Observable<Contacto> {
    return this.http.get<Contacto>(`${this.apiUrl}/${id}`);
  }

}

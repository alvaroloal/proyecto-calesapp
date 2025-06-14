import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Contacto } from '../../models/contacto.model';
import { AuthService } from '../auth/auth.service';

@Injectable({
  providedIn: 'root'
})
export class ContactosService {
  private apiUrl = '/api/contactos';

  constructor(private http: HttpClient, private authService: AuthService) { }

  getContactos(): Observable<Contacto[]> {
    return this.http.get<Contacto[]>(this.apiUrl);
  }

  getContactoById(id: number): Observable<Contacto> {
    return this.http.get<Contacto>(`${this.apiUrl}/${id}`);
  }

  crearContacto(contacto: Partial<Contacto>): Observable<Contacto> {
    const token = this.authService.getJwtToken();
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    return this.http.post<Contacto>(this.apiUrl, contacto, { headers });
  }

  //crearContacto(contacto: Contacto): Observable<Contacto> {
  //  const token = this.authService.getJwtToken();
  //  const headers = new HttpHeaders({
  //    'Authorization': `Bearer ${token}`
  //  });
  //
  //  return this.http.post<Contacto>(this.apiUrl, contacto, { headers });
  //}


  editarContacto(id: number, contacto: Contacto): Observable<Contacto> {
    const token = this.authService.getJwtToken();
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    return this.http.put<Contacto>(`${this.apiUrl}/${id}`, contacto, { headers });
  }


  eliminarContacto(id: number): Observable<void> {
    const token = this.authService.getJwtToken();
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    return this.http.delete<void>(`${this.apiUrl}/${id}`, { headers });
  }

}

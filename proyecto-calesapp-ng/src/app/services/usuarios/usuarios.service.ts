import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Usuario } from '../../models/usuario.model';
import { AuthService } from '../auth/auth.service';

import { UsuarioEdit } from '../../models/usuarioEdit.model';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  private apiUrl = '/api/usuarios';

  constructor(private http: HttpClient, private authService: AuthService) { }

  getUsuarios(): Observable<Usuario[]> {
    const token = this.authService.getJwtToken();

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    return this.http.get<Usuario[]>(this.apiUrl, { headers });

  }

  getUsuarioById(id: string): Observable<Usuario> {
    return this.http.get<Usuario>(`${this.apiUrl}/${id}`);
  }

  eliminarUsuario(id: string): Observable<void> {
    const token = this.authService.getJwtToken();
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    return this.http.delete<void>(`${this.apiUrl}/${id}`, { headers });
  }


  editarUsuario(id: string, usuario: UsuarioEdit): Observable<Usuario> {
    const token = this.authService.getJwtToken();
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    return this.http.put<Usuario>(`${this.apiUrl}/${id}`, usuario, { headers });
  }

  bloqueoUsuario(id: string, enabled: boolean): Observable<Usuario> {
    const token = this.authService.getJwtToken();
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.patch<Usuario>(`${this.apiUrl}/${id}/bloqueo`, { enabled }, { headers });
  }
  desbloqueoUsuario(id: string, enabled: boolean): Observable<Usuario> {
    const token = this.authService.getJwtToken();
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.patch<Usuario>(`${this.apiUrl}/${id}/desbloqueo`, { enabled }, { headers });
  }

}

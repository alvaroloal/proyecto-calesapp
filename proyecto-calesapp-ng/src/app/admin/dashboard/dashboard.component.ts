import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Parada } from '../../models/parada.model';
import { ParadasService } from '../../services/paradas/paradas.service';
import { Router, RouterLink, RouterModule } from '@angular/router';
import { CocherosService } from '../../services/cocheros/cocheros.service';
import { Cochero } from '../../models/cochero.model';
import { ValoracionesService } from '../../services/valoraciones/valoraciones.service';
import { Valoracion } from '../../models/valoracion.model';
import { Contacto } from '../../models/contacto.model';
import { ContactosService } from '../../services/contactos/contactos.service';
import { Usuario } from '../../models/usuario.model';
import { UsuarioService } from '../../services/usuarios/usuarios.service';
import { Servicio } from '../../models/servicio.model';
import { ServiciosService } from '../../services/servicios/servicios.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit {
  paradas: Parada[] = [];
  cocheros: Cochero[] = [];
  valoraciones: Valoracion[] = [];
  contactos: Contacto[] = [];
  usuarios: Usuario[] = [];
  servicios: Servicio[] = [];


  currentPage: number = 1;
  itemsPerPage: number = 5;
  totalPages: number = 1;
  currentPageParadas = 1;
  totalPagesParadas = 1;
  currentPageValoraciones = 1;
  totalPagesValoraciones = 1;
  currentPageContactos = 1;
  totalPagesContactos = 1;
  currentPageCocheros = 1;
  totalPagesCocheros = 1;
  totalPagesUsuarios = 1;
  currentPageUsuarios = 1;

  totalPagesServicios = 1;
  currentPageServicios = 1;


  constructor(private serviciosService: ServiciosService, private usuariosService: UsuarioService, private contactosService: ContactosService, private paradasService: ParadasService, private router: Router, private cocherosService: CocherosService, private valoracionesService: ValoracionesService) { }

  ngOnInit(): void {
    this.paradasService.getParadas().subscribe({
      next: (data) => {
        this.paradas = data;
        this.totalPagesParadas = Math.ceil(this.paradas.length / this.itemsPerPage);
      },
      error: (err) => {
        console.error('Error cargando paradas', err);
      }
    });

    this.cocherosService.getCocheros().subscribe({
      next: (data) => {
        this.cocheros = data;
        this.totalPagesCocheros = Math.ceil(this.cocheros.length / this.itemsPerPage);
      },
      error: (err) => {
        console.error('Error cargando cocheros', err);
      }
    });

    this.valoracionesService.getValoraciones().subscribe({
      next: (data) => {
        this.valoraciones = data;
        this.totalPagesValoraciones = Math.ceil(this.valoraciones.length / this.itemsPerPage);
      },
      error: (err) => {
        console.error('Error cargando valoraciones', err);
      }
    });

    this.contactosService.getContactos().subscribe({
      next: (data) => {
        this.contactos = data;
        this.totalPagesContactos = Math.ceil(this.contactos.length / this.itemsPerPage);
      },
      error: (err) => {
        console.error('Error al cargar contactos', err);
      }
    });
    this.usuariosService.getUsuarios().subscribe({
      next: (data) => this.usuarios = data,
      error: (err) => console.error('Error al obtener usuarios:', err)
    });
    this.serviciosService.getServicios().subscribe({
      next: (data) => this.servicios = data,
      error: (err) => console.error('Error al obtener servicios:', err)
    });
  }


  verParada(parada: any): void {
    console.log('Ver detalles de parada:', parada);
    this.router.navigate(['/paradas', parada.id]);

  }

  editarParada(parada: any): void {
    console.log('Editar parada:', parada);
    // puedo abrir un formulario de edición o redirigir a otro componente

  }

  eliminarParada(id: number): void {
    if (confirm('¿Estás seguro de que deseas eliminar esta parada?')) {
      this.paradasService.eliminarParada(id).subscribe({
        next: () => {
          this.paradas = this.paradas.filter(p => p.id !== id);

          this.totalPagesParadas = Math.ceil(this.paradas.length / this.itemsPerPage);

          if (this.currentPageParadas > this.totalPagesParadas) {
            this.currentPageParadas = this.totalPagesParadas;
          }

        },
        error: (err) => {
          console.error('Error al eliminar la parada:', err);
        }
      });
    }
  }

  verCochero(cochero: any): void {
    console.log('Ver detalles de cochero:', cochero);
    this.router.navigate(['/cocheros', cochero.id]);

  }

  editarCochero(cochero: any): void {
    console.log('Editar cochero:', cochero);
    // puedo abrir un formulario de edición o redirigir a otro componente
  }

  verValoracion(valoracion: Valoracion): void {
    this.router.navigate(['/valoraciones', valoracion.id]);
  }

  editarValoracion(valoracion: Valoracion): void {
    this.router.navigate(['/valoraciones/editar', valoracion.id]);
  }

  verContacto(contacto: Contacto): void {
    this.router.navigate(['/contactos', contacto.id]);
  }

  editarContacto(contacto: Contacto): void {
    this.router.navigate(['/contactos/editar', contacto.id]);
  }

  verUsuario(usuario: Usuario): void {
    this.router.navigate(['/usuarios', usuario.id]);
  }

  editarUsuario(usuario: Usuario): void {
    this.router.navigate(['/usuarios/editar', usuario.id]);
  }

  verServicio(servicio: Servicio): void {
    this.router.navigate(['/servicios', servicio.id]);
  }

  editarServicio(servicio: Servicio): void {
    this.router.navigate(['/servicios/editar', servicio.id]);
  }


  //paginación
  get paginatedParadas(): Parada[] {
    const startIndex = (this.currentPageParadas - 1) * this.itemsPerPage;
    const endIndex = startIndex + this.itemsPerPage;
    return this.paradas.slice(startIndex, endIndex);
  }


  get paginatedCocheros(): Cochero[] {
    const startIndex = (this.currentPageCocheros - 1) * this.itemsPerPage;
    const endIndex = startIndex + this.itemsPerPage;
    return this.cocheros.slice(startIndex, endIndex);
  }

  get paginatedValoraciones(): Valoracion[] {
    const startIndex = (this.currentPageValoraciones - 1) * this.itemsPerPage;
    const endIndex = startIndex + this.itemsPerPage;
    return this.valoraciones.slice(startIndex, endIndex);
  }


  get paginatedContactos(): Contacto[] {
    const startIndex = (this.currentPageContactos - 1) * this.itemsPerPage;
    const endIndex = startIndex + this.itemsPerPage;
    return this.contactos.slice(startIndex, endIndex);
  }

  get paginatedUsuarios(): Usuario[] {
    const startIndex = (this.currentPageContactos - 1) * this.itemsPerPage;
    const endIndex = startIndex + this.itemsPerPage;
    return this.usuarios.slice(startIndex, endIndex);
  }

  get paginatedServicios(): Servicio[] {
    const startIndex = (this.currentPageContactos - 1) * this.itemsPerPage;
    const endIndex = startIndex + this.itemsPerPage;
    return this.servicios.slice(startIndex, endIndex);
  }


  nextPageParadas() {
    const totalPages = Math.ceil(this.paradas.length / this.itemsPerPage);
    if (this.currentPageParadas < totalPages) {
      this.currentPageParadas++;
    }
  }

  prevPageParadas() {
    if (this.currentPageParadas > 1) {
      this.currentPageParadas--;
    }
  }

  nextPageCocheros() {
    const totalPages = Math.ceil(this.cocheros.length / this.itemsPerPage);
    if (this.currentPageCocheros < totalPages) {
      this.currentPageCocheros++;
    }
  }

  prevPageCocheros() {
    if (this.currentPageCocheros > 1) {
      this.currentPageCocheros--;
    }
  }

  nextPageValoraciones() {
    const totalPages = Math.ceil(this.valoraciones.length / this.itemsPerPage);
    if (this.currentPageValoraciones < totalPages) {
      this.currentPageValoraciones++;
    }
  }

  prevPageValoraciones() {
    if (this.currentPageValoraciones > 1) {
      this.currentPageValoraciones--;
    }
  }

  nextPageContactos() {
    const totalPages = Math.ceil(this.contactos.length / this.itemsPerPage);
    if (this.currentPageContactos < totalPages) {
      this.currentPageContactos++;
    }
  }

  prevPageContactos() {
    if (this.currentPageContactos > 1) {
      this.currentPageContactos--;
    }
  }

  nextPageUsuarios() {
    const totalPages = Math.ceil(this.usuarios.length / this.itemsPerPage);
    if (this.currentPageUsuarios < totalPages) {
      this.currentPageUsuarios++;
    }
  }

  prevPageUsuarios() {
    if (this.currentPageUsuarios > 1) {
      this.currentPageUsuarios--;
    }
  }

  nextPageServicios() {
    const totalPages = Math.ceil(this.servicios.length / this.itemsPerPage);
    if (this.currentPageServicios < totalPages) {
      this.currentPageServicios++;
    }
  }

  prevPageServicios() {
    if (this.currentPageServicios > 1) {
      this.currentPageServicios--;
    }
  }


}

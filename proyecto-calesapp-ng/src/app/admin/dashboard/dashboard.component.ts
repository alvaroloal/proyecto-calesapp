import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { Parada } from '../../models/parada.model';
import { Cochero } from '../../models/cochero.model';
import { Valoracion } from '../../models/valoracion.model';
import { Contacto } from '../../models/contacto.model';
import { Usuario } from '../../models/usuario.model';
import { ParadasService } from '../../services/paradas/paradas.service';
import { CocherosService } from '../../services/cocheros/cocheros.service';
import { ValoracionesService } from '../../services/valoraciones/valoraciones.service';
import { ContactosService } from '../../services/contactos/contactos.service';
import { UsuarioService } from '../../services/usuarios/usuarios.service';
import { Servicio } from '../../models/servicio.model';
import { ServiciosService } from '../../services/servicios/servicios.service';
import Swal from 'sweetalert2';


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

  itemsPerPage = 5;
  currentPageParadas = 1;
  currentPageValoraciones = 1;
  currentPageContactos = 1;
  currentPageCocheros = 1;
  currentPageUsuarios = 1;
  currentPageServicios = 1;


  constructor(
    private router: Router,
    private paradasService: ParadasService,
    private cocherosService: CocherosService,
    private valoracionesService: ValoracionesService,
    private contactosService: ContactosService,
    private usuariosService: UsuarioService,
    private serviciosService: ServiciosService
  ) { }

  ngOnInit(): void {
    this.paradasService.getParadas().subscribe(data => this.paradas = data);
    this.cocherosService.getCocheros().subscribe(data => this.cocheros = data);
    this.valoracionesService.getValoraciones().subscribe(data => this.valoraciones = data);
    this.contactosService.getContactos().subscribe(data => this.contactos = data);
    this.usuariosService.getUsuarios().subscribe(data => this.usuarios = data);
    this.serviciosService.getServicios().subscribe(data => this.servicios = data);
  }


  // navegacion
  crearValoracion() { this.router.navigate(['/valoraciones/crear']); }
  crearContacto() { this.router.navigate(['/contactos/crear']); }
  crearCochero() { this.router.navigate(['/cocheros/crear']); }
  crearUsuario() { this.router.navigate(['/usuarios/crear']); }
  crearServicio() { this.router.navigate(['/servicios/crear']); }



  crearParadaModal(): void {
    Swal.fire({
      title: 'Crear nueva parada',
      html: `
      <input id="nombre" class="swal2-input" placeholder="Nombre (mín. 3 caracteres)">
      <input id="ubicacion" class="swal2-input" placeholder="Ubicación (lat,long)">
      <textarea id="descripcion" class="swal2-textarea" placeholder="Descripción (mín. 10 caracteres)"></textarea>
    `,
      focusConfirm: false,
      showCancelButton: true,
      confirmButtonText: 'Crear',
      cancelButtonText: 'Cancelar',
      preConfirm: () => {
        const nombre = (document.getElementById('nombre') as HTMLInputElement).value.trim();
        const ubicacion = (document.getElementById('ubicacion') as HTMLInputElement).value.trim();
        const descripcion = (document.getElementById('descripcion') as HTMLTextAreaElement).value.trim();

        if (nombre.length < 3) {
          Swal.showValidationMessage('El nombre debe tener al menos 3 caracteres.');
          return false;
        }

        const ubicacionRegex = /^-?\d+(\.\d+)?,-?\d+(\.\d+)?$/;
        if (!ubicacionRegex.test(ubicacion)) {
          Swal.showValidationMessage('La ubicación debe tener el formato "latitud,longitud", por ejemplo: 40.4168,-3.7038');
          return false;
        }

        if (descripcion.length < 10) {
          Swal.showValidationMessage('La descripción debe tener al menos 10 caracteres.');
          return false;
        }

        return { nombre, ubicacion, descripcion };
      }
    }).then((result) => {
      if (result.isConfirmed && result.value) {
        const parada: Parada = result.value;
        this.paradasService.crearParada(parada).subscribe({
          next: (nuevaParada) => {
            this.paradas.push(nuevaParada);
            Swal.fire({
              icon: 'success',
              title: 'Parada creada',
              text: `Se ha creado correctamente la parada "${nuevaParada.nombre}"`,
              timer: 2200,
              showConfirmButton: false
            });
          },
          error: (err) => {
            console.error('Error creando parada:', err);
            Swal.fire({
              icon: 'error',
              title: 'Error al crear parada',
              text: err.error?.message || 'No se pudo crear la parada.',
              confirmButtonText: 'Aceptar'
            });
          }
        });
      }
    });
  }
  verParada(p: Parada) { this.router.navigate(['/paradas', p.id]); }
  editarParada(p: Parada) { this.router.navigate(['/paradas/editar', p.id]); }
  eliminarParada(id: number): void {
    Swal.fire({
      title: '¿Estás seguro?',
      text: 'Esta acción eliminará la parada permanentemente.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar',
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6'
    }).then(result => {
      if (result.isConfirmed) {
        this.paradasService.eliminarParada(id).subscribe({
          next: () => {
            this.paradas = this.paradas.filter(p => p.id !== id);
            Swal.fire({
              title: 'Eliminado',
              text: 'La parada se ha eliminado correctamente.',
              icon: 'success',
              timer: 2000,
              showConfirmButton: false
            });
          },
          error: () => {
            Swal.fire({
              title: 'Error',
              text: 'No se pudo eliminar la parada.',
              icon: 'error',
              confirmButtonText: 'Aceptar'
            });
          }
        });
      }
    });
  }


  verValoracion(v: Valoracion) { this.router.navigate(['/valoraciones', v.id]); }
  editarValoracion(v: Valoracion) { this.router.navigate(['/valoraciones/editar', v.id]); }
  eliminarValoracion(id: number): void {
    Swal.fire({
      title: '¿Estás seguro?',
      text: 'Esta acción eliminará la valoración permanentemente.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar',
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6'
    }).then(result => {
      if (result.isConfirmed) {
        this.valoracionesService.eliminarValoracion(id).subscribe({
          next: () => {
            this.valoraciones = this.valoraciones.filter(p => p.id !== id);
            Swal.fire({
              title: 'Eliminado',
              text: 'La valoración se ha eliminado correctamente.',
              icon: 'success',
              timer: 2000,
              showConfirmButton: false
            });
          },
          error: () => {
            Swal.fire({
              title: 'Error',
              text: 'No se pudo eliminar la valoración.',
              icon: 'error',
              confirmButtonText: 'Aceptar'
            });
          }
        });
      }
    });
  }


  verContacto(c: Contacto) { this.router.navigate(['/contactos', c.id]); }
  editarContacto(c: Contacto) { this.router.navigate(['/contactos/editar', c.id]); }
  eliminarContacto(id: number): void {
    Swal.fire({
      title: '¿Estás seguro?',
      text: 'Esta acción eliminará el contacto permanentemente.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar',
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6'
    }).then(result => {
      if (result.isConfirmed) {
        this.contactosService.eliminarContacto(id).subscribe({
          next: () => {
            this.contactos = this.contactos.filter(p => p.id !== id);
            Swal.fire({
              title: 'Eliminado',
              text: 'El contacto se ha eliminado correctamente.',
              icon: 'success',
              timer: 2000,
              showConfirmButton: false
            });
          },
          error: () => {
            Swal.fire({
              title: 'Error',
              text: 'No se pudo eliminar la parada.',
              icon: 'error',
              confirmButtonText: 'Aceptar'
            });
          }
        });
      }
    });
  }


  verCochero(c: Cochero) { this.router.navigate(['/cocheros', c.id]); }
  editarCochero(c: Cochero) { this.router.navigate(['/cocheros/editar', c.id]); }
  eliminarCochero(id: number): void {
    Swal.fire({
      title: '¿Estás seguro?',
      text: 'Esta acción eliminará el cochero permanentemente.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar',
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6'
    }).then(result => {
      if (result.isConfirmed) {
        this.cocherosService.eliminarCochero(id).subscribe({
          next: () => {
            this.cocheros = this.cocheros.filter(c => c.id !== id);
            Swal.fire({
              title: 'Eliminado',
              text: 'El cochero se ha eliminado correctamente.',
              icon: 'success',
              timer: 2000,
              showConfirmButton: false
            });
          },
          error: () => {
            Swal.fire({
              title: 'Error',
              text: 'No se pudo eliminar el cochero.',
              icon: 'error',
              confirmButtonText: 'Aceptar'
            });
          }
        });
      }
    });
  }


  verUsuario(u: Usuario) { this.router.navigate(['/usuarios', u.id]); }
  editarUsuario(u: Usuario) { this.router.navigate(['/usuarios/editar', u.id]); }
  eliminarUsuario(id: string) {
    if (confirm('¿Eliminar usuario?')) {
      this.usuariosService.eliminarUsuario(id).subscribe(() => this.usuarios = this.usuarios.filter(u => u.id !== id));
    }
  }


  verServicio(s: Servicio) { this.router.navigate(['/servicios', s.id]); }
  editarServicio(s: Servicio) { this.router.navigate(['/servicios/editar', s.id]); }
  eliminarServicio(id: number): void {
    Swal.fire({
      title: '¿Estás seguro?',
      text: 'Esta acción eliminará el servicio permanentemente.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar',
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6'
    }).then(result => {
      if (result.isConfirmed) {
        this.serviciosService.eliminarServicio(id).subscribe({
          next: () => {
            this.servicios = this.servicios.filter(s => s.id !== id);
            Swal.fire({
              title: 'Eliminado',
              text: 'El servicio se ha eliminado correctamente.',
              icon: 'success',
              timer: 2000,
              showConfirmButton: false
            });
          },
          error: () => {
            Swal.fire({
              title: 'Error',
              text: 'No se pudo eliminar el servicio.',
              icon: 'error',
              confirmButtonText: 'Aceptar'
            });
          }
        });
      }
    });
  }


  // paginacion
  get paginatedParadas(): Parada[] {
    const start = (this.currentPageParadas - 1) * this.itemsPerPage;
    return this.paradas.slice(start, start + this.itemsPerPage);
  }

  get paginatedValoraciones(): Valoracion[] {
    const start = (this.currentPageValoraciones - 1) * this.itemsPerPage;
    return this.valoraciones.slice(start, start + this.itemsPerPage);
  }

  get paginatedContactos(): Contacto[] {
    const start = (this.currentPageContactos - 1) * this.itemsPerPage;
    return this.contactos.slice(start, start + this.itemsPerPage);
  }

  get paginatedCocheros(): Cochero[] {
    const start = (this.currentPageCocheros - 1) * this.itemsPerPage;
    return this.cocheros.slice(start, start + this.itemsPerPage);
  }

  get paginatedUsuarios(): Usuario[] {
    const start = (this.currentPageUsuarios - 1) * this.itemsPerPage;
    return this.usuarios.slice(start, start + this.itemsPerPage);
  }

  get paginatedServicios(): Servicio[] {
    const start = (this.currentPageServicios - 1) * this.itemsPerPage;
    return this.servicios.slice(start, start + this.itemsPerPage);
  }

  getTotalPages(totalItems: number): number {
    return Math.ceil(totalItems / this.itemsPerPage);
  }


  //  paginación
  nextPageParadas(): void {
    const totalPages = Math.ceil(this.paradas.length / this.itemsPerPage);
    if (this.currentPageParadas < totalPages) {
      this.currentPageParadas++;
    }
  }

  prevPageParadas(): void {
    if (this.currentPageParadas > 1) {
      this.currentPageParadas--;
    }
  }


  nextPageValoraciones(): void {
    const totalPages = Math.ceil(this.valoraciones.length / this.itemsPerPage);
    if (this.currentPageValoraciones < totalPages) {
      this.currentPageValoraciones++;
    }
  }

  prevPageValoraciones(): void {
    if (this.currentPageValoraciones > 1) {
      this.currentPageValoraciones--;
    }
  }



  nextPageContactos(): void {
    const totalPages = Math.ceil(this.contactos.length / this.itemsPerPage);
    if (this.currentPageContactos < totalPages) {
      this.currentPageContactos++;
    }
  }

  prevPageContactos(): void {
    if (this.currentPageContactos > 1) {
      this.currentPageContactos--;
    }
  }



  nextPageCocheros(): void {
    const totalPages = Math.ceil(this.cocheros.length / this.itemsPerPage);
    if (this.currentPageCocheros < totalPages) {
      this.currentPageCocheros++;
    }
  }

  prevPageCocheros(): void {
    if (this.currentPageCocheros > 1) {
      this.currentPageCocheros--;
    }
  }


  nextPageUsuarios(): void {
    const totalPages = Math.ceil(this.usuarios.length / this.itemsPerPage);
    if (this.currentPageUsuarios < totalPages) {
      this.currentPageUsuarios++;
    }
  }

  prevPageUsuarios(): void {
    if (this.currentPageUsuarios > 1) {
      this.currentPageUsuarios--;
    }
  }

  nextPageServicios(): void {
    const totalPages = Math.ceil(this.servicios.length / this.itemsPerPage);
    if (this.currentPageServicios < totalPages) {
      this.currentPageServicios++;
    }
  }

  prevPageServicios(): void {
    if (this.currentPageServicios > 1) {
      this.currentPageServicios--;
    }
  }







}

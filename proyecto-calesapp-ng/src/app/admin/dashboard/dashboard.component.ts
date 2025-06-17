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
import { AuthService } from '../../services/auth/auth.service';
import { UsuarioEdit } from '../../models/usuarioEdit.model';
import { forkJoin } from 'rxjs';




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
    private serviciosService: ServiciosService,
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    this.paradasService.getParadas().subscribe(data => this.paradas = data);
    this.cocherosService.getCocheros().subscribe(data => this.cocheros = data);
    this.valoracionesService.getValoraciones().subscribe(data => this.valoraciones = data);
    this.contactosService.getContactos().subscribe(data => this.contactos = data);
    this.usuariosService.getUsuarios().subscribe(data => this.usuarios = data);
    this.serviciosService.getServicios().subscribe(data => this.servicios = data);
  }


  // CRUD paradas
  crearParadaModal(): void {
    Swal.fire({
      title: 'Crear nueva parada',
      html: `
      <input id="nombre" class="swal2-input" placeholder="Nombre (mín. 3 caracteres)">
      <input id="lat" type="number" step="any" class="swal2-input" placeholder="Latitud (ej: 37.3828)">
      <input id="lng" type="number" step="any" class="swal2-input" placeholder="Longitud (ej: -5.9731)">
      <textarea id="descripcion" class="swal2-textarea" placeholder="Descripción (mín. 10 caracteres)"></textarea>
    `,
      focusConfirm: false,
      showCancelButton: true,
      confirmButtonText: 'Crear',
      cancelButtonText: 'Cancelar',
      preConfirm: () => {
        const nombre = (document.getElementById('nombre') as HTMLInputElement).value.trim();
        const latStr = (document.getElementById('lat') as HTMLInputElement).value.trim();
        const lngStr = (document.getElementById('lng') as HTMLInputElement).value.trim();
        const descripcion = (document.getElementById('descripcion') as HTMLTextAreaElement).value.trim();

        if (nombre.length < 3) {
          Swal.showValidationMessage('El nombre debe tener al menos 3 caracteres.');
          return false;
        }

        if (!latStr) {
          Swal.showValidationMessage('Debe introducir una latitud.');
          return false;
        }
        const lat = parseFloat(latStr);
        if (isNaN(lat)) {
          Swal.showValidationMessage('La latitud debe ser un número válido.');
          return false;
        }
        if (lat < -90 || lat > 90) {
          Swal.showValidationMessage('La latitud debe estar entre -90 y 90.');
          return false;
        }

        if (!lngStr) {
          Swal.showValidationMessage('Debe introducir una longitud.');
          return false;
        }
        const lng = parseFloat(lngStr);
        if (isNaN(lng)) {
          Swal.showValidationMessage('La longitud debe ser un número válido.');
          return false;
        }
        if (lng < -180 || lng > 180) {
          Swal.showValidationMessage('La longitud debe estar entre -180 y 180.');
          return false;
        }

        if (descripcion.length < 10) {
          Swal.showValidationMessage('La descripción debe tener al menos 10 caracteres.');
          return false;
        }

        return { nombre, lat, lng, descripcion };
      }
    }).then((result) => {
      if (result.isConfirmed && result.value) {
        const parada: Parada = result.value as unknown as Parada;

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
  editarParada(parada: Parada): void {
    Swal.fire({
      title: 'Editar parada',
      html: `
      <input id="nombre" class="swal2-input" placeholder="Nombre" value="${parada.nombre}">
      <input id="lat" type="number" step="any" class="swal2-input" placeholder="Latitud (ej: 37.3828)" value="${parada.lat}">
      <input id="lng" type="number" step="any" class="swal2-input" placeholder="Longitud (ej: -5.9731)" value="${parada.lng}">
      <textarea id="descripcion" class="swal2-textarea" placeholder="Descripción">${parada.descripcion}</textarea>
    `,
      focusConfirm: false,
      showCancelButton: true,
      confirmButtonText: 'Actualizar',
      cancelButtonText: 'Cancelar',
      preConfirm: () => {
        const nombre = (document.getElementById('nombre') as HTMLInputElement).value.trim();
        const latStr = (document.getElementById('lat') as HTMLInputElement).value.trim();
        const lngStr = (document.getElementById('lng') as HTMLInputElement).value.trim();
        const descripcion = (document.getElementById('descripcion') as HTMLTextAreaElement).value.trim();

        if (nombre.length < 3) {
          Swal.showValidationMessage('El nombre debe tener al menos 3 caracteres.');
          return false;
        }

        const lat = parseFloat(latStr);
        if (!latStr || isNaN(lat) || lat < -90 || lat > 90) {
          Swal.showValidationMessage('La latitud debe ser un número entre -90 y 90.');
          return false;
        }

        const lng = parseFloat(lngStr);
        if (!lngStr || isNaN(lng) || lng < -180 || lng > 180) {
          Swal.showValidationMessage('La longitud debe ser un número entre -180 y 180.');
          return false;
        }

        if (descripcion.length < 10) {
          Swal.showValidationMessage('La descripción debe tener al menos 10 caracteres.');
          return false;
        }

        return {
          id: parada.id,
          nombre,
          lat,
          lng,
          descripcion
        } as Parada;
      }
    }).then(result => {
      if (result.isConfirmed && result.value) {
        const paradaEditada = result.value as Parada;
        this.paradasService.actualizarParada(paradaEditada).subscribe({
          next: (actualizada) => {
            const index = this.paradas.findIndex(p => p.id === actualizada.id);
            if (index !== -1) this.paradas[index] = actualizada;

            Swal.fire({
              icon: 'success',
              title: 'Parada actualizada',
              text: 'Los datos se han actualizado correctamente.',
              timer: 2000,
              showConfirmButton: false
            });
          },
          error: (err) => {
            console.error('Error actualizando parada:', err);
            Swal.fire({
              icon: 'error',
              title: 'Error al actualizar',
              text: err.error?.message || 'No se pudo actualizar la parada.',
              confirmButtonText: 'Aceptar'
            });
          }
        });
      }
    });
  }

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

  // CRUD valoraciones
  crearValoracionModal(): void {
    const opcionesServicio = this.servicios.map(s => `<option value="${s.id}">${s.tipoServicio}</option>`).join('');

    Swal.fire({
      title: 'Nueva Valoración',
      html: `
      <input id="puntuacion" type="number" min="1" max="10" class="swal2-input" placeholder="Puntuación (1-10)">
      <textarea id="comentario" class="swal2-textarea" placeholder="Comentario"></textarea>
      <input id="fecha" type="date" class="swal2-input">
      <select id="servicioId" class="swal2-select">
        <option value="">Seleccione un servicio</option>
        ${opcionesServicio}
      </select>
    `,
      showCancelButton: true,
      confirmButtonText: 'Crear',
      cancelButtonText: 'Cancelar',
      preConfirm: () => {
        const puntuacion = parseInt((<HTMLInputElement>document.getElementById('puntuacion')).value);
        const comentario = (<HTMLInputElement>document.getElementById('comentario')).value.trim();
        const fecha = (<HTMLInputElement>document.getElementById('fecha')).value;
        const servicioId = parseInt((<HTMLInputElement>document.getElementById('servicioId')).value);
        const usuarioId = this.authService.getUserId();

        if (!puntuacion || puntuacion < 1 || puntuacion > 10) {
          Swal.showValidationMessage('La puntuación debe estar entre 1 y 10');
          return false;
        }

        if (!comentario || comentario.length < 5) {
          Swal.showValidationMessage('El comentario debe tener al menos 5 caracteres');
          return false;
        }

        if (!fecha || !usuarioId || !servicioId) {
          Swal.showValidationMessage('Todos los campos son obligatorios');
          return false;
        }

        const hoy = new Date();
        hoy.setHours(0, 0, 0, 0);
        const fechaSeleccionada = new Date(fecha);
        fechaSeleccionada.setHours(0, 0, 0, 0);

        if (fechaSeleccionada > hoy) {
          Swal.showValidationMessage('La fecha debe ser pasada o como máximo hoy');
          return false;
        }

        return {
          puntuacion,
          comentario,
          fecha,
          usuarioId,
          servicioId
        };
      }

    }).then(result => {
      if (result.isConfirmed && result.value) {
        const nuevaValoracion = result.value;

        this.valoracionesService.crearValoracion(nuevaValoracion).subscribe({
          next: (val) => {
            this.valoraciones.push(val);
            Swal.fire({
              icon: 'success',
              title: '¡Valoración creada!',
              text: 'Se ha registrado correctamente.',
              timer: 2000,
              showConfirmButton: false
            });
          },
          error: (err) => {
            console.error('Error al crear valoración:', err);
            Swal.fire({
              icon: 'error',
              title: 'Error',
              text: 'No se pudo crear la valoración.',
              confirmButtonText: 'Aceptar'
            });
          }
        });
      }
    });
  }
  verValoracion(v: Valoracion) { this.router.navigate(['/valoraciones', v.id]); }

  editarValoracion(valoracion: Valoracion): void {
    if (!valoracion.id) {
      console.error('ID de valoración no definido');
      return;
    }

    Swal.fire({
      title: 'Editar Valoración',
      html: `
      <input id="puntuacion" type="number" min="1" max="10" value="${valoracion.puntuacion}" class="swal2-input" placeholder="Puntuación (1-10)">
      <textarea id="comentario" class="swal2-textarea" placeholder="Comentario">${valoracion.comentario}</textarea>
      <input id="fecha" type="date" value="${valoracion.fecha}" class="swal2-input">
    `,
      showCancelButton: true,
      confirmButtonText: 'Actualizar',
      cancelButtonText: 'Cancelar',
      preConfirm: () => {
        const puntuacion = parseInt((document.getElementById('puntuacion') as HTMLInputElement).value);
        const comentario = (document.getElementById('comentario') as HTMLInputElement).value.trim();
        const fecha = (document.getElementById('fecha') as HTMLInputElement).value;

        if (!puntuacion || puntuacion < 1 || puntuacion > 10) {
          Swal.showValidationMessage('La puntuación debe estar entre 1 y 10');
          return;
        }
        if (!comentario || comentario.length < 5) {
          Swal.showValidationMessage('El comentario debe tener al menos 5 caracteres');
          return;
        }
        if (!fecha) {
          Swal.showValidationMessage('La fecha es obligatoria');
          return;
        }

        return {
          id: valoracion.id,
          puntuacion,
          comentario,
          fecha,
          usuarioId: valoracion.usuarioId,
          servicioId: valoracion.servicioId
        } as Valoracion;
      }
    }).then(result => {
      if (result.isConfirmed && result.value) {
        const updatedValoracion = result.value;

        this.valoracionesService.editarValoracion(updatedValoracion).subscribe({
          next: (updated) => {
            const index = this.valoraciones.findIndex(v => v.id === updated.id);
            if (index !== -1) {
              this.valoraciones[index] = updated;
            }

            Swal.fire({
              icon: 'success',
              title: '¡Valoración actualizada!',
              text: 'La valoración se ha actualizado correctamente.',
              timer: 2000,
              showConfirmButton: false
            });
          },
          error: (err) => {
            console.error('Error al actualizar valoración:', err);
            Swal.fire({
              icon: 'error',
              title: 'Error',
              text: 'No se pudo actualizar la valoración.',
              confirmButtonText: 'Aceptar'
            });
          }
        });
      }
    });
  }
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

  // CRUD contactos
  crearContacto(): void {
    if (this.servicios.length === 0) {
      Swal.fire({
        icon: 'warning',
        title: 'Sin servicios disponibles',
        text: 'No puedes crear un contacto sin servicios.',
        confirmButtonText: 'Aceptar'
      });
      return;
    }

    const today = new Date().toISOString().split('T')[0]; // formato YYYY-MM-DD
    const opcionesServicio = this.servicios.map(s => `<option value="${s.id}">${s.tipoServicio}</option>`).join('');

    const opcionesParadas = this.paradas
      .map(p => `<option value="${p.id}">${p.nombre}</option>`)
      .join('');

    const opcionesCocheros = this.cocheros
      .map(c => `<option value="${c.id}">${c.nombre}</option>`)
      .join('');


    Swal.fire({
      title: 'Nuevo Contacto',
      html: `
      <textarea id="mensaje" class="swal2-textarea" placeholder="Mensaje"></textarea>
      <input id="fecha" type="date" class="swal2-input" min="${today}">
      <select id="servicioId" class="swal2-select">
        <option value="">Seleccione un servicio</option>
        ${opcionesServicio}
      </select>
      <select id="paradaId" class="swal2-select">
        <option value="">Seleccione una parada</option>
        ${opcionesParadas}
      </select>
      <select id="cocheroId" class="swal2-select">
        <option value="">Seleccione un cochero</option>
        ${opcionesCocheros}
      </select>
    `,
      showCancelButton: true,
      confirmButtonText: 'Crear',
      cancelButtonText: 'Cancelar',
      preConfirm: () => {
        const mensaje = (document.getElementById('mensaje') as HTMLTextAreaElement).value.trim();
        const fecha = (document.getElementById('fecha') as HTMLInputElement).value;
        const servicioId = parseInt((document.getElementById('servicioId') as HTMLSelectElement).value);
        const paradaId = parseInt((document.getElementById('paradaId') as HTMLSelectElement).value);
        const cocheroId = parseInt((document.getElementById('cocheroId') as HTMLSelectElement).value);
        const usuarioId = this.authService.getUserId();

        const hoy = new Date();
        const fechaSeleccionada = new Date(fecha);

        if (!mensaje || mensaje.length < 3) {
          Swal.showValidationMessage('El mensaje debe tener al menos 3 caracteres.');
          return;
        }

        if (!fecha) {
          Swal.showValidationMessage('La fecha es obligatoria.');
          return;
        }

        const hoySinHoras = new Date();
        hoySinHoras.setHours(0, 0, 0, 0);

        if (fechaSeleccionada < hoySinHoras) {
          Swal.showValidationMessage('La fecha no puede ser anterior a hoy.');
          return;
        }

        if (isNaN(servicioId) || servicioId <= 0) {
          Swal.showValidationMessage('Debes seleccionar un servicio válido.');
          return;
        }

        if (isNaN(paradaId) || paradaId <= 0) {
          Swal.showValidationMessage('Debes seleccionar una parada válida.');
          return;
        }

        if (isNaN(cocheroId) || cocheroId <= 0) {
          Swal.showValidationMessage('Debes seleccionar un cochero válido.');
          return;
        }

        return { mensaje, fecha, servicioId, paradaId, cocheroId, usuarioId };
      }
    }).then(result => {
      if (result.isConfirmed && result.value) {
        const nuevoContacto = result.value;

        this.contactosService.crearContacto(nuevoContacto).subscribe({
          next: (contactoCreado) => {
            this.contactos.push(contactoCreado);
            Swal.fire({
              icon: 'success',
              title: '¡Contacto creado!',
              text: 'El contacto se ha registrado correctamente.',
              timer: 2000,
              showConfirmButton: false
            });
          },
          error: (err) => {
            console.error('Error al crear contacto:', err);
            Swal.fire({
              icon: 'error',
              title: 'Error',
              text: 'No se pudo crear el contacto.',
              confirmButtonText: 'Aceptar'
            });
          }
        });
      }
    });
  }

  verContacto(contacto: Contacto): void {

    forkJoin({

      cochero: this.cocherosService.getCocheroById(contacto.cocheroId),

      servicio: this.serviciosService.getServicioById(contacto.servicioId),

      parada: this.paradasService.getParadaById(contacto.paradaId)

    }).subscribe({

      next: ({ cochero, servicio, parada }) => {

        Swal.fire({

          title: 'Detalles del contacto',

          html: `
                <p><strong>Cochero: </strong> ${cochero.nombre}</p>
                <p><strong>Servicio: </strong> ${servicio.tipoServicio}</p>
                <p><strong>Fecha: </strong> ${contacto.fecha}</p>
                <p><strong>Parada: </strong> ${parada.nombre}</p>
        `,
          confirmButtonText: 'Cerrar'
        });
      },
      error: () => {
        Swal.fire('Error', 'No se pudo cargar alguno de los datos relacionados', 'error');
      }
    });

  }


  editarContacto(contacto: Contacto): void {
    if (!contacto.id) {
      console.error('ID de contacto no definido');
      return;
    }

    const opcionesServicios = this.servicios.map(s =>
      `<option value="${s.id}" ${s.id === contacto.servicioId ? 'selected' : ''}>${s.tipoServicio}</option>`
    ).join('');

    const opcionesParadas = this.paradas.map(p =>
      `<option value="${p.id}" ${p.id === contacto.paradaId ? 'selected' : ''}>${p.nombre}</option>`
    ).join('');

    Swal.fire({
      title: 'Editar Contacto',
      html: `
      <textarea id="mensaje" class="swal2-textarea" placeholder="Mensaje">${contacto.mensaje}</textarea>
      <input id="fecha" type="date" value="${contacto.fecha}" class="swal2-input">
      <select id="servicioId" class="swal2-select">
        <option value="">-- Selecciona un servicio --</option>
        ${opcionesServicios}
      </select>
      <select id="paradaId" class="swal2-select">
        <option value="">-- Selecciona una parada --</option>
        ${opcionesParadas}
      </select>
    `,
      showCancelButton: true,
      confirmButtonText: 'Actualizar',
      cancelButtonText: 'Cancelar',
      focusConfirm: false,
      preConfirm: () => {
        const mensaje = (document.getElementById('mensaje') as HTMLTextAreaElement).value.trim();
        const fecha = (document.getElementById('fecha') as HTMLInputElement).value;
        const servicioId = parseInt((document.getElementById('servicioId') as HTMLSelectElement).value);
        const paradaId = parseInt((document.getElementById('paradaId') as HTMLSelectElement).value);
        const usuarioId = this.authService.getUserId();
        const cocheroId = contacto.cocheroId;


        const hoy = new Date();
        hoy.setHours(0, 0, 0, 0);
        const fechaSeleccionada = new Date(fecha);

        if (!mensaje || mensaje.length < 5) {
          Swal.showValidationMessage('El mensaje debe tener al menos 5 caracteres');
          return;
        }
        if (!fecha) {
          Swal.showValidationMessage('La fecha es obligatoria');
          return;
        }
        if (fechaSeleccionada < hoy) {
          Swal.showValidationMessage('La fecha no puede ser anterior a hoy');
          return;
        }
        if (!servicioId || isNaN(servicioId)) {
          Swal.showValidationMessage('Debes seleccionar un servicio válido');
          return;
        }
        if (!paradaId || isNaN(paradaId)) {
          Swal.showValidationMessage('Debes seleccionar una parada válida');
          return;
        }

        return {
          mensaje,
          fecha,
          servicioId,
          paradaId,
          usuarioId,
          cocheroId
        }
      }
    }).then(result => {
      if (result.isConfirmed && result.value) {
        const contactoActualizado = result.value as Contacto;

        this.contactosService.editarContacto(contacto.id!, contactoActualizado).subscribe({
          next: (actualizado) => {
            const index = this.contactos.findIndex(c => c.id === actualizado.id);
            if (index !== -1) {
              this.contactos[index] = actualizado;
            }

            Swal.fire({
              icon: 'success',
              title: 'Contacto actualizado',
              text: 'El contacto se actualizó correctamente.',
              timer: 2000,
              showConfirmButton: false
            });
          },
          error: (err) => {
            console.error('Error al actualizar contacto:', err);
            Swal.fire({
              icon: 'error',
              title: 'Error',
              text: 'No se pudo actualizar el contacto.',
              confirmButtonText: 'Aceptar'
            });
          }
        });
      }
    });
  }

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


  // CRUD cocheros
  crearCochero(): void {
    Swal.fire({
      title: 'Crear Cochero',
      html: `
      <input id="nombre" type="text" class="swal2-input" placeholder="Nombre">
      <input id="apellidos" type="text" class="swal2-input" placeholder="Apellidos">
      <input id="mediaValoracion" type="number" min="0" step="0.1" class="swal2-input" placeholder="mediaValoracion (años)">
    `,
      showCancelButton: true,
      confirmButtonText: 'Crear',
      cancelButtonText: 'Cancelar',
      preConfirm: () => {
        const nombre = (document.getElementById('nombre') as HTMLInputElement).value.trim();
        const apellidos = (document.getElementById('apellidos') as HTMLInputElement).value.trim();
        const mediaValoracion = parseFloat((document.getElementById('mediaValoracion') as HTMLInputElement).value);

        if (!nombre || !apellidos || isNaN(mediaValoracion) || mediaValoracion < 0) {
          Swal.showValidationMessage('Todos los campos son obligatorios y la mediaValoracion debe ser válida.');
          return;
        }

        return {
          nombre,
          apellidos,
          mediaValoracion
        };
      }
    }).then(result => {
      if (result.isConfirmed && result.value) {
        this.cocherosService.crearCochero(result.value).subscribe({
          next: (nuevo) => {
            this.cocheros.push(nuevo);
            Swal.fire({
              icon: 'success',
              title: 'Cochero creado',
              text: `${nuevo.nombre} ha sido añadido correctamente.`,
              timer: 2000,
              showConfirmButton: false
            });
          },
          error: (err) => {
            console.error('Error al crear cochero:', err);
            Swal.fire({
              icon: 'error',
              title: 'Error',
              text: 'No se pudo crear el cochero.',
              confirmButtonText: 'Aceptar'
            });
          }
        });
      }
    });
  }
  verCochero(c: Cochero) { this.router.navigate(['/cocheros', c.id]); }
  editarCochero(cochero: Cochero): void {
    if (!cochero.id) {
      console.error('ID del cochero no definido');
      return;
    }

    Swal.fire({
      title: 'Editar Cochero',
      html: `
      <input id="nombre" type="text" class="swal2-input" placeholder="Nombre" value="${cochero.nombre}">
      <input id="apellidos" type="text" class="swal2-input" placeholder="Apellidos" value="${cochero.apellidos}">
      <input id="mediaValoracion" type="number" min="0" step="0.1" class="swal2-input" placeholder="mediaValoracion (años)" value="${cochero.mediaValoracion}">
    `,
      showCancelButton: true,
      confirmButtonText: 'Actualizar',
      cancelButtonText: 'Cancelar',
      preConfirm: () => {
        const nombre = (document.getElementById('nombre') as HTMLInputElement).value.trim();
        const apellidos = (document.getElementById('apellidos') as HTMLInputElement).value.trim();
        const mediaValoracion = parseFloat((document.getElementById('mediaValoracion') as HTMLInputElement).value);

        if (!nombre || !apellidos || isNaN(mediaValoracion) || mediaValoracion < 0) {
          Swal.showValidationMessage('Todos los campos son obligatorios y la mediaValoracion debe ser válida.');
          return;
        }

        return {
          id: cochero.id,
          nombre,
          apellidos,
          mediaValoracion
        } as Cochero;
      }
    }).then(result => {
      if (result.isConfirmed && result.value) {
        const updatedCochero = result.value as Cochero;

        this.cocherosService.editarCochero(updatedCochero).subscribe({
          next: (actualizado) => {
            const index = this.cocheros.findIndex(c => c.id === actualizado.id);
            if (index !== -1) {
              this.cocheros[index] = actualizado;
            }

            Swal.fire({
              icon: 'success',
              title: '¡Cochero actualizado!',
              text: 'Los datos del cochero se han actualizado correctamente.',
              timer: 2000,
              showConfirmButton: false
            });
          },
          error: (err) => {
            console.error('Error al actualizar cochero:', err);
            Swal.fire({
              icon: 'error',
              title: 'Error',
              text: 'No se pudo actualizar el cochero.',
              confirmButtonText: 'Aceptar'
            });
          }
        });
      }
    });
  }

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

  // CRUD usuarios
  crearUsuario(): void {
    Swal.fire({
      title: 'Crear Usuario',
      html: `
      <input id="username" class="swal2-input" placeholder="Usuario">
      <input id="email" class="swal2-input" placeholder="Email">
      <input id="password" type="password" class="swal2-input" placeholder="Contraseña">
      <select id="rol" class="swal2-select">
        <option value="ROLE_USER" selected>USER</option>
        <option value="ROLE_ADMIN">ADMIN</option>
      </select>
    `,
      focusConfirm: false,
      showCancelButton: true,
      confirmButtonText: 'Registrar',
      preConfirm: () => {
        const username = (document.getElementById('username') as HTMLInputElement).value.trim();
        const email = (document.getElementById('email') as HTMLInputElement).value.trim();
        const password = (document.getElementById('password') as HTMLInputElement).value;
        const rol = (document.getElementById('rol') as HTMLSelectElement).value;

        if (!username || username.length < 3) {
          Swal.showValidationMessage('El nombre de usuario debe tener al menos 3 caracteres');
          return;
        }
        if (!email.includes('@')) {
          Swal.showValidationMessage('Introduce un email válido');
          return;
        }
        if (!password || password.length < 6) {
          Swal.showValidationMessage('La contraseña debe tener al menos 6 caracteres');
          return;
        }

        return { username, email, password, rol };
      }
    }).then(result => {
      if (result.isConfirmed && result.value) {
        const nuevoUsuario = result.value;

        this.authService.register(nuevoUsuario).subscribe({
          next: (res) => {
            Swal.fire({
              icon: 'info',
              title: 'Usuario registrado',
              html: `
              <p>Ahora debes verificar el usuario <b>${res.username}</b></p>
              <p><code>${res.verificationToken}</code></p>
            `,
              confirmButtonText: 'Verificar ahora'
            }).then(() => {
              this.verificarUsuario(res.username, nuevoUsuario.password, res.verificationToken);
            });
          },
          error: (err) => {
            Swal.fire({
              icon: 'error',
              title: 'Error al registrar',
              text: err.message || 'No se pudo registrar el usuario.'
            });
          }
        });
      }
    });
  }
  verificarUsuario(username: string, password: string, token: string): void {
    this.authService.verifyAccount(token, { username, password }).subscribe({
      next: (res) => {
        Swal.fire({
          icon: 'success',
          title: 'Cuenta verificada',
          text: `El usuario ${res.username} ya puede iniciar sesión.`,
          confirmButtonText: 'Aceptar'
        });
      },
      error: (err) => {
        Swal.fire({
          icon: 'error',
          title: 'Error de verificación',
          text: err.message || 'No se pudo verificar la cuenta.'
        });
      }
    });
  }
  verUsuario(u: Usuario) { this.router.navigate(['/usuarios', u.id]); }
  editarUsuario(usuario: Usuario): void {
    Swal.fire({
      title: 'Editar Usuario',
      html: `
      <input id="username" class="swal2-input" placeholder="Username" value="${usuario.username}">
      <input id="email" class="swal2-input" placeholder="Email" value="${usuario.email}">
      <input id="password" type="password" class="swal2-input" placeholder="Nueva contraseña (opcional)">
      <select id="rol" class="swal2-select">
        <option value="ROLE_USER" ${usuario.rol === 'ROLE_USER' ? 'selected' : ''}>USER</option>
        <option value="ROLE_ADMIN" ${usuario.rol === 'ROLE_ADMIN' ? 'selected' : ''}>ADMIN</option>
      </select>
    `,
      focusConfirm: false,
      showCancelButton: true,
      confirmButtonText: 'Actualizar',
      cancelButtonText: 'Cancelar',
      preConfirm: () => {
        const username = (document.getElementById('username') as HTMLInputElement).value.trim();
        const email = (document.getElementById('email') as HTMLInputElement).value.trim();
        const password = (document.getElementById('password') as HTMLInputElement).value;
        const rol = (document.getElementById('rol') as HTMLSelectElement).value as 'ROLE_USER' | 'ROLE_ADMIN';

        if (!username || username.length < 3) {
          Swal.showValidationMessage('El nombre de usuario debe tener al menos 3 caracteres');
          return;
        }

        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(email)) {
          Swal.showValidationMessage('El correo electrónico no es válido');
          return;
        }

        if (!rol) {
          Swal.showValidationMessage('Debes seleccionar un rol');
          return;
        }

        const updatedUsuario: UsuarioEdit = {
          username,
          email,
          rol
        };

        if (password) {
          updatedUsuario.password = password;
        }

        return updatedUsuario;
      }
    }).then(result => {
      if (result.isConfirmed && result.value) {
        const updatedData: UsuarioEdit = result.value;

        this.usuariosService.editarUsuario(usuario.id!, updatedData).subscribe({
          next: (updatedUsuario) => {
            const index = this.usuarios.findIndex(u => u.id === updatedUsuario.id);
            if (index !== -1) {
              this.usuarios[index] = updatedUsuario;
            }

            Swal.fire({
              icon: 'success',
              title: 'Usuario actualizado',
              text: 'Los datos del usuario han sido actualizados correctamente',
              timer: 2000,
              showConfirmButton: false
            });
          },
          error: (err) => {
            console.error('Error al actualizar el usuario:', err);
            Swal.fire({
              icon: 'error',
              title: 'Error',
              text: 'No se pudo actualizar el usuario. Verifica los datos e inténtalo de nuevo.',
              confirmButtonText: 'Aceptar'
            });
          }
        });
      }
    });
  }

  eliminarUsuario(id: string): void {
    Swal.fire({
      title: '¿Eliminar usuario?',
      text: 'Esta acción no se puede deshacer.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar',
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6'
    }).then(result => {
      if (result.isConfirmed) {
        this.usuariosService.eliminarUsuario(id).subscribe({
          next: () => {
            this.usuarios = this.usuarios.filter(u => u.id !== id);
            Swal.fire({
              icon: 'success',
              title: 'Eliminado',
              text: 'El usuario ha sido eliminado correctamente.',
              timer: 2000,
              showConfirmButton: false
            });
          },
          error: (err) => {
            console.error('Error al eliminar usuario:', err);
            Swal.fire({
              icon: 'error',
              title: 'Error',
              text: 'No se pudo eliminar el usuario.',
              confirmButtonText: 'Aceptar'
            });
          }
        });
      }
    });
  }

  toggleBloqueo(usuario: Usuario) {
    if (!usuario.enabled) {
      this.bloqueoUsuario(usuario);
    } else {
      this.desbloqueoUsuario(usuario);
    }
  }
  bloqueoUsuario(usuario: any): void {
    const nuevoEstado = !usuario.enabled;
    this.usuariosService.bloqueoUsuario(usuario.id, nuevoEstado).subscribe({
      next: () => {
        usuario.enabled = nuevoEstado;
      },
      error: (err) => {
        console.error('Error al bloquear usuario:', err);
      }
    });
  }
  desbloqueoUsuario(usuario: any): void {
    const nuevoEstado = !usuario.enabled;
    this.usuariosService.desbloqueoUsuario(usuario.id, nuevoEstado).subscribe({
      next: () => {
        usuario.enabled = nuevoEstado;
      },
      error: (err) => {
        console.error('Error al desbloquear usuario:', err);
      }
    });
  }

  // CRUD servicios PORHACER
  crearServicio(): void {
    Swal.fire({
      title: 'Crear Servicio',
      html: `
      <input id="tipoServicio" class="swal2-input" placeholder="Tipo de servicio (ej: OTRO)">
      <input id="tarifa" type="number" class="swal2-input" placeholder="Tarifa (ej: 150.75)">
      <input id="duracion" type="number" class="swal2-input" placeholder="Duración (en minutos)">
      <textarea id="descripcion" class="swal2-textarea" placeholder="Descripción"></textarea>
      <select id="disponibilidad" class="swal2-select">
        <option value="true">Disponible</option>
        <option value="false">No disponible</option>
      </select>
    `,
      showCancelButton: true,
      confirmButtonText: 'Crear',
      cancelButtonText: 'Cancelar',
      preConfirm: () => {
        const tipoServicio = (document.getElementById('tipoServicio') as HTMLInputElement).value.trim();
        const tarifa = parseFloat((document.getElementById('tarifa') as HTMLInputElement).value);
        const duracion = parseInt((document.getElementById('duracion') as HTMLInputElement).value);
        const descripcion = (document.getElementById('descripcion') as HTMLTextAreaElement).value.trim();
        const disponibilidad = (document.getElementById('disponibilidad') as HTMLSelectElement).value === 'true';

        if (!tipoServicio || !descripcion || isNaN(tarifa) || isNaN(duracion)) {
          Swal.showValidationMessage('Todos los campos son obligatorios y deben tener valores válidos.');
          return;
        }

        return { tipoServicio, tarifa, duracion, descripcion, disponibilidad } as Servicio;
      }
    }).then(result => {
      if (result.isConfirmed && result.value) {
        const nuevoServicio = result.value as Servicio;

        this.serviciosService.crearServicio(nuevoServicio).subscribe({
          next: (servicioCreado) => {
            this.servicios.push(servicioCreado);
            Swal.fire({
              icon: 'success',
              title: 'Servicio creado',
              text: 'El servicio se ha creado correctamente.',
              timer: 2000,
              showConfirmButton: false
            });
          },
          error: (err) => {
            console.error('Error al crear servicio:', err);
            Swal.fire({
              icon: 'error',
              title: 'Error',
              text: 'No se pudo crear el servicio.',
              confirmButtonText: 'Aceptar'
            });
          }
        });
      }
    });
  }
  verServicio(s: Servicio) { this.router.navigate(['/servicios', s.id]); }
  editarServicio(servicio: Servicio): void {
    Swal.fire({
      title: 'Editar Servicio',
      html: `
      <select id="tipoServicio" class="swal2-select">
        <option value="BODAS" ${servicio.tipoServicio === 'BODAS' ? 'selected' : ''}>Bodas</option>
        <option value="TOURS" ${servicio.tipoServicio === 'TOURS' ? 'selected' : ''}>Tours</option>
        <option value="EVENTOS" ${servicio.tipoServicio === 'EVENTOS' ? 'selected' : ''}>Eventos</option>
        <option value="OTRO" ${servicio.tipoServicio === 'OTRO' ? 'selected' : ''}>Otro</option>
      </select>
      <input id="tarifa" type="number" class="swal2-input" placeholder="Tarifa (€)" value="${servicio.tarifa}">
      <input id="duracion" type="number" class="swal2-input" placeholder="Duración (min)" value="${servicio.duracion}">
      <textarea id="descripcion" class="swal2-textarea" placeholder="Descripción">${servicio.descripcion}</textarea>
      <select id="disponibilidad" class="swal2-select">
        <option value="true" ${servicio.disponibilidad ? 'selected' : ''}>Disponible</option>
        <option value="false" ${!servicio.disponibilidad ? 'selected' : ''}>No disponible</option>
      </select>
    `,
      showCancelButton: true,
      confirmButtonText: 'Actualizar',
      cancelButtonText: 'Cancelar',
      preConfirm: () => {
        const tipoServicio = (document.getElementById('tipoServicio') as HTMLSelectElement).value;
        const tarifa = parseFloat((document.getElementById('tarifa') as HTMLInputElement).value);
        const duracion = parseInt((document.getElementById('duracion') as HTMLInputElement).value);
        const descripcion = (document.getElementById('descripcion') as HTMLTextAreaElement).value.trim();
        const disponibilidad = (document.getElementById('disponibilidad') as HTMLSelectElement).value === 'true';

        if (!descripcion || isNaN(tarifa) || isNaN(duracion)) {
          Swal.showValidationMessage('Todos los campos son obligatorios y deben ser válidos.');
          return;
        }

        return { id: servicio.id, tipoServicio, tarifa, duracion, descripcion, disponibilidad } as Servicio;
      }
    }).then(result => {
      if (result.isConfirmed && result.value) {
        const servicioActualizado = result.value as Servicio;

        this.serviciosService.editarServicio(servicioActualizado).subscribe({
          next: (actualizado) => {
            const index = this.servicios.findIndex(s => s.id === actualizado.id);
            if (index !== -1) {
              this.servicios[index] = actualizado;
            }
            Swal.fire({
              icon: 'success',
              title: 'Servicio actualizado',
              text: 'El servicio ha sido actualizado correctamente.',
              timer: 2000,
              showConfirmButton: false
            });
          },
          error: (err) => {
            console.error('Error al actualizar servicio:', err);
            Swal.fire({
              icon: 'error',
              title: 'Error',
              text: 'No se pudo actualizar el servicio.',
              confirmButtonText: 'Aceptar'
            });
          }
        });
      }
    });
  }


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

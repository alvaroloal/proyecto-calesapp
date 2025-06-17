import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink, RouterModule } from '@angular/router';
import { CocherosService } from '../../../services/cocheros/cocheros.service';
import { ParadasService } from '../../../services/paradas/paradas.service';
import { ServiciosService } from '../../../services/servicios/servicios.service';
import { forkJoin } from 'rxjs';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../../services/auth/auth.service';
import Swal from 'sweetalert2';



@Component({
  selector: 'app-detalle-cochero',
  standalone: true,
  imports: [CommonModule, RouterModule, RouterLink, FormsModule],
  templateUrl: './detalle-cochero.component.html',
  styleUrls: ['./detalle-cochero.component.css'],
})

export class DetalleCocheroComponent implements OnInit {
  cocheroId!: number;
  cochero: any;
  loading = true;
  paradas: any;
  servicios: any;
  hoy: string = new Date().toISOString().split('T')[0];

  form = {
    parada: 0,
    tipoServicio: 0,
    fecha: '',
    comentario: '',
    userId: ''
  }
  constructor(
    private route: ActivatedRoute,
    private cocherosService: CocherosService,
    private paradasService: ParadasService,
    private serviciosService: ServiciosService,
    private authService: AuthService,
  ) { }


  contactar() {
    const { parada, tipoServicio, fecha, comentario } = this.form;

    if (!parada || !tipoServicio || !fecha || !comentario.trim()) {
      Swal.fire({
        icon: 'warning',
        title: 'Campos incompletos',
        text: 'Todos los campos son obligatorios.',
        confirmButtonText: 'Aceptar'
      });
      return;
    }

    this.cocherosService.contactarCochero(this.cocheroId, parada, tipoServicio, fecha, comentario)
      .subscribe({
        next: () => {
          Swal.fire({
            icon: 'success',
            title: 'Solicitud enviada',
            text: 'Tu solicitud se ha enviado correctamente al cochero.',
            timer: 2000,
            showConfirmButton: false
          });
          this.form = {
            parada: 0,
            tipoServicio: 0,
            fecha: '',
            comentario: '',
            userId: '',
          };
        },
        error: err => {
          console.error('Error al enviar solicitud', err);
          Swal.fire({
            icon: 'error',
            title: 'Error al reservar',
            text: err.error?.message || 'No se pudo reservar con el cochero. Inténtalo más tarde.',
            confirmButtonText: 'Aceptar'
          });
        }
      });
  }


  ngOnInit() {
    this.cocheroId = Number(this.route.snapshot.paramMap.get('id'));
    if (isNaN(this.cocheroId)) {
      console.error('ID de cochero inválido');
      this.loading = false;
      return;
    }
    forkJoin({
      paradas: this.paradasService.getParadas(),
      servicios: this.serviciosService.getServicios(),
      cochero: this.cocherosService.getCocheroById(this.cocheroId)
    }).subscribe({
      next: ({ paradas, servicios, cochero }) => {
        this.paradas = paradas;
        this.servicios = servicios.filter(s => s.disponibilidad);
        this.cochero = cochero;
        this.loading = false;
      },
      error: err => {
        console.error('Error al cargar datos:', err);
        this.loading = false;
      }
    });
  }


}

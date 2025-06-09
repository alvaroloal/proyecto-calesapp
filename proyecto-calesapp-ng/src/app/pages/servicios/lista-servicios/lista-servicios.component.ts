import { Component } from '@angular/core';
import { Servicio } from '../../../models/servicio.model';
import { ServiciosService } from '../../../services/servicios/servicios.service';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-lista-servicios',
  imports: [CommonModule, RouterLink],
  templateUrl: './lista-servicios.component.html',
  styleUrl: './lista-servicios.component.css'
})
export class ListaServiciosComponent {

  servicios: Servicio[] = [];
  loading = true;

  constructor(private serviciosService: ServiciosService) { }

  ngOnInit() {
    this.serviciosService.getServicios().subscribe({
      next: (data) => {
        this.servicios = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error al obtener la lista de servicios', err);
        this.loading = false;
      }
    });
  }

}

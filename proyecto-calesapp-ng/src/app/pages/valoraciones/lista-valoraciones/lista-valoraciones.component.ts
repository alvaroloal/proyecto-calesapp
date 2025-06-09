import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { Valoracion } from '../../../models/valoracion.model';
import { ValoracionesService } from '../../../services/valoraciones/valoraciones.service';

@Component({
  selector: 'app-lista-valoraciones',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './lista-valoraciones.component.html',
  styleUrls: ['./lista-valoraciones.component.css'],
})
export class ListaValoracionesComponent implements OnInit {
  valoraciones: Valoracion[] = [];
  loading = true;

  constructor(private valoracionesService: ValoracionesService) { }

  ngOnInit() {
    this.valoracionesService.getValoraciones().subscribe({
      next: (data) => {
        this.valoraciones = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error al obtener la lista de valoraciones', err);
        this.loading = false;
      }
    });
  }
}


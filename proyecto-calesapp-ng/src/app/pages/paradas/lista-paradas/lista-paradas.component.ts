import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterModule } from '@angular/router';
import { ParadasService } from '../../../services/paradas/paradas.service';
import { Parada } from '../../../models/parada.model';

@Component({
  selector: 'app-lista-paradas',
  standalone: true,
  imports: [CommonModule, RouterModule, RouterLink],
  templateUrl: './lista-paradas.component.html',
  styleUrls: ['./lista-paradas.component.css'],
})
export class ListaParadasComponent implements OnInit {
  paradas: Parada[] = [];
  loading = true;

  constructor(private paradasService: ParadasService) { }

  ngOnInit() {
    this.paradasService.getParadas().subscribe({
      next: (data) => {
        this.paradas = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error al obtener la lista de paradas', err);
        this.loading = false;
      }
    });
  }


}

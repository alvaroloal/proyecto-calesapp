import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink, RouterModule } from '@angular/router';
import { ParadasService } from '../../../services/paradas/paradas.service';

@Component({
  selector: 'app-detalle-parada',
  standalone: true,
  imports: [CommonModule, RouterModule, RouterLink],
  templateUrl: './detalle-parada.component.html',
  styleUrls: ['./detalle-parada.component.css'],
})
export class DetalleParadaComponent implements OnInit {
  paradaId!: number;
  parada: any;
  loading = true;

  constructor(
    private route: ActivatedRoute,
    private paradasService: ParadasService
  ) { }

  ngOnInit() {
    this.paradaId = Number(this.route.snapshot.paramMap.get('id'));

    this.paradasService.getParadaById(this.paradaId).subscribe({
      next: (data) => {
        this.parada = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error al obtener parada', err);
        this.loading = false;
      }
    });
  }
}
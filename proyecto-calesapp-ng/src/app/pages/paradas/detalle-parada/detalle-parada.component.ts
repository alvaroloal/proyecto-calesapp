import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink, RouterModule } from '@angular/router';
import { ParadasService } from '../../../services/paradas/paradas.service';
import { Parada } from '../../../models/parada.model';

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

    if (!isNaN(this.paradaId)) {
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
    } else {
      console.error('ID de parada inválido');
      this.loading = false;
    }
  }

  getParadaImageUrl(parada: Parada): string {
    if (parada.nombre === "Plaza de Toros") {
      return "assets/toros.webp";
    }

    if (parada.nombre === "Plaza Virgen de los Reyes") {
      return "assets/virgendlreyes.jpg";
    }

    if (parada.nombre === "Torre del Oro") {
      return "assets/torre.jpg";
    }

    if (parada.nombre === "Parque Maria Luisa") {
      return "assets/parque2.jpg";
    }

    if (parada.nombre === "Plaza de EspaÃ±a") {
      return "assets/plazaespaña.jpg";
    }

    if (parada.nombre === "Plaza de America") {
      return "assets/plazaamerica.jpg";
    }

    if (parada.nombre === "Giralda") {
      return "assets/lagartito.jpg";
    }

    if (parada.nombre === "Altozano") {
      return "assets/altozano.jpg";
    }

    if (parada.nombre === "Todogoma") {
      return "assets/todogoma.jpg";
    }

    if (parada.nombre === "Estatua Becquer") {
      return "assets/becquer.jpg";
    }

    if (parada.nombre === "Monte Gurugu") {
      return "assets/gurugu.jpg";
    }

    //añadir imagenes de paradas nuevas
    if (parada.nombre === "Archivo General de Indias") {
      return "assets/archivoIndias.jpg";
    }


    return "assets/feria.jpg";
  }
}

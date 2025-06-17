import { Component } from '@angular/core';
import { HeroComponent } from "../../components/hero/hero.component";
import { MapaParadasComponent } from '../../components/mapa-paradas/mapa-paradas.component';
import { ListaServiciosComponent } from "../../pages/servicios/lista-servicios/lista-servicios.component";
import { Parada } from '../../models/parada.model';
import { ParadasService } from '../../services/paradas/paradas.service';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-layout',
  imports: [HeroComponent, MapaParadasComponent, ListaServiciosComponent, CommonModule, RouterLink],
  templateUrl: './layout.component.html',
  styleUrl: './layout.component.css'
})
export class LayoutComponent {

  paradas: Parada[] = [];
  loading = true;

  constructor(private paradasService: ParadasService) { }

  ngOnInit(): void {
    this.paradasService.getParadas().subscribe({
      next: data => {
        this.paradas = data;
        this.loading = false;
      },
      error: err => {
        console.error('Error al cargar paradas', err);
        this.loading = false;
      }
    });
  }

  getParadaImageUrl(parada: Parada): string {
    if (parada.nombre === "Plaza de Toros") { return "assets/toros.webp"; }

    if (parada.nombre === "Plaza Virgen de los Reyes") {
      return "assets/virgendlreyes.jpg";
    }

    if (parada.nombre === "Torre del Oro") {
      return "assets/torre.jpg";
    }

    if (parada.nombre === "Parque Maria Luisa") {
      return "assets/parque2.jpg";
    }

    if (parada.nombre === "Plaza de España") {
      return "assets/plazaespaña.jpg";
    }

    if (parada.nombre === "Plaza de América") {
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


    return "assets/parque2.jpg";
  }

}

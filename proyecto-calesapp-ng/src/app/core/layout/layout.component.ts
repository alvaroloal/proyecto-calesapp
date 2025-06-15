import { Component } from '@angular/core';
import { HeroComponent } from "../../components/hero/hero.component";
import { RegisterComponent } from '../../components/register/register.component';
import { MapaParadasComponent } from '../../components/mapa-paradas/mapa-paradas.component';
import { ListaServiciosComponent } from "../../pages/servicios/lista-servicios/lista-servicios.component";

@Component({
  selector: 'app-layout',
  imports: [HeroComponent, MapaParadasComponent, ListaServiciosComponent],
  templateUrl: './layout.component.html',
  styleUrl: './layout.component.css'
})
export class LayoutComponent {

}

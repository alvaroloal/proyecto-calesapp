import { Component } from '@angular/core';
import { HeroComponent } from "../../components/hero/hero.component";
import { RegisterComponent } from '../../components/register/register.component';
import { MapaParadasComponent } from '../../components/mapa-paradas/mapa-paradas.component';

@Component({
  selector: 'app-layout',
  imports: [HeroComponent, MapaParadasComponent],
  templateUrl: './layout.component.html',
  styleUrl: './layout.component.css'
})
export class LayoutComponent {

}

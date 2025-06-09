import { Component } from '@angular/core';
import { HeroComponent } from "../../components/hero/hero.component";
import { RegisterComponent } from '../../components/register/register.component';

@Component({
  selector: 'app-layout',
  imports: [HeroComponent],
  templateUrl: './layout.component.html',
  styleUrl: './layout.component.css'
})
export class LayoutComponent {

}

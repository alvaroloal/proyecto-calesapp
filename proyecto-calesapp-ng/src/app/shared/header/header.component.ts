import { CommonModule } from '@angular/common';
import { Component, signal } from '@angular/core';
import { RouterModule } from '@angular/router';
import { transition, trigger, style, animate } from '@angular/animations';
import { AuthService } from '../../services/auth/auth.service';

@Component({
  selector: 'app-header',
  imports: [CommonModule, RouterModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css',
  animations: [
    trigger('expandContractMenu', [
      transition(':enter', [
        style({ opacity: 0, height: 0 }),
        animate('500ms ease-out', style({ opacity: 1, height: '*' }))
      ]),
      transition(':leave', [
        animate('500ms ease-out', style({ opacity: 0, height: 0 }))
      ])
    ])
  ],

})
export class HeaderComponent {
  collapsed = signal(false);

  constructor(public authService: AuthService) { }

  // llamar logout del servicio
  callLogout(): void {
    this.authService.logout();
    this.collapsed.set(false);//cerrar el menu
  }
}

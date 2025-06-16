import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Contacto } from '../../../models/contacto.model';
import { ContactosService } from '../../../services/contactos/contactos.service';

@Component({
  selector: 'app-mis-contactos',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './mis-contactos.component.html',
})
export class MisContactosComponent implements OnInit {
  contactos: Contacto[] = [];

  constructor(private contactosService: ContactosService) { }

  ngOnInit(): void {
    this.contactosService.getMisContactos().subscribe({
      next: (data) => this.contactos = data,
      error: (err) => console.error('Error al cargar tus contactos', err)
    });
  }
}


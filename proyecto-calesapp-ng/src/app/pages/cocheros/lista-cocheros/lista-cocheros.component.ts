import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterModule } from '@angular/router';
import { Cochero } from '../../../models/cochero.model';
import { CocherosService } from '../../../services/cocheros/cocheros.service';

@Component({
  selector: 'app-lista-cocheros',
  standalone: true,
  imports: [CommonModule, RouterModule, RouterLink],
  templateUrl: './lista-cocheros.component.html',
  styleUrls: ['./lista-cocheros.component.css'],
})
export class ListaCocherosComponent implements OnInit {
  cocheros: Cochero[] = [];
  loading = true;

  constructor(private cocherosService: CocherosService) { }

  ngOnInit() {
    this.cocherosService.getCocheros().subscribe({
      next: (data) => {
        this.cocheros = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error al obtener la lista de cocheros', err);
        this.loading = false;
      }
    });
  }
}

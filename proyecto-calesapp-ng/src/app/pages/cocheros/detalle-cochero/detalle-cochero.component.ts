import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink, RouterModule } from '@angular/router';
import { CocherosService } from '../../../services/cocheros/cocheros.service';

@Component({
  selector: 'app-detalle-cochero',
  standalone: true,
  imports: [CommonModule, RouterModule, RouterLink],
  templateUrl: './detalle-cochero.component.html',
  styleUrls: ['./detalle-cochero.component.css'],
})
export class DetalleCocheroComponent implements OnInit {
  cocheroId!: number;
  cochero: any;
  loading = true;

  constructor(
    private route: ActivatedRoute,
    private cocherosService: CocherosService
  ) { }

  ngOnInit() {
    this.cocheroId = Number(this.route.snapshot.paramMap.get('id'));

    if (!isNaN(this.cocheroId)) {
      this.cocherosService.getCocheroById(this.cocheroId).subscribe({
        next: (data) => {
          this.cochero = data;
          this.loading = false;
        },
        error: (err) => {
          console.error('Error al obtener cochero', err);
          this.loading = false;
        }
      });
    } else {
      console.error('ID de cochero inválido');
      this.loading = false;
    }
  }
}

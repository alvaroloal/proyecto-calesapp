import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GoogleMapsModule } from '@angular/google-maps';
import { ParadasService } from '../../services/paradas/paradas.service';
import { Parada } from '../../models/parada.model';

@Component({
  selector: 'app-mapa-paradas',
  standalone: true,
  imports: [CommonModule, GoogleMapsModule],
  templateUrl: './mapa-paradas.component.html',
})
export class MapaParadasComponent implements OnInit {
  paradas: Parada[] = [];

  center: google.maps.LatLngLiteral = { lat: 37.3861, lng: -5.9922 }; // Centro Sevilla
  zoom = 14;
  markerOptions: google.maps.MarkerOptions = { animation: google.maps.Animation.DROP };

  constructor(private paradasService: ParadasService) { }

  ngOnInit(): void {
    this.paradasService.getParadas().subscribe({
      next: (data) => {
        this.paradas = data.filter(p => p.lat && p.lng); // Solo las que tengan coordenadas
      },
      error: (err) => console.error('Error cargando paradas para el mapa', err)
    });
  }
}

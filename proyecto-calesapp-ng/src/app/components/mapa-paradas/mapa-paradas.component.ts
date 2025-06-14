import { Component, OnInit, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GoogleMapsModule, MapInfoWindow, MapMarker } from '@angular/google-maps';
import { ParadasService } from '../../services/paradas/paradas.service';
import { Parada } from '../../models/parada.model';
import { Router } from '@angular/router';



@Component({
  selector: 'app-mapa-paradas',
  standalone: true,
  imports: [CommonModule, GoogleMapsModule],
  templateUrl: './mapa-paradas.component.html',
})
export class MapaParadasComponent implements OnInit {
  paradas: Parada[] = [];
  paradaSeleccionada: Parada | null = null;


  center: google.maps.LatLngLiteral = { lat: 37.3861, lng: -5.9922 };
  zoom = 14;
  markerOptions: google.maps.MarkerOptions = { animation: google.maps.Animation.DROP };

  @ViewChild(MapInfoWindow) infoWindow!: MapInfoWindow;

  constructor(private paradasService: ParadasService, private router: Router) { }

  ngOnInit(): void {
    this.paradasService.getParadas().subscribe({
      next: (data) => {
        this.paradas = data.filter(p => p.lat && p.lng);
      },
      error: (err) => console.error('Error cargando paradas para el mapa', err)
    });
  }

  abrirInfoWindow(parada: Parada, marker: MapMarker): void {
    this.paradaSeleccionada = parada;
    this.infoWindow.open(marker);
  }


  verDetallesParada(): void {
    if (this.paradaSeleccionada?.id) {
      this.infoWindow.close();
      this.router.navigate(['/paradas', this.paradaSeleccionada.id]).then(() => {
        window.scrollTo({ top: 0, behavior: 'smooth' });
      });
    }
  }

  cerrarInfoWindow(): void {
    this.infoWindow.close();
    this.paradaSeleccionada = null;
  }


}

import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class GoogleMapsLoaderService {
  private loaded = false;
  private loadPromise!: Promise<void>;

  load(): Promise<void> {
    if (this.loaded) {
      return this.loadPromise;
    }

    this.loadPromise = new Promise((resolve, reject) => {
      const existingScript = document.querySelector(`script[src*="maps.googleapis.com"]`);
      if (existingScript) {
        resolve();
        return;
      }

      const script = document.createElement('script');
      script.src = `https://maps.googleapis.com/maps/api/js?key=${environment.googleMapsApiKey}&libraries=places`;
      script.async = true;
      script.defer = true;

      script.onload = () => {
        this.loaded = true;
        resolve();
      };

      script.onerror = (err) => reject(`Google Maps script failed to load: ${err}`);

      document.head.appendChild(script);
    });

    return this.loadPromise;
  }
}

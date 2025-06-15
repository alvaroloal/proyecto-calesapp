import { bootstrapApplication } from '@angular/platform-browser';
import { provideRouter } from '@angular/router';
import { AppComponent } from './app/app.component';
import { ListaParadasComponent } from './app/pages/paradas/lista-paradas/lista-paradas.component';
import { ListaCocherosComponent } from './app/pages/cocheros/lista-cocheros/lista-cocheros.component';
import { DetalleParadaComponent } from './app/pages/paradas/detalle-parada/detalle-parada.component';
import { ListaValoracionesComponent } from './app/pages/valoraciones/lista-valoraciones/lista-valoraciones.component';
import { DashboardComponent } from './app/admin/dashboard/dashboard.component';
import { ListaServiciosComponent } from './app/pages/servicios/lista-servicios/lista-servicios.component';
import { LayoutComponent } from './app/core/layout/layout.component';
import { provideHttpClient, withFetch, withInterceptorsFromDi } from '@angular/common/http';
import { LoginComponent } from './app/components/login/login.component';
import { RegisterComponent } from './app/components/register/register.component';
import { VerifyAccountComponent } from './app/components/verify-account/verify-account.component';
import { DetalleCocheroComponent } from './app/pages/cocheros/detalle-cochero/detalle-cochero.component';
import { LocationStrategy, HashLocationStrategy } from '@angular/common';
import { MapaParadasComponent } from './app/components/mapa-paradas/mapa-paradas.component';

bootstrapApplication(AppComponent, {
  providers: [
    provideHttpClient(withFetch(), withInterceptorsFromDi()),
    provideRouter([
      { path: '', component: LayoutComponent },
      { path: 'paradas', component: ListaParadasComponent },
      { path: 'cocheros', component: ListaCocherosComponent },
      { path: 'cocheros/:id', component: DetalleCocheroComponent },
      { path: 'paradas/:id', component: DetalleParadaComponent },
      { path: 'valoraciones', component: ListaValoracionesComponent },
      { path: 'contactos', component: ListaValoracionesComponent },
      { path: 'servicios', component: ListaServiciosComponent },
      { path: 'admin', component: DashboardComponent },
      { path: 'login', component: LoginComponent },
      { path: 'register', component: RegisterComponent },
      { path: 'verify-account', component: VerifyAccountComponent },
      { path: 'mapa', component: MapaParadasComponent },
      { path: '**', redirectTo: '', pathMatch: 'full' }
    ]),
    { provide: LocationStrategy, useClass: HashLocationStrategy }
  ]
});
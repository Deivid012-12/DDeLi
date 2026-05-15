import { ApplicationConfig } from '@angular/core';
import { provideAnimations } from '@angular/platform-browser/animations';
import { provideZoneChangeDetection } from '@angular/core';
import { provideRouter, Routes } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { providePrimeNG } from 'primeng/config';
import Aura from '@primeng/themes/aura';
import { InicioComponent } from './component/inicio/inicio.component';
import { PrincipalComponent } from './component/principal/principal.component';
import { RegistroComponent } from './component/registro/registro.component';
import { PagarComponent} from './component/pagar/pagar.component';
import { CarritoComponent } from './component/carrito/carrito.component';
import { MenuComponent} from './component/menu/menu.component';

const routes: Routes = [
  { path: '', redirectTo: 'principal', pathMatch: 'full' },
  { path: 'inicio', component: InicioComponent },
  { path: 'principal', component: PrincipalComponent },
  { path: 'registro', component: RegistroComponent },
  { path: 'pagar', component: PagarComponent },
  { path: 'carrito', component: CarritoComponent },
  { path: 'menu', component: MenuComponent },
  { path: '**', redirectTo: 'principal' }

];

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    provideHttpClient(),
    providePrimeNG({
      theme: { preset: Aura },
    }),
    provideAnimations()
  ]
};

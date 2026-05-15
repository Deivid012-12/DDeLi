import { bootstrapApplication } from '@angular/platform-browser';
import { provideAnimations } from '@angular/platform-browser/animations';
import { provideRouter, Routes } from '@angular/router';
import { providePrimeNG } from 'primeng/config';
import Aura from '@primeng/themes/aura';
import { AppComponent } from './app/app.component';
import { InicioComponent } from './app/component/inicio/inicio.component';
import { PrincipalComponent} from './app/component/principal/principal.component';
import { RegistroComponent} from './app/component/registro/registro.component';
import { provideHttpClient } from '@angular/common/http';
import { CarritoComponent}  from './app/component/carrito/carrito.component';
import { PagarComponent } from './app/component/pagar/pagar.component';

const routes: Routes = [
  { path: '', redirectTo: 'principal', pathMatch: 'full' },
  { path: 'inicio', component: InicioComponent },
  { path: 'principal', component: PrincipalComponent},
  { path: 'registro', component: RegistroComponent},
  { path: 'carrito', component: CarritoComponent},
  { path: 'pagar', component: PagarComponent}
];

bootstrapApplication(AppComponent, {
  providers: [
    provideRouter(routes),
    providePrimeNG({
      theme: {
        preset: Aura
      }
    }),
    provideHttpClient(),
    provideAnimations(),
  ]
});

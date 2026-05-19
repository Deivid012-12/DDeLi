import { bootstrapApplication } from '@angular/platform-browser';
import { provideAnimations } from '@angular/platform-browser/animations';
import { provideRouter, Routes, withRouterConfig } from '@angular/router';  // ← agregar withRouterConfig
import { providePrimeNG } from 'primeng/config';
import Aura from '@primeng/themes/aura';
import { AppComponent } from './app/app.component';
import { InicioComponent } from './app/component/inicio/inicio.component';
import { PrincipalComponent} from './app/component/principal/principal.component';
import { RegistroComponent} from './app/component/registro/registro.component';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { CarritoComponent}  from './app/component/carrito/carrito.component';
import { PagarComponent } from './app/component/pagar/pagar.component';
import { MenuComponent } from './app/component/menu/menu.component';
import { PerfilComponent } from './app/component/perfil/perfil.component';
import { authInterceptor } from './app/service/auth.interceptor';
import { PersonalizacionComponent } from './app/component/personalizacion/personalizacion.component';
import { PromocionesComponent } from './app/component/promociones/promociones.component';
import { SuscripcionComponent } from './app/component/suscripcion/suscripcion.component';
import { EventoComponent } from './app/component/evento/evento.component';

const routes: Routes = [
  { path: '', redirectTo: 'principal', pathMatch: 'full' },
  { path: 'inicio', component: InicioComponent },
  { path: 'principal', component: PrincipalComponent},
  { path: 'registro', component: RegistroComponent},
  { path: 'carrito', component: CarritoComponent},
  { path: 'pagar', component: PagarComponent},
  { path: 'menu', component: MenuComponent},
  { path: 'perfil', component: PerfilComponent},
  { path: 'personalizacion', component: PersonalizacionComponent },
  { path: 'promociones', component: PromocionesComponent },
  { path: 'evento', component: EventoComponent },
  {path: 'suscripcion', component: SuscripcionComponent}
];

bootstrapApplication(AppComponent, {
  providers: [
    provideRouter(routes),
    provideHttpClient(
      withInterceptors([authInterceptor])
    ),
    providePrimeNG({
      theme: {
        preset: Aura
      }
    }),
    provideAnimations(),
  ]
});

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { AppComponent } from './app.component';
import { InicioComponent } from './component/inicio/inicio.component';
import { PrincipalComponent } from './component/principal/principal.component';
import { RegistroComponent } from './component/registro/registro.component';
import { HttpClientModule } from '@angular/common/http';
import { CarritoComponent } from './component/carrito/carrito.component';
import { TopBarComponent } from './component/top-bar/top-bar.component';
import { FooterComponent } from './component/footer/footer.component';

@NgModule({
  declarations: [],
  imports: [
    BrowserModule,
    FormsModule,
    InicioComponent,
    PrincipalComponent,
    RegistroComponent,
    TopBarComponent,
    CarritoComponent,
    FooterComponent,
  ],
  providers: [],
  bootstrap: [],
})
export class AppModule {}

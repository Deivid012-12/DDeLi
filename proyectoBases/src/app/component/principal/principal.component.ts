import { Component } from '@angular/core';
import { Avatar } from 'primeng/avatar';
import { Drawer } from 'primeng/drawer';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-principal',
  templateUrl: './principal.component.html',
  standalone: true,
  imports: [
    Avatar,
    Drawer,
    RouterLink,
  ],
  styleUrls: ['./principal.component.css']
})
export class PrincipalComponent {
  visible: boolean = false;



  logout() {
    // lógica de cierre de sesión
  }
}

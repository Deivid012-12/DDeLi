import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-principal',
  templateUrl: './principal.component.html',
  standalone: true,
  imports: [
    RouterModule,
  ],
  styleUrls: ['./principal.component.css']
})
export class PrincipalComponent {
  visible: boolean = false;

  logout() {
  }
}

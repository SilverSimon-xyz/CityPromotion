import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth/auth.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {

  constructor(private authService: AuthService, private router: Router) {
  }

  logout() {
    if(this.isAutheticated()) {
      this.authService.logout();
      this.router.navigate(['/login']);
    }
  }

  isAutheticated(): boolean {
    return this.authService.isAutheticated();
  }

}

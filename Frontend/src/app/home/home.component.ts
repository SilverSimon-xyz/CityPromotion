import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../services/auth/auth.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {
  private isAutheticated = false;

  constructor(private authService: AuthService, private router: Router) {
    this.isAutheticated = this.authService.isAutheticated();
  }

  logout() {
    if(this.isAutheticated) {
      this.authService.logout();
      this.router.navigate(['/login']);
    }
  }

}

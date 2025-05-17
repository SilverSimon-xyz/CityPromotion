import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../services/auth/auth.service';

@Component({
  selector: 'app-dashboard',
  imports: [CommonModule, RouterLink],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent {
    constructor(private authService: AuthService, private router: Router) {}

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

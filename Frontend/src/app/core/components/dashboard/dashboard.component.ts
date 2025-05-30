import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth/auth.service';

@Component({
  selector: 'app-dashboard',
  imports: [CommonModule],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent implements OnInit {

  userRole!: string;

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit() {
    this.authService.fetchUserRole().subscribe({
      next: (response) => {
        this.userRole = response['role'];
        console.log('Ruolo recuperato:', this.userRole);
      }, 
        error: (err: string) => console.error('Error while retrieving role, ', err) 
      })
    
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

import { Component, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth/auth.service';
import { UserService } from '../../services/user/user.service';

@Component({
  selector: 'app-dashboard',
  imports: [CommonModule, RouterLink],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent implements OnInit {

  userRole: string = '';

  constructor(private authService: AuthService, private userService: UserService, private router: Router) {}

  ngOnInit() {
    this.userService.getUserRoles().subscribe(role => {
        this.userRole = role;
      }
    );
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

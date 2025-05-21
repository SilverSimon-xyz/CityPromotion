import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user/user.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-admin.dashboard',
  imports: [CommonModule],
  templateUrl: './admin.dashboard.component.html',
  styleUrl: './admin.dashboard.component.scss'
})
export class AdminDashboardComponent implements OnInit {

  userRole?: string = '';

  constructor(
    private userService: UserService,
    private router: Router
  ) {}

  ngOnInit() {
    //this.userService.getUserRoles().subscribe(role => {this.userRole = role;});
  }

  manageUsers() {
    this.router.navigate(['/users']);
  }
}

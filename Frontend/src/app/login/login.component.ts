import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  constructor(private authService: AuthService, private router: Router) {}

  username: string = ''
  password: string = ''
  errorMessage: string = ''
  
  login() {
    this.authService.login(this.username, this.password).subscribe({
	    next: () => this.router.navigate(['/home']),
      error: () => this.errorMessage = 'Credenziali non valide'
    });
  }
}

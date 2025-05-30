import { Injectable } from '@angular/core';
import { AuthService } from '../auth/auth.service';

@Injectable({
  providedIn: 'root'
})
export class RolePermissionService {

  constructor(private authService: AuthService) { }

  isAdmin(): boolean {
    return this.authService.getUserRole() === 'ADMIN';
  }

  isCurator(): boolean {
    return this.authService.getUserRole() === 'CURATOR';
  }

  isAnimator(): boolean {
    return this.authService.getUserRole() === 'ANIMATOR';
  }

  isContributor(): boolean {
    return this.authService.getUserRole() === 'CONTRIBUTOR';
  }

}

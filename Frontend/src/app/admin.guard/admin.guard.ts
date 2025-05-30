import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../core/services/auth/auth.service';

export const adminGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const authService = inject(AuthService);
  const role = authService.getUserRole()
  if(role === 'ADMIN') {
    return true;
  } else {
    router.navigate(['/error']);
    return false;
  }
};

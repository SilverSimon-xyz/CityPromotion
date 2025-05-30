import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../core/services/auth/auth.service';

export const roleGuard: CanActivateFn = (route, state) => {

  const router = inject(Router);
  const authService = inject(AuthService);

  const role = authService.getUserRole();

  const allowedRoles: string[] = route.data['roles'] || [];

  const notAllowedRoles: string[] = route.data['blockedRoles'] || [];

  if(notAllowedRoles.includes(role)) {
    console.error('Access denied for the role: ', role);
    router.navigate(['/error']);
    return false;
  } else if(allowedRoles.length > 0 && allowedRoles.includes(role)) {
    return true;
  }
  
  router.navigate(['/error']);
  return false;
};

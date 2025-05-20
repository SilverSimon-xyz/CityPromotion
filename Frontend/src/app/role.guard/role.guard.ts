import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { UserService } from '../core/services/user/user.service';

export const roleGuard: CanActivateFn = (route, state) => {

  const router = inject(Router);
  const userService = inject(UserService);

  const requiredRole = route.data['role'];

  if(userService.hasRole(requiredRole)) {
    return true;
  } else {
    router.navigate(['/dashboard']);
    return false;
  }
  
};

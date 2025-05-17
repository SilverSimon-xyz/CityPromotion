import { HttpEvent, HttpHandler, HttpHandlerFn, HttpInterceptorFn, HttpRequest } from '@angular/common/http';
import { AuthService } from './services/auth/auth.service';
import { inject } from '@angular/core';
import { BehaviorSubject, catchError, filter, Observable, switchMap, take, throwError } from 'rxjs';

export const tokenInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);
  const refreshTokenSubject: BehaviorSubject<any> = new BehaviorSubject(null);
  let isRefreshing = false;

  if(req.url.includes('refresh') || req.url.includes('login')) {
    return next(req);
  }

  return next(req).pipe(
    catchError(error => {
      
      if(error.status === 401) {
        authService.logout();
      } else{
        return throwError(() => error);
      }

      if(error.status === 403) {
          return handleAuthErrors(req, next, authService, refreshTokenSubject, isRefreshing);
        } else {
          return throwError(() => new Error(error.message));
      }
    })
  );
}

function handleAuthErrors(
  req: HttpRequest<any>, 
  next: HttpHandlerFn,
  authService: AuthService,
  refreshTokenSubject: BehaviorSubject<any>,
  isRefreshing: boolean
): Observable<HttpEvent<any>> {

  if(!isRefreshing) {
    isRefreshing = true;
    refreshTokenSubject.next(null);

    return authService.refreshToken().pipe(
      switchMap((res: any) => {
        isRefreshing = false;
        authService.saveToken(res);
        refreshTokenSubject.next(res.accessToken);
        return next(req.clone({
          setHeaders: {
            Authorization: `Bearer ${res.accessToken}`
          }
        }));
      }),
      catchError(err => {
        isRefreshing = false;
        authService.logout();
        return throwError(() => new Error(err.message));
      })
    );
  } else {
    return refreshTokenSubject.pipe(
      filter(token => token !== null),
      take(1),
      switchMap(token => next(req.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      })))
    );
  }
}

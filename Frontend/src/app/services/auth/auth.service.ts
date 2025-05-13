import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Inject, Injectable, PLATFORM_ID } from '@angular/core';

import { catchError, map, Observable, of, tap } from 'rxjs';
import { environment } from '../../../environments/environment.development';
import { SessionStorageService } from '../session.storage/session.storage.service';
import { AuthResponse } from '../../auth.response';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiURL = environment.baseUrl;

  constructor(private http: HttpClient, private sessionStorage: SessionStorageService) { }

  login(email: string, password: string): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiURL}/auth/login`, {email, password});
  }

  getAccessToken(): string | null {
    return this.sessionStorage.getItem('accessToken');
  }

  getRefreshToken(): string | null {
    return this.sessionStorage.getItem('refreshToken');
  }

  saveToken(response: AuthResponse): void {
    this.sessionStorage.setItem('accessToken', response.accessToken);
    this.sessionStorage.setItem('refreshToken', response.token);
  }

  refreshToken(): Observable<boolean> {
    const refreshToken = this.sessionStorage.getItem('refreshToken');
    if(refreshToken) {
      return this.http.post<{authToken: string}>(`${this.apiURL}/refresh-token`, {refreshToken})
      .pipe(
        tap(response => { this.sessionStorage.setItem('authToken', response.authToken);
        }),
        map(() => true),
        catchError(() => of(false))
      )
    }
    return of(false);
  }
  
  logout() {
    this.sessionStorage.clear();
  }

  isTokenExpired(): boolean {
    const token = this.sessionStorage.getItem('accessToken');
    if(!token) return true;

    const expiry = JSON.parse(atob(token.split('.')[1])).exp;
    return (expiry * 1000) < Date.now();
  }

  isAutheticated(): boolean {
    return !!this.getAccessToken();
  }
}

import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { catchError, map, Observable, of, tap } from 'rxjs';
import { environment } from '../../../../environments/environment.development';
import { SessionStorageService } from '../session.storage/session.storage.service';
import { AuthResponse } from '../../interfaces/auth.response';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiURL = environment.baseUrl;

  constructor(
    private http: HttpClient, 
    private sessionStorage: SessionStorageService
  ) { }

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
    this.sessionStorage.setItem('refreshToken', response.refreshToken);
  }

  refreshToken(): Observable<boolean> {
    const refreshToken = this.sessionStorage.getItem('refreshToken');
    if(refreshToken) {
      return this.http.post<{accessToken: string}>(`${this.apiURL}/auth/refresh-token`, {refreshToken})
      .pipe(
        tap(response => { this.sessionStorage.setItem('accessToken', response.accessToken);
        }),
        map(() => true),
        catchError(() => of(false))
      )
    }
    return of(false);
  }
  
  logout() {
    const refreshToken = this.getRefreshToken();
    if(!refreshToken) {
      console.error('Token not Found!');
      return;
    } 
    const headers = new HttpHeaders({'Authorization': `Bearer ${refreshToken}`});
    this.http.post<boolean>(`${this.apiURL}/auth/logout`, {token: refreshToken}, { headers }).subscribe( {
      next:(response) => {
        if(response) {
          this.sessionStorage.removeItem('accessToken');
          this.sessionStorage.removeItem('refreshToken');
        } else {
          console.error('Response is false!');
        }
    },
      error: (err) => {
        console.error('Error during logout: ', err);
      }
    });
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

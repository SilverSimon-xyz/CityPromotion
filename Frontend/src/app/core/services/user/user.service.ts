import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { environment } from '../../../../environments/environment.development';
import { User } from '../../interfaces/user';
import { Role } from '../../interfaces/role';
import { SessionStorageService } from '../session.storage/session.storage.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiURL = environment.baseUrl;
  

  constructor(private http: HttpClient, private sessionStorage: SessionStorageService) { }

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(`${this.apiURL}/users/all`);
  }

  getUserById(id: number): Observable<User> {
    return this.http.get<User>(`${this.apiURL}/users/find/${id}`);
  }

  createUser(user: User): Observable<User> {
    return this.http.post<User>(`${this.apiURL}/users/add`, user);
  }

  updateUser(id: number, user: User): Observable<User> {
    return this.http.put<User>(`${this.apiURL}/users/edit/${id}`, user);
  }

  deleteUser(id: number): Observable<void>{
    return this.http.delete<void>(`${this.apiURL}/users/delete/${id}`);
  }

  getUserRoles(): Observable<string> {
    return this.http.get<string>(`${this.apiURL}/users/find/role`).pipe(
      tap(roles => this.sessionStorage.setItem('userRoles', JSON.stringify(roles)))
    );
  }

  hasRole(...roleNames: string[]): boolean {
    const userRoles = JSON.parse(this.sessionStorage.getItem('userRoles') || '[]');
    return roleNames.some(role => userRoles.some((userRole: Role) => userRole.name === role));
  }
}

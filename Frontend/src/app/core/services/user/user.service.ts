import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { environment } from '../../../../environments/environment.development';
import { User } from '../../interfaces/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiURL = environment.baseUrl;
  

  constructor(private http: HttpClient) { }

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

}

import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
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

  getUser(id: number): Observable<User> {
    return this.http.get<User>(`${this.apiURL}/users/find/${id}`);
  }

  createUser(user: User): Observable<User> {
    return this.http.post<User>(`${this.apiURL}/users/add`, user);
  }

  updateUser(id: number, name: string): Observable<User> {
    return this.http.patch<User>(`${this.apiURL}/users/edit/${id}`, name);
  }

  deleteUser(id: number): Observable<Object>{
    return this.http.delete(`${this.apiURL}/users/delete/${id}`);
  }
}

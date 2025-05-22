import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment.development';
import { Contest } from '../../interfaces/contest';

@Injectable({
  providedIn: 'root'
})
export class ContestService {

  private apiURL = environment.baseUrl;

  constructor(private http: HttpClient) { }

  getAllContest(): Observable<Contest[]> {
    return this.http.get<Contest[]>(`${this.apiURL}/contest/all`);
  }

  getContest(id: number): Observable<Contest> {
    return this.http.get<Contest>(`${this.apiURL}/contest/find/${id}`);
  }

  createContest(contest: Contest): Observable<Contest> {
    return this.http.post<Contest>(`${this.apiURL}/contest/add`, contest);
  }

  updateContest(id: number, contest: Contest): Observable<Contest> {
    return this.http.put<Contest>(`${this.apiURL}/contest/edit/${id}`, contest);
  }

  deleteContest(id: number): Observable<Object>{
    return this.http.delete(`${this.apiURL}/contest/delete/${id}`);
  }

  searchContest(name: string): Observable<Contest[]> {
    return this.http.get<Contest[]>(`${this.apiURL}/contest/search?name=${name}`);
  }
}

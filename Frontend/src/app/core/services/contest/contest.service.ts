import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment.development';
import { Contest } from '../../interfaces/contest';
import { ContestParticipant, QuoteCriterion } from '../../interfaces/contest.participant';
import { SessionStorageService } from '../session.storage/session.storage.service';

@Injectable({
  providedIn: 'root'
})
export class ContestService {

  private apiURL = environment.baseUrl;

  constructor(private http: HttpClient, private sessionStorageService: SessionStorageService) { }

  getAllContest(): Observable<Contest[]> {
    return this.http.get<Contest[]>(`${this.apiURL}/contest/all`);
  }

  getContest(id: number): Observable<Contest> {
    return this.http.get<Contest>(`${this.apiURL}/contest/find/${id}`);
  }

  createContest(contest: Contest): Observable<Contest> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.sessionStorageService.getItem('accessToken')}`
    })
    return this.http.post<Contest>(`${this.apiURL}/contest/add`, contest, {headers});
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

  getAllParticipantsContest(id: number): Observable<ContestParticipant[]> {
    return this.http.get<ContestParticipant[]>(`${this.apiURL}/contest/participant/${id}/all`);
  }

  participateContest(idContest: number, file: File): Observable<ContestParticipant> {
    const formData = new FormData();
    formData.append('idContest', idContest.toString());
    formData.append('file', file);
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.sessionStorageService.getItem('accessToken')}`
    })
    return this.http.post<ContestParticipant>(`${this.apiURL}/contest/participant/participate`, formData, {headers});
  }

  deleteParticipation(idContest: number, idParticipant: number): Observable<Object> {
    return this.http.delete<Contest>(`${this.apiURL}/contest/participant/delete/${idContest}/${idParticipant}`);
  }

  getParticipant(id: number): Observable<ContestParticipant> {
    return this.http.get<ContestParticipant>(`${this.apiURL}/contest/participant/${id}`);
  }

  evaluateParticipant(idContest: number, idParticipant: number, request: {vote: number, description: string}): Observable<ContestParticipant> {
    return this.http.patch<ContestParticipant>(`${this.apiURL}/contest/participant/${idContest}/${idParticipant}/validate`, request);
  }

  declareWinners(id: number): Observable<ContestParticipant[]>{
    return this.http.get<ContestParticipant[]>(`${this.apiURL}/contest/participant/winners/${id}`);
  }
}

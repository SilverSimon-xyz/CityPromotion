import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment.development';
import { Contest } from '../../interfaces/contest';
import { ContestParticipation, QuoteCritirion } from '../../interfaces/contest.participation';
import { User } from '../../interfaces/user';
import { MediaFile } from '../../interfaces/media.file';
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

  getAllParticipantsContest(): Observable<ContestParticipation[]> {
    return this.http.get<ContestParticipation[]>(`${this.apiURL}/contest/participant/all`);
  }

  participateContest(idContest: number, idUser: number, mediaFile: MediaFile): Observable<ContestParticipation> {
    return this.http.post<ContestParticipation>(`${this.apiURL}/contest/participant/participate/${idContest}/idUser`, {idUser, mediaFile});
  }

  deleteParticipation(idParticipant: number): Observable<Object> {
    return this.http.delete<Contest>(`${this.apiURL}/contest/participant/delete/${idParticipant}`);
  }

  evaluateParticipant(idParticipant: number, quoteCritirion: QuoteCritirion): Observable<Contest> {
    return this.http.patch<Contest>(`${this.apiURL}/contest/participant/validate/${idParticipant}`, quoteCritirion);
  }

  declareWinners(id: number): Observable<User[]>{
    return this.http.get<User[]>(`${this.apiURL}/contest/participant/winners/${id}`);
  }
}

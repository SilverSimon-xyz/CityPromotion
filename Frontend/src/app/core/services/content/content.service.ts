import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment.development';
import { Content, Status } from '../../interfaces/content';
import { SessionStorageService } from '../session.storage/session.storage.service';

@Injectable({
  providedIn: 'root'
})
export class ContentService {
  private apiURL = environment.baseUrl;

  constructor(private http: HttpClient, private sessionStorageService: SessionStorageService) { }

  getAllContent(): Observable<Content[]> {
    return this.http.get<Content[]>(`${this.apiURL}/contents/all`);
  }

  getContentDetails(id: number): Observable<Content> {
    return this.http.get<Content>(`${this.apiURL}/contents/find/${id}`);
  }

  createContent(formData: FormData): Observable<Content> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.sessionStorageService.getItem('accessToken')}`
    })
    return this.http.post<Content>(`${this.apiURL}/contents/add`, formData, {headers});
  }

  updateContent(id: number, content: Content): Observable<Content> {
    return this.http.put<Content>(`${this.apiURL}/contents/edit/${id}`, content);
  }

  deleteContent(id: number): Observable<Object>{
    return this.http.delete(`${this.apiURL}/contents/delete/${id}`);
  }

  validateContent(id: number, newStatus: Status): Observable<Content> {
    return this.http.patch<Content>(`${this.apiURL}/contents/validate/${id}/status`, {status: newStatus});
  }

  deleteRejectedContent(): Observable<Object> {
    return this.http.delete(`${this.apiURL}/contents/validate/delete/rejected`);
  }
}

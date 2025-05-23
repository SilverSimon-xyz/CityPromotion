import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable, pipe } from 'rxjs';
import { environment } from '../../../../environments/environment.development';
import { Content, Status } from '../../interfaces/content';

@Injectable({
  providedIn: 'root'
})
export class ContentService {
  private apiURL = environment.baseUrl;

  constructor(private http: HttpClient) { }

  getAllContent(): Observable<Content[]> {
    return this.http.get<Content[]>(`${this.apiURL}/contents/all`);
  }

  getContentDetails(id: number): Observable<Content> {
    return this.http.get<Content>(`${this.apiURL}/contents/find/${id}`);
  }

  createContent(formData: FormData): Observable<Content> {
    return this.http.post<Content>(`${this.apiURL}/contents/add`, formData);
  }

  updateContent(id: number, content: Content, idPoi: number): Observable<Content> {
    return this.http.put<Content>(`${this.apiURL}/contents/edit/${id}`, { ...content, idPoi});
  }

  deleteContent(id: number): Observable<Object>{
    return this.http.delete(`${this.apiURL}/contents/delete/${id}`);
  }

  validateContent(id: number, status: Status): Observable<Content> {
    return this.http.patch<Content>(`${this.apiURL}/contents/validate/${id}`, status);
  }

  deleteRejectedContent(): Observable<Object> {
    return this.http.delete(`${this.apiURL}/contents/validate/delete-all-rejected`);
  }
}

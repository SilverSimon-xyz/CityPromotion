import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable, pipe } from 'rxjs';
import { environment } from '../../../../environments/environment.development';
import { MultimediaContent } from '../../interfaces/multimedia.content';

@Injectable({
  providedIn: 'root'
})
export class MultimediaContentService {
  private apiURL = environment.baseUrl;

  constructor(private http: HttpClient) { }

  getAllMultimediaContent(): Observable<MultimediaContent[]> {
    return this.http.get<MultimediaContent[]>(`${this.apiURL}/contents/all`);
/** .pipe(
      map(response => response.map(multimediaContent => ({
        id: multimediaContent.id,
        title: multimediaContent.title,
        type: multimediaContent.type,
        description: multimediaContent.description,
        author: multimediaContent.author,
        status: multimediaContent.status,
        mediaFile: { ...multimediaContent.mediaFile },
        pointOfInterest: { ...multimediaContent.pointOfInterest },
        createdAt: multimediaContent.createdAt,
        updatedAt: multimediaContent.updatedAt,
      }))))*/
    
  }

  getMultimediaContent(id: number): Observable<MultimediaContent> {
    return this.http.get<MultimediaContent>(`${this.apiURL}/contents/find/${id}`);
  }

  createMultimediaContent(multimediaContent: MultimediaContent): Observable<MultimediaContent> {
    return this.http.post<MultimediaContent>(`${this.apiURL}/contents/add`, multimediaContent);
  }

  updateMultimediaContent(id: number, multimediaContent: MultimediaContent): Observable<MultimediaContent> {
    return this.http.patch<MultimediaContent>(`${this.apiURL}/contents/edit/${id}`, multimediaContent);
  }

  deleteMultimediaContent(id: number): Observable<Object>{
    return this.http.delete(`${this.apiURL}/contents/delete/${id}`);
  }
}

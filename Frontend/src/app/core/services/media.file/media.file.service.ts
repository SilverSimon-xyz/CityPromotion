import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment.development';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MediaFileService {

  private apiURL = environment.baseUrl;
  
  constructor(private http: HttpClient) { }

  getFile(id: number): Observable<Blob> {
    return this.http.get(`${this.apiURL}/media/get/${id}`, { responseType: 'blob' });
  }
}

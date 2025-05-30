import { Injectable } from '@angular/core';
import { PointOfInterest, PointOfInterestType } from '../../interfaces/point.of.interest';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment.development';
import { SessionStorageService } from '../session.storage/session.storage.service';

@Injectable({
  providedIn: 'root'
})
export class PoiService {
private apiURL = environment.baseUrl;

  constructor(private http: HttpClient, private sessionStorageService: SessionStorageService) { }

  getAllPointOfInterest(): Observable<PointOfInterest[]> {
    return this.http.get<PointOfInterest[]>(`${this.apiURL}/poi/all`);
  }

  getPointOfInterest(id: number): Observable<PointOfInterest> {
    return this.http.get<PointOfInterest>(`${this.apiURL}/poi/find/${id}`);
  }

  createPointOfInterest(pointOfInterest: PointOfInterest): Observable<PointOfInterest> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.sessionStorageService.getItem('accessToken')}`
    })
    return this.http.post<PointOfInterest>(`${this.apiURL}/poi/add`, pointOfInterest, {headers});
  }

  updatePointOfInterest(id: number, pointOfInterest: PointOfInterest): Observable<PointOfInterest> {
    return this.http.put<PointOfInterest>(`${this.apiURL}/poi/edit/${id}`, pointOfInterest);
  }

  deletePointOfInterest(id: number): Observable<Object>{
    return this.http.delete(`${this.apiURL}/poi/delete/${id}`);
  }

  searchPointOfIntersts(name: string, type: PointOfInterestType): Observable<PointOfInterest[]> {
    const params: any = {};
    if(name) params.name = name;
    if(type) params.type = type;
    return this.http.get<PointOfInterest[]>(`${this.apiURL}/poi/search`, {params});
  }

}

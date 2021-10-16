import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { ProjectRelease } from '../entity/project_release';

@Injectable({
  providedIn: 'root'
})

export class ProjectReleasesService {

  constructor(private http: HttpClient) { }

  getByID(proId: number): Observable<ProjectRelease> {
    return this.http.get<ProjectRelease>(`${environment.baseUrl}projects/releases/${proId}`);
  }

  getAllByProjectID(proId: number): Observable<any> {
    return this.http.get(`${environment.baseUrl}projects/${proId}/releases`);
  }

  create(proId: number, data: any): Observable<any> {
    return this.http.post(`${environment.baseUrl}projects/${proId}/releases/`, data);
  }

  update(proId: number, data: any): Observable<any> {
    return this.http.put(`${environment.baseUrl}projects/${proId}/releases/`, data);
  }

  delete(proId: number, id: any): Observable<any> {
    return this.http.delete(`${environment.baseUrl}rojects/${proId}/releases/${id}`);
  }
}

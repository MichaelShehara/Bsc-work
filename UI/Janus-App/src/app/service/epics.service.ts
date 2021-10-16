import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Epic } from '../entity/epic';
import { Project } from '../entity/project';


@Injectable({
  providedIn: 'root'
})
export class EpicsService {

  private baseUrl = environment.baseUrl + 'projects/';

  constructor(private http: HttpClient) { }

  createEpic(epic: Epic, id: any): Observable<object> {
    return this.http.post(`${environment.baseUrl}projects/${id}/epics`, epic);
  }

  getAll(): Observable<Epic[]> {
    return this.http.get<Epic[]>(`${environment.baseUrl}epics/`);
  }

  getAllbyPid(id: any): Observable<any> {
    return this.http.get(`${environment.baseUrl}projects/${id}/allepics`);
  }

  getAllWithPagenation(id: any, pageNo: number, pageSize: number): Observable<Project[]> {
    return this.http.get<Epic[]>(this.baseUrl + id + '/epics?pageNo=' + pageNo + '&pageSize=' + pageSize);
  }

  getByID(proid: number, id: number): Observable<any> {
    return this.http.get(`${environment.baseUrl}projects/${proid}/epics/${id}`);
  }

  update(proid: number, data: any): Observable<any> {
    return this.http.put(`${environment.baseUrl}projects/${proid}/epics`, data);
  }
  delete(id: any): Observable<any> {
    return this.http.delete(`${environment.baseUrl}epics/${id}`);
  }

}

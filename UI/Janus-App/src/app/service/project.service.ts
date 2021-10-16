import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Project } from '../entity/project';
import { environment } from '../../environments/environment';


@Injectable({
  providedIn: 'root'
})

export class ProjectService {
  private baseUrl = environment.baseUrl + 'projects/';

  constructor(private http: HttpClient) { }

  getAll(): Observable<Project[]> {
    return this.http.get<Project[]>(`${environment.baseUrl}projects/`)
  }

  getAllWithPagenation(pageNo: number, pageSize: number): Observable<Project[]> {
    return this.http.get<Project[]>(this.baseUrl + 'list?pageNo=' + pageNo + '&pageSize=' + pageSize);
  }

  getByID(id: number): Observable<Project> {
    return this.http.get<Project>(`${environment.baseUrl}projects/${id}`);
  }

  create(data: any): Observable<any> {
    return this.http.post(`${environment.baseUrl}projects/`, data);
  }

  update(data: any): Observable<any> {
    return this.http.put(`${environment.baseUrl}projects/`, data);
  }

  delete(id: any): Observable<any> {
    return this.http.delete(`${environment.baseUrl}projects/${id}`);
  }

  getallTasksNotinSprint(id: any): Observable<any> {
    return this.http.get(`${environment.baseUrl}projects/${id}/tasks/backlog`)
  }

  getallIssuesNotinSprint(id: any): Observable<any> {
    return this.http.get(`${environment.baseUrl}projects/${id}/issues/backlog`)
  }

}

import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Issue } from '../entity/issue';
import { Project } from '../entity/project';


@Injectable({
  providedIn: 'root'
})
export class IssueService {

  private baseUrl = environment.baseUrl + 'projects/';
  private baseUrlForSprint = environment.baseUrl + 'sprints/';

  constructor(private http: HttpClient) { }

  createIssue(issue: Issue, id: any): Observable<object> {
    return this.http.post(`${environment.baseUrl}projects/${id}/issues`, issue);
  }

  getAllbyPid(id: any): Observable<any> {
    return this.http.get(`${environment.baseUrl}projects/${id}/allissues`);
  }

  getAllWithPagenation(id: any, pageNo: number, pageSize: number): Observable<Issue[]> {
    return this.http.get<Issue[]>(this.baseUrl + id + '/issues?pageNo=' + pageNo + '&pageSize=' + pageSize);
  }

  getAllPendingIssueINSprint(id: any, state: string): Observable<Issue[]> {
    return this.http.get<Issue[]>(this.baseUrlForSprint + id + '/issues?s=' + state);
  }

  getByID(proid: number, id: number): Observable<any> {
    return this.http.get(`${environment.baseUrl}projects/${proid}/issues/${id}`);
  }

  update(projectId: number, data: any): Observable<any> {
    return this.http.put(`${environment.baseUrl}projects/${projectId}/issues`, data);
  }

  delete(id: any): Observable<any> {
    return this.http.delete(`${environment.baseUrl}issues/${id}`);
  }

  updateResolvedTransition(sprintId: number, issueId: number, data: any): Observable<any> {
    return this.http.put(`${environment.baseUrl}sprints/${sprintId}/issues/${issueId}/transition`, data);
  }

}

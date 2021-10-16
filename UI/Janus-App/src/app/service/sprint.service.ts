import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { Sprint } from '../entity/sprint';


@Injectable({
  providedIn: 'root'
})

export class SprintService {
  private baseUrl = environment.baseUrl + 'projects/';

  constructor(private http: HttpClient) { }


  // create
  createSprint(sprint: Sprint, id: any): Observable<any> {
    return this.http.post(`${environment.baseUrl}projects/${id}/sprints`, sprint)
    // .pipe(catchError(this.errorHandler));
  }

  // getall
  getSprintList(): Observable<Sprint[]> {
    return this.http.get<Sprint[]>(`${environment.baseUrl}sprint/`)
    // .pipe(catchError(this.errorHandler));
  }


  // update
  updateSprint(projectID: number, data: any): Observable<any> {
    return this.http.put(`${environment.baseUrl}projects/${projectID}/sprints`, data)

  }

  delete(id: any): Observable<any> {
    return this.http.delete(`${environment.baseUrl}sprint/${id}`);
  }


  getByID(proid: number, id: number): Observable<any> {
    return this.http.get(`${environment.baseUrl}projects/${proid}/sprints/${id}`);
  }

  getAllbyPid(id: any): Observable<any> {
    return this.http.get(`${environment.baseUrl}projects/${id}/allsprints`);
  }

  getActiveSprintByProject(id: any): Observable<any> {
    return this.http.get(`${environment.baseUrl}projects/${id}/allActivesprints`);
  }

  getAllWithPagenation(id: any, pageNo: number, pageSize: number): Observable<Sprint[]> {
    return this.http.get<Sprint[]>(this.baseUrl + id + '/sprints?pageNo=' + pageNo + '&pageSize=' + pageSize);
  }

  getCompletedTaskCount(sid: any): Observable<any> {
    return this.http.get(`${environment.baseUrl}sprints/${sid}/completedTaskCount`)

  }

  getCompletedIssueCount(sid: any): Observable<any> {
    return this.http.get(`${environment.baseUrl}sprints/${sid}/completedIssueCount`)

  }

  // getallTasksFromSprint
  getAllTasksFromSprint(sid: any): Observable<any> {
    return this.http.get(`${environment.baseUrl}sprints/${sid}/tasks`)

  }

  // getAllIssuesfromSprint
  getAllIssuesFromSprint(sid: any): Observable<any> {
    return this.http.get(`${environment.baseUrl}sprints/${sid}/issues`)

  }

  // Progress bar
  getProgressbar(sid: any): Observable<any> {
    return this.http.get(`${environment.baseUrl}sprints/${sid}/progress`)

  }

  bulkUpdateForTaskANDIssues(sprintID: number, data: any): Observable<any> {
    return this.http.put(`${environment.baseUrl}sprints/${sprintID}/bulkdeallocate`, data);
  }

  assignIssueById(sId: number, isId: number, data: any): Observable<any> {
    return this.http.put(`${environment.baseUrl}sprints/${sId}/issues/${isId}/allocate`, data);
  }

  assignTaskById(sId: number, tsId: number, alldata: any): Observable<any> {
    return this.http.put(`${environment.baseUrl}sprints/${sId}/tasks/${tsId}/allocate`, alldata);
  }

  updateSprintTransition(projectID: number, sprintID: number, data: any): Observable<any> {
    return this.http.put(`${environment.baseUrl}projects/${projectID}/sprints/${sprintID}/transition`, data);
  }
}

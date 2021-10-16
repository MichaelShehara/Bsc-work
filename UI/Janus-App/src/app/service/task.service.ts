import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Task } from '../entity/task';

@Injectable({
  providedIn: 'root',
})
export class TaskService {

  private newBaseUrl = environment.baseUrl + 'projects/';
  private baseUrlForSprint = environment.baseUrl + 'sprints/';

  constructor(private http: HttpClient) { }

  // create
  createTask(data: any, id: number): Observable<any> {
    return this.http.post(`${environment.baseUrl}stories/${id}/tasks`, data);
  }

  // getall
  getTaskList(): Observable<Task[]> {
    return this.http.get<Task[]>(`${environment.baseUrl}task/`);
  }

  getAllbyPid(id: any): Observable<any> {
    return this.http.get(`${environment.baseUrl}projects/${id}/alltasks`);
  }

  getAllWithPagination(id: any, pageNo: number, pageSize: number): Observable<Task[]> {
    return this.http.get<Task[]>(this.newBaseUrl + id + '/tasks?pageNo=' + pageNo + '&pageSize=' + pageSize);
  }

  getAllPendingTaskINSprint(id: any, state: string): Observable<Task[]> {
    return this.http.get<Task[]>(this.baseUrlForSprint + id + '/tasks?s=' + state);
  }
  // update
  // updateTask(data: any): Observable<any> {
  //   return this.http.put(`${environment.baseUrl}task/`, data);
  // }

  updateTask(storyId: number, data: any): Observable<any> {
    return this.http.put(`${environment.baseUrl}stories/${storyId}/tasks`, data);
  }

  getByID(stid: number, id: number): Observable<any> {
    return this.http.get(`${environment.baseUrl}stories/${stid}/tasks/${id}`);
  }

  delete(id: any): Observable<any> {
    return this.http.delete(`${environment.baseUrl}task/${id}`);
  }

  updateTaskTransition(sprintId: number, taskId: number, data: any): Observable<any> {
    return this.http.put(`${environment.baseUrl}sprints/${sprintId}/tasks/${taskId}/transition`, data);
  }
}

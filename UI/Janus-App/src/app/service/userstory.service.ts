import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Story } from '../entity/story';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserstoryService {

  private baseUrl = environment.baseUrl + 'projects/';

  constructor(private http: HttpClient) { }

  getAll(): Observable<Story[]> {
    return this.http.get<Story[]>(`${environment.baseUrl}stories/`)
  }

  getAllbyPid(id: any): Observable<any> {
    return this.http.get(`${environment.baseUrl}projects/${id}/allstories`);
  }

  getAllWithPagination(id: any, pageNo: number, pageSize: number): Observable<Story[]> {
    return this.http.get<Story[]>(this.baseUrl + id + '/stories?pageNo=' + pageNo + '&pageSize=' + pageSize);
  }

  getAllByEpic(epicId: any): Observable<Story[]> {
    return this.http.get<Story[]>(`${environment.baseUrl}epics/${epicId}/stories`)
  }
  create(epicId: number, data: any): Observable<any> {
    return this.http.post(`${environment.baseUrl}epics/${epicId}/stories`, data);
  }

  updateStory(epicId: number, data: any): Observable<any> {
    return this.http.put(`${environment.baseUrl}epics/${epicId}/stories`, data);
  }

  getById( id: number): Observable<Story> {
    return this.http.get<Story>(`${environment.baseUrl}stories/${id}`);
  }

   // getall
   getList(): Observable<any> {
    return this.http.get(`${environment.baseUrl}stories/`)

  }

  delete(id: any): Observable<any> {
    return this.http.delete(`${environment.baseUrl}stories/${id}`);
  }
}



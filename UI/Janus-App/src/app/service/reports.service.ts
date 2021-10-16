import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { Sprint } from '../entity/sprint';


@Injectable({
  providedIn: 'root'
})

export class ReportsService {

  constructor(private http: HttpClient) { }

  getBySprintID(sprintId: number): Observable<any> {
    return this.http.get(`${environment.baseUrl}sprints/${sprintId}/burndownchart`);
  }

  getDetectsChartBySprintID(projectId: number): Observable<any> {
    return this.http.get(`${environment.baseUrl}projects/${projectId}/issuechart`);
  }

  getSprintChartBySprintID(projectId: number): Observable<any> {
    return this.http.get(`${environment.baseUrl}projects/${projectId}/sprintchart`);
  }
}

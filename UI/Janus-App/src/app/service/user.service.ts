import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

import { Profile } from 'src/app/entity/profile';

@Injectable({
  providedIn: 'root'
})


export class UserService {
  private baseUrl: string;
  constructor(private http: HttpClient) { }

  // get
  getUser(id: number): Observable<Profile> {
    return this.http.get<Profile>(`${environment.baseUrl}users/${id}`);
  }
  // get
  getUserProjectsById(id: number): Observable<Profile> {
    return this.http.get<Profile>(`${environment.baseUrl}users/projects/${id}`);
  }
  // create
  createUser(data: any): Observable<any> {
    return this.http.post(`${environment.baseUrl}users/`, data);
  }

  // create
  createUserWithoutToken(data: any): Observable<any> {
    return this.http.post(`${environment.baseUrl}users/create/`, data);
  }

  // getall
  getUserList(): Observable<any> {
    return this.http.get(`${environment.baseUrl}users/`);
  }
  // update
  updateUser(data: any): Observable<object> {
    return this.http.put(`${environment.baseUrl}users/`, data);
  }


}

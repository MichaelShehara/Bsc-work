import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { RoleList } from '../entity/roleList';

@Injectable({
  providedIn: 'root'
})
export class RoleService {

  constructor(private http: HttpClient) { }
    // getall
    getRoleList(): Observable<RoleList[]> {
      return this.http.get<RoleList[]>(`${environment.baseUrl}roles/`)

    }
}

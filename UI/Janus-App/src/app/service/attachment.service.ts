import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Attachments } from '../entity/attachments';

@Injectable({
  providedIn: 'root'
})
export class AttachmentService {

  constructor(private http: HttpClient) { }

  private baseUrl = environment.baseUrl + 'attachments/';

  getAll(identifier: any): Observable<any> {
    return this.http.get(`${environment.baseUrl}attachments/${identifier}`);
  }

  getAttachmentDownload(identifier: any, id: any): Observable<any> {
    return this.http.get(`${environment.baseUrl}attachments/${identifier}/download/${id}`);
  }

  uploadAttachments(identifier: any, data: any): Observable<any> {
    return this.http.post(`${environment.baseUrl}attachments/${identifier}/upload`, data);
  }

}

// import { Injectable } from '@angular/core';
// import { HttpClient, HttpHeaders } from '@angular/common/http';
// import { environment } from 'src/environments/environment';

// @Injectable({
//   providedIn: 'root'
// })
// export class LoginService {

//   baseUrl = environment.baseUrl;

//   constructor(private http: HttpClient) { }

//   public generateToken(request) {
//     return this.http.post("http://localhost:9911/auth/", request, { responseType: 'text' as 'json' });
//   }

//   public welcome(token) {

//     let tokenString = token;
//     const headers = new HttpHeaders().set("X-API-VERSION", "v1")
//     return this.http.get("http://localhost:9911/", { headers, responseType: 'text' as 'json' })
//   }
// }

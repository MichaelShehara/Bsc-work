import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { User } from '../entity/user';
import { map } from 'rxjs/operators';
import { Router } from '@angular/router';
import { ErrorInterceptor } from '../auth/error.interceptor';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  // static flag = true;
  private userSubject: BehaviorSubject<User>;
  public currentUser: Observable<User>;

  constructor(
    private httpClient: HttpClient,
    private router: Router
    ) {
    this.userSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('userid')));
    this.currentUser = this.userSubject.asObservable();
  }


  login(obj: any) {
    return this.httpClient.post<any>(`${environment.baseUrl}login/`, obj)
      .pipe(map(user => {
        localStorage.setItem('userid', JSON.stringify(user));
        this.userSubject.next(user);
        // AuthenticationService.flag = true;
        // this.startRefreshTokenTimer();
        return user;
      }));
  }


  logout() {
    localStorage.removeItem('userid');
    // this.currentUser = null;
    localStorage.clear();
    // this.stopRefreshTokenTimer();

    this.userSubject.next(null);
    this.router.navigate(['/login']);
  }

  public get currentUserValue(): User {
    return this.userSubject.value;
  }

  refreshToken() {
    return this.httpClient.get<Map<string, string>>(`${environment.baseUrl}token/accessToken/`, {})
    /*.pipe(map((user) => {
      localStorage.setItem('userid', JSON.stringify(user));
        this.userSubject.next(user);
        //AuthenticationService.flag = true;
        //this.startRefreshTokenTimer();
        return user;
    }));*/
  }

  /*private refreshTokenTimeout;

      private startRefreshTokenTimer() {
          // parse json object from base64 encoded jwt token
          const refreshToken = JSON.parse(atob(this.currentUserValue.accessToken.split('.')[1]));
          console.log(this.currentUserValue.id)
          // set a timeout to refresh the token a minute before it expires
          const expires = new Date(refreshToken.exp * 1000);
          const timeout = expires.getTime() - Date.now() - (24 * 60 * 60);
          this.refreshTokenTimeout = setTimeout(() => this.refreshToken().subscribe(), timeout);
      }

      private stopRefreshTokenTimer() {
        clearTimeout(this.refreshTokenTimeout);
    }*/

}

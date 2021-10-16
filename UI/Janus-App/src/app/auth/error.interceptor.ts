import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { User } from '../entity/user';
import { AuthenticationService } from '../service/authentication.service';

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
  constructor(private authenticationService: AuthenticationService) { }
  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(
      catchError((err) => {
        if (err.status === 401) {
          const currentUser = this.authenticationService.currentUserValue;
          // console.log(AuthenticationService.flag)
          if (currentUser && currentUser.accessToken) {
            // AuthenticationService.flag = false;
            this.authenticationService
              .refreshToken()
              .toPromise()
              .then((data) => {
                const user = JSON.parse(localStorage.getItem('userid')) as User;

                user.accessToken = data['accessToken'];
                localStorage.setItem('userid', JSON.stringify(user));
              })
              .catch((error) => {
                alert(error.message);
                this.authenticationService.logout();
              });
          }
        }

        const e = err.error.message || err.statusText;
        return throwError(e);
      })
    );
  }
}

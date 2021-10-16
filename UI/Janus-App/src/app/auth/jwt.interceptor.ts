import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthenticationService } from '../service/authentication.service';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {
  constructor(private authenticationService: AuthenticationService) { }

  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    const currentUser = this.authenticationService.currentUserValue;
    if (currentUser && currentUser.accessToken) {
      let token = '';
      if (req.url.includes('/accessToken/')) {
        token = currentUser.refreshToken;
      } else {
        token = currentUser.accessToken;
      }
      req = req.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`,
          'X-API-VERSION': 'v1',
        },
      });
    } else {
      req = req.clone({
        setHeaders: {
          'X-API-VERSION': 'v1',
        },
      });
    }
    return next.handle(req);
  }
}

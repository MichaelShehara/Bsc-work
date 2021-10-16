import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';
import { AuthenticationService } from '../service/authentication.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {


  constructor(private router: Router, private authenticationService: AuthenticationService) { }

  canActivate(route: ActivatedRouteSnapshot) {
    const currentUser = this.authenticationService.currentUserValue;
    if (currentUser !== null && currentUser) {
      if (this.hasRole(route.data.roles, currentUser.roles)) {
        return true;
      } else {
       // alert('Access Denied, You have not permission to access!')
        this.router.navigate(['/dashboard']);
        return false;
      }
    }

    this.router.navigate(['/login']);
    return false;
  }

  hasRole(routeRoles, userRoles): boolean {

    for (const routeRole of routeRoles) {
      for (const userRole of userRoles) {
        if (routeRole === userRole) {
          return true;
        }
      }
    }
    return false;
  }

}

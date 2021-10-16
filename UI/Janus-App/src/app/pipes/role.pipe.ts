import { Pipe, PipeTransform } from '@angular/core';
import { AuthenticationService } from '../service/authentication.service';

@Pipe({
  name: 'role'
})
export class RolePipe implements PipeTransform {

  constructor(private authenticationService: AuthenticationService) { }

  transform(roles: []): boolean {

    const userRoles = this.authenticationService.currentUserValue.roles;
    for (const routeRole of roles) {
      for (const userRole of userRoles) {
        if (routeRole === userRole) {
          return true;
        }
      }
    }

    return false;
  }

}

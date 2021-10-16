import { Component } from '@angular/core';
import { User } from './entity/user';
import { AuthenticationService } from './service/authentication.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  currentUser: User;

  title = 'TaskCheck-App';

  constructor(private authenticationService: AuthenticationService) {
    // this.authenticationService.currentUser.subscribe(
    //   (user) => (this.currentUser = user)
    // );
  }

  validateRole(): boolean {
    // let userRoles = this.authenticationService.currentUserValue.roles;
    // for(let routeRole of roles){
    //   for(let userRole of userRoles){
    //     if (routeRole === userRole){
    //       return true;
    //     }
    //   }
    // }
    return false;
  }
}

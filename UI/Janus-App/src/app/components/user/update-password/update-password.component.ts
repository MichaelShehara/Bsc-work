import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NbToastrService } from '@nebular/theme';
import { BehaviorSubject } from 'rxjs';
import { first } from 'rxjs/operators';
import { Profile } from 'src/app/entity/profile';
import { User } from 'src/app/entity/user';
import { AuthenticationService } from 'src/app/service/authentication.service';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-update-password',
  templateUrl: './update-password.component.html',
  styleUrls: ['./update-password.component.css'],
})
export class UpdatePasswordComponent implements OnInit {
  userFormPassUpdate: FormGroup;
  submitted = false;
  currentUser: User;
  currentUserProjects: User;
  id: number;
  private userSubject: BehaviorSubject<User>;
  profile = new Profile();
  profileEntity: Profile = new Profile();
  loading = false;

  constructor(
    private formBuilder: FormBuilder,
    private authenticationService: AuthenticationService,
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router,
    private toastrService: NbToastrService
  ) {
    this.currentUser = this.authenticationService.currentUserValue;
    this.userSubject = new BehaviorSubject<User>(
      JSON.parse(localStorage.getItem('userid'))
    );
  }

  ngOnInit() {
    if (this.route.snapshot.params.id) {
      this.id = this.route.snapshot.params.id;
    } else {
      this.id = JSON.parse(localStorage.getItem('userid')).userId;
    }
    this.getUserProfile(this.id);
    this.userService
      .getUserProjectsById(this.id)
      .subscribe((data) => (this.currentUserProjects = data));
    this.userFormPassUpdate = this.formBuilder.group(
      {
        password: ['',
          [
            Validators.required,
            Validators.minLength(8),
            Validators.pattern('(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!#^~%*?&,.<>"\'\\;:\{\\\}\\\[\\\]\\\|\\\+\\\-\\\=\\\_\\\)\\\(\\\)\\\`\\\/\\\\\\]])[A-Za-z0-9\d$@].{7,}'),
          ],
        ],
        confirmPassword: ['', Validators.required],
      },
      {
        validator: MustMatch('password', 'confirmPassword'),
      }
    );
  }

  updateUser() {
    this.profileEntity.id = this.profile.id;
    this.profileEntity.username = this.profile.username;
    this.profileEntity.fullName = this.profile.fullName;
    this.profileEntity.password = this.userFormPassUpdate.get('password').value;
    this.profileEntity.roles = this.profile.roles;
    this.profileEntity.projects = this.profile.projects;
    this.userService.updateUser(this.profileEntity).subscribe(
      (data) => {
        console.log(data);
      },
      (error) => console.log(error)
    );
  }

  getUserProfile(id) {
    this.userService.getUser(id).subscribe(
      (data) => this.profile = data,
    );
  }

  // Validations new
  // check the validity in feilds entered
  isFieldValid(field: string) {
    return (
      !this.userFormPassUpdate.get(field).valid &&
      this.userFormPassUpdate.get(field).touched
    );
  }
  // css changes that occur when entered feilds are wrong
  displayFieldCss(field: string) {
    return {
      'has-error': this.isFieldValid(field),
      'has-feedback': this.isFieldValid(field),
    };
  }
  // validate the feilds
  validateAllFormFields(formGroup: FormGroup) {
    Object.keys(formGroup.controls).forEach((field) => {
      console.log(field);
      const control = formGroup.get(field);
      if (control instanceof FormControl) {
        control.markAsTouched({ onlySelf: true });
      } else if (control instanceof FormGroup) {
        this.validateAllFormFields(control);
      }
    });
  }
  onSubmit() {
    if (this.userFormPassUpdate.valid) {
      this.updateUser();
      this.toastrService.success(
        status || '',
        `Successfully updated password!`
      );
      this.router.navigate(['/profile']);
    } else {
      this.validateAllFormFields(this.userFormPassUpdate);
      this.toastrService.danger(
        status || '',
        `Failed to update password, Please try again!`
      );
    }
  }

  onReset() {
    this.submitted = false;
    this.userFormPassUpdate.reset();
    this.router.navigate(['/profile']);
  }
}

export function MustMatch(controlName: string, matchingControlName: string) {
  return (formGroup: FormGroup) => {
    const control = formGroup.controls[controlName];
    const matchingControl = formGroup.controls[matchingControlName];

    if (matchingControl.errors && !matchingControl.errors.mustMatch) {
      // return if another validator has already found an error on the matchingControl
      return;
    }

    // set error on matchingControl if validation fails
    if (control.value !== matchingControl.value) {
      matchingControl.setErrors({ mustMatch: true });
    } else {
      matchingControl.setErrors(null);
    }
  };
}

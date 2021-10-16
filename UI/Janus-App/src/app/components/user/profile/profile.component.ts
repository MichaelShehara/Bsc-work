import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { AuthenticationService } from 'src/app/service/authentication.service';
import { UserService } from 'src/app/service/user.service';
import { User } from 'src/app/entity/user';
import { BehaviorSubject } from 'rxjs';
import { Profile } from 'src/app/entity/profile';
import { Router, ActivatedRoute } from '@angular/router';
import { NbToastrService } from '@nebular/theme';
import { LoaderService } from 'src/app/service/loader.service';
@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
})
export class ProfileComponent implements OnInit {
  public errorMsg;
  private userSubject: BehaviorSubject<User>;
  submitted = false;
  user: User[];
  formGroup: FormGroup;
  profileJson: string = null;
  currentUser: User;
  currentUserProjects: User;
  profile = new Profile();
  profileEntity: Profile = new Profile();
  users = null;
  id: number;
  url: any;

  constructor(
    private formBuilder: FormBuilder,
    private authenticationService: AuthenticationService,
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router,
    private toastrService: NbToastrService,
  ) {
    this.currentUser = this.authenticationService.currentUserValue;
    this.userSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('userid')));

  }

  ngOnInit() {
    if (this.route.snapshot.params.id) {
      this.id = this.route.snapshot.params.id;
    } else {
      this.id = JSON.parse(localStorage.getItem('userid')).userId;
    }
    this.getUserProfile(this.id);

    this.formGroup = this.formBuilder.group({
      fullName: ['', Validators.required],
      username: ['', Validators.required],
    });
  }

  getUserProfile(id) {
    this.userService.getUser(id).subscribe((profile) => {
      this.profile = profile;
      console.log(this.profile)
    });
  }

  // check the validity in feilds entered
  isFieldValid(field: string) {
    return (
      !this.formGroup.get(field).valid && this.formGroup.get(field).touched
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
  // on trigerring the cancel button the form resets
  reset() {
    this.formGroup.reset();
  }

  updateUser() {
    this.profileEntity.id = this.profile.id;
    this.profileEntity.fullName = this.profile.fullName;
    this.profileEntity.username = this.profile.username;
    this.profileEntity.updatedBy = this.profile.updatedBy;
    //this.profileEntity.roles = this.profile.roles;
    // this.profileEntity.projects = this.profile.projects;
    this.userService.updateUser(this.profileEntity).subscribe(
      (data) => {
        console.log(data);
      },
      (error) => console.log(error)
    );
  }

  onSubmit() {
    if (this.formGroup.valid) {
      this.updateUser();
      this.toastrService.success(status || '', `Successfully updated profile!`);
      this.router.navigate(['/user-list']);
    } else {
      this.validateAllFormFields(this.formGroup);
      this.toastrService.danger(status || '', `Failed to update profile!`);
    }
  }

  onSelectFile(event) {
    if (event.target.files && event.target.files[0]) {
      var reader = new FileReader();

      reader.readAsDataURL(event.target.files[0]);

      reader.onload = (event) => {
        if (reader.result) {
          this.url = reader.result;
        }
      }
    }
  }
  /*updatePassword(id){
    this.router.navigate(['update-password',id]);
  }*/
}

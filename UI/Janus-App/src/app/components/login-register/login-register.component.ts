import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { EmailValidator, FormArray, FormBuilder, FormControl, FormGroup, NgForm, Validators, } from '@angular/forms';
import { UserService } from 'src/app/service/user.service';
import { RoleService } from 'src/app/service/role.service';
import { MustMatch } from '../user/update-password/update-password.component';

import { User } from 'src/app/entity/user';
import { CustomValidators } from './CustomValidators';
import { NbToastrService } from '@nebular/theme';
import { RoleList } from 'src/app/entity/roleList';
import { Role } from 'src/app/entity/role';
import 'src/assets/smtp.js';
import { environment } from 'src/environments/environment';
declare let Email: any;


@Component({
  selector: 'app-login-register',
  templateUrl: './login-register.component.html',
  styleUrls: ['./login-register.component.css']
})
export class LoginRegisterComponent implements OnInit {
  user: User = new User();
  currentUser: User;
  roleList?: RoleList[];
  registerForm: FormGroup;
  validateFields:any;
  loading = false;
  form = {
    role: [{ id: 1 }],
  };

  roleID: any;
  submitted = false;


  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    private roleService: RoleService,
    private router: Router,
    private toastrService: NbToastrService
  ) { }

  ngOnInit() {
    this.roleService.getRoleList().subscribe((data) => (this.roleList = data));
    console.log(this.roleList);
    this.registerForm = this.formBuilder.group(
      {
        roles: new FormArray([]),
        fullName: ['', Validators.required],

        username: ['', [Validators.required, Validators.email]],

        password: ['',
          [
            Validators.required,
            Validators.minLength(8),
            Validators.pattern('(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!#^~%*?&,.<>"\'\\;:\{\\\}\\\[\\\]\\\|\\\+\\\-\\\=\\\_\\\)\\\(\\\)\\\`\\\/\\\\\\]])[A-Za-z0-9\d$@].{7,}'),
          ]],
        confirmPassword: ['', Validators.required],
        role: ['', Validators.required],

      }, {
      validator: MustMatch('password', 'confirmPassword')
    });
  }

  get ordersFormArray() {
    return this.registerForm.controls.roles as FormArray;
  }

  // convenience getter for easy access to form fields
  get f() {
    return this.registerForm.controls;
  }

  // check the validity in feilds entered
  isFieldValid(field: string) {
    return !this.registerForm.get(field).valid && this.registerForm.get(field).touched;
  }
  // css changes that occur when entered feilds are wrong
  displayFieldCss(field: string) {
    return {
      'has-error': this.isFieldValid(field),
      'has-feedback': this.isFieldValid(field)
    };
  }
  // validate the feilds
  validateAllFormFields(registerForm: FormGroup) {
    Object.keys(registerForm.controls).forEach(field => {
      console.log(field);
      const control = registerForm.get(field);
      if (control instanceof FormControl) {
        control.markAsTouched({ onlySelf: true });
      } else if (control instanceof FormGroup) {
        this.validateAllFormFields(control);
      }
    });
  }


  saveUser() {
    this.user.roles = this.form.role;
    this.userService.createUserWithoutToken(this.user).subscribe(
      (response) => {
        this.toastrService.success(
          status || '',
          `User registered succesfully! Please check your email`
        );
        this.onSubmit;
        this.goToUserLogin();
      },
      (error) => {
        console.log(error);
        this.toastrService.danger(status || '', `Failed to add User!`);
      }
    );
    this.validateFields = this.validateAllFormFields(this.registerForm);
  }

  goToUserLogin() {
    this.router.navigate(['/login'])
  }

  // isAdmin() {
  //   this.currentUser.roles.forEach(element => {
  //     if (element) {
  //       element.name === Role.ROLE_ADMINISTRATOR
  //     }
  //   });
  // }

  loginLink(){
    environment.baseUrl + '/login'
  }

  onSubmit(f: NgForm) {
    Email.send({
      Host: 'smtp.gmail.com',
      Username: 'michaelluvis99@gmail.com',
      Password: 'Michael@321',
      To: this.user.username,
      From: `admin@taskcheck.com`,
      Subject: 'Welcome aboard' + ' ' + this.user.fullName + '!!!',
      Body: `
    
<html>

<head>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <style>
    .container2 {
      font-size: large;
      max-width: 50%;
      color: rgb(0, 0, 0);
      margin-right: auto;
      margin-left: auto;
      font-family: Arial, Helvetica, sans-serif;

    }

    .container {
      display: flex;
      align-items: center;
      justify-content: center
    }

    img {
      width: 70px;
      height: 70px;
    }

    .image {
      flex-basis: 5rem
    }

    .text {
      font-size: 20px;
      padding-left: 20px;
    }
  </style>
</head>

<body>
  <div class="container">
    <div class="image">
      <img alt="" src="data:image/jpeg;base64,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" />
    </div>
    <div class="text">
      <h1 style="font-family: Arial, Helvetica, sans-serif;">Welcome to TaskCheck!</h1>
    </div>
  </div>
  <div class="container2">
    <p style="line-height: 40px;"> We are glad you joined our system. You are now registered as an Administrator which makes you
    eligible to access all of our funnctionalities in the system. Hope it makes project management easier for you.
      Thank you.. <br></p>

 <br>
    <footer>
      <small style="background-color:grey">
        <p>TaskCheck team</p>
        <p>Copyrights@2021 TaskCheck</p>
      </small>
    </footer>
  </div>
</body>

</html>`
    }).then(message => {
      // this.toastrService.success(
      //   status || '',
      //   `Email Sent succesfully`
      // );
      //f.resetForm();
    },
      (error) => {
        console.log(error);
        this.toastrService.danger(status || '', `Failed to send email`);
        f.resetForm();
      }

    );

  }
}




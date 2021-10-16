import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { EmailValidator, FormArray, FormBuilder, FormControl, FormGroup, NgForm, Validators, } from '@angular/forms';
import { UserService } from 'src/app/service/user.service';
import { RoleService } from 'src/app/service/role.service';
import { MustMatch } from '../../user/update-password/update-password.component';

import { User } from 'src/app/entity/user';
import { CustomValidators } from './CustomValidators';
import { NbToastrService } from '@nebular/theme';
import { RoleList } from 'src/app/entity/roleList';
import { Role } from 'src/app/entity/role';
import 'src/assets/smtp.js';
declare let Email: any;

@Component({
  selector: 'app-user-registration',
  templateUrl: './user-registration.component.html',
  styleUrls: ['./user-registration.component.css'],
})
export class UserRegistrationComponent implements OnInit {
  user: User = new User();
  currentUser: User;
  roleList?: RoleList[];
  registerForm: FormGroup;
  loading = false;
  form = {
    role: [],
  };

  roleID: any;
  submitted = false;
  UserRole: any = [
    { id: 1, name: 'Developer' },
    { id: 2, name: 'Administrator' },
    { id: 3, name: 'Project manager' },
    { id: 4, name: 'Tester' },
  ];

  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    private roleService: RoleService,
    //private snotify: SnotifyService,
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

  checkboxAdd(event) {
    if (event.srcElement.checked) {
      this.roleID = Number(event.srcElement.id);
      const data = {
        id: this.roleID,
      };
      this.form.role.push(data);
      console.log(this.form.role);
    } else {
      const index = this.form.role.indexOf(event.srcElement.id);
      this.form.role.splice(index + 1, index + 2);
      console.log(this.form.role);

    }
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
    // this.user.roles = this.form.role;
    // this.userService.createUser(this.user).subscribe(
    //   (response) => {
    //     console.log(response);
    //     this.toastrService.success(
    //       status || '',
    //       `User registered succesfully! Please check your email`
    //     );
    //     this.onSubmit;
    //     this.goToUserProfile();
    //   },
    //   (error) => {
    //     console.log(error);
    //     this.toastrService.danger(status || '', `Failed to add User!`);
    //   }
    // );
    // this.validateAllFormFields(this.registerForm);
    this.onSubmit;
    
  }

  goToUserProfile() {
    this.router.navigate(['/user-list'])
  }

  // isAdmin() {
  //   this.currentUser.roles.forEach(element => {
  //     if (element) {
  //       element.name === Role.ROLE_ADMINISTRATOR
  //     }
  //   });
  // }

  onSubmit(f: NgForm) {
  // console.log(Email)
  //   Email.send({
  //     Host: 'smtp.gmail.com',
  //     Username: 'michaelluvis99@gmail.com',
  //     Password: 'Michael@321',
  //     To: this.user.username,
  //     From: `admin@taskcheck.com`,
  //     Subject : 'Welcome aboard' + ' ' + this.user.fullName + '!!!' ,
      /*Body : `
      <i>This is sent as a feedback from my resume page.</i> <br/> <b>Name: </b>${this.model.name} <br /> <b>Email: </b>${this.model.email}<br /> <b>Subject: </b>${this.model.subject}<br /> <b>Message:</b> <br /> ${this.model.message} <br><br> <b>~End of Message.~</b> `
  */
      // Body : `
      // <h3>Welcome to TaskCheck </h3> <br/> <b>Name: </b>
      //  <br /> <b>Email: </b><br /> <b>Subject: </b>
      // <br /> <b>Message:</b> <br /> <br><br> 
      // <b>~End of Message.~</b> `
//       Body : `
//       <!DOCTYPE html>
// <html>

// <head>
//   <meta name="viewport" content="width=device-width, initial-scale=1">
//   <style>
//     .container2 {
//       font-size: large;
//       max-width: 50%;
//       color: rgb(0, 0, 0);
//       margin-right: auto;
//       margin-left: auto;
//       font-family: Arial, Helvetica, sans-serif;

//     }

//     .container {
//       display: flex;
//       align-items: center;
//       justify-content: center
//     }

//     img {
//       width: 70px;
//       height: 70px;
//     }

//     .image {
//       flex-basis: 5rem
//     }

//     .text {
//       font-size: 20px;
//       padding-left: 20px;
//     }
//   </style>
// </head>

// <body>
//   <div class="container">
//     <div class="text">
//       <h1 style="font-family: Arial, Helvetica, sans-serif;">Welcome to TaskCheck</h1>
//     </div>
//   </div>
//   <div class="container2">
//     <p style="line-height: 40px;"> Your administrator has registered you to the system. You will be allowed to log in
//       once you have received your
//       username & password from the administrator.</p>
//     <p style="color: red;"> *Please not that you will not be allowed to login until you have been assigned to a project.
//     </p><br>
//     <footer>
//     <small style="background-color:grey">
//     <p>TaskCheck team</p>
//     <p>Copyrights@2021 TaskCheck</p>
//   </small>
//     </footer>
//   </div>
// </body>

// </html>`
//     }).then(message => { 
//       // this.toastrService.success(
//       //   status || '',
//       //   `Email Sent succesfully`
//       // );
//       // f.resetForm(); 
//       alert(message)
//     },
//     (error) => {
//       console.log(error);
//       this.toastrService.danger(status || '', `Failed to send email`);
//       f.resetForm(); 
//     }
    
//     );

Email.send({
  Host : `smtp.gmail.com`,
  Username : `michaelluvis@gmail.com`,
  Password : `Michael@321`,
  To : this.user.username,
  From : `admin@taskcheck.com`,
  Subject : 'Welcome aboard' + ' ' + this.user.fullName + '!!!',
  Body : `
  <i>This is sent as a feedback from my resume page.</i> <br/> <b>Name: </b> <b>Email: </b> <b>Subject: </b> <b>Message:</b> <br /><br> <b>~End of Message.~</b> `
  }).then( message => {alert(message); f.resetForm(); } );
    
  }

  
}

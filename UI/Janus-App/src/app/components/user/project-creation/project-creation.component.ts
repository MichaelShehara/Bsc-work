import { Component, OnInit } from '@angular/core';
import { Project } from '../../../entity/project';
import { ProjectService } from '../../../service/project.service';
import { Router } from '@angular/router';
import { UserService } from '../../../service/user.service';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { NbToastrService } from '@nebular/theme';


@Component({
  selector: 'app-project-creation',
  templateUrl: './project-creation.component.html',
  styleUrls: ['./project-creation.component.css']
})
export class ProjectCreationComponent implements OnInit {

  user: any[];
  assignUserProjects: any[];
  project: Project = {
    name: '',
    leaderName: '',
    users: [],
    status: '0',
    code: '',
    workWeek: ''
  }
  // submitted = false;
  public errorMsg;
  angForm: FormGroup;
  projectList: any[];
  usersDropdownSettings: any = {};
  groupBySelectedItems: any[];

  constructor(
    private projectService: ProjectService,
    private router: Router,
    private userService: UserService,
    private formBuilder: FormBuilder,
    private toastrService: NbToastrService,
  ) { }

  ngOnInit() {
    this.userService.getUserList().subscribe(
      (data) => {
        if (data) {
          this.user = data;
        }
      },
      (error) => {
        this.toastrService.show('Failed to Get Users!', 'Error', {
          status: 'danger',
        });
      }
    );

    this.angForm = this.formBuilder.group({
      name: ['', Validators.required],
      leaderName: ['', Validators.required],
      assignProject: ['', Validators.required],
      code: [
        '',
        [
          Validators.required,
          Validators.maxLength(4),
          Validators.minLength(2),
          Validators.pattern('[a-zA-Z]*'),
        ],
      ],
      workWeek: ['', Validators.required],

    });

    this.usersDropdownSettings = {
      singleSelection: false,
      idField: 'id',
      textField: 'username',
      selectAllText: 'Select All',
      unSelectAllText: 'UnSelect All',
      itemsShowLimit: 3,
      allowSearchFilter: true,
    };
  }

  // base function to create a project
  saveProject() {
    // this.project.users = this.assignUserProjects;
    this.projectService.create(this.project).subscribe(
      (data) => {
        // this.submitted = true;
      },
      (error) => (this.errorMsg = error)
    );
  }

  // check the validity in feilds entered
  isFieldValid(field: string) {
    return !this.angForm.get(field).valid && this.angForm.get(field).touched;
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
    this.angForm.reset();
  }
  // on trigerring the create button the form validates and then checks the errors and if no
  // error the form create the project
  onSubmit() {
    if (this.angForm.valid) {
      this.saveProject();
      this.toastrService.success(status || '', `Successfully added project!`);
      this.router.navigate(['/projects']);
    } else {
      this.validateAllFormFields(this.angForm);
      this.toastrService.danger(status || '', `Failed to add project!`);
    }
  }

  // Multiple Users Seletion
  onItemSelect(e) {
    this.project.users.push(e);
  }

  onDeSelectItem(e) {
    const index = this.project.users.indexOf(e.id);
    this.project.users.splice(index + 1, index + 2);
    console.log(this.project.users);
  }

  onSelectAll(e) { }
}




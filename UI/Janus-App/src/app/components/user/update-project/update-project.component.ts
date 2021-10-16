import { Component, OnInit } from '@angular/core';
import {
  FormGroup,
  FormBuilder,
  Validators,
  FormControl,
} from '@angular/forms';
import { Project } from 'src/app/entity/project';
import { ProjectService } from 'src/app/service/project.service';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from 'src/app/service/user.service';
import { NbToastrService } from '@nebular/theme';
import { StateService } from 'src/app/service/state.service';
import { User } from 'src/app/entity/user';
import { ProjectReleasesService } from 'src/app/service/project-releases.service';
import { ProjectRelease } from 'src/app/entity/project_release';

@Component({
  selector: 'app-update-project',
  templateUrl: './update-project.component.html',
  styleUrls: ['./update-project.component.css'],
})

export class UpdateProjectComponent implements OnInit {

  projectRelease: ProjectRelease = new ProjectRelease();
  user: User[];
  proID: any;
  project: Project = new Project();
  projectEntity: Project = new Project();
  formGroup: FormGroup;
  id: number;
  projectID: number;
  usersDropdownSettings: any = {};
  groupBySelectedItems: any[];

  constructor(
    private projectService: ProjectService,
    private route: ActivatedRoute,
    private formBuilder: FormBuilder,
    private userService: UserService,
    private router: Router,
    private toastrService: NbToastrService,
    public stateService: StateService,
    private projectReleasesService: ProjectReleasesService,

  ) {

  }

  ngOnInit() {
    this.id = this.route.snapshot.params.id;
    this.projectID = Number(this.id);
    this.getUserDeatails();
    this.getProjectByID(this.id);
    this.getProjectReleaseDetails(this.id);
    //this.createDialogComponent = new CreateDialogComponent();

    this.stateService.setprojectid(this.id);

    this.formGroup = this.formBuilder.group({
      name: ['', Validators.required],
      // code: ['', [Validators.required]],
      status: ['', Validators.required],
      leaderName: ['', Validators.required],
      groupBySelectedItems: ['', Validators.required],
      releaseName: ['', Validators.required],
      targetDate: ['', Validators.required],
      releaseDescription: ['', Validators.required],
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

    this.stateService.selectedProjectId.subscribe(pid => {
      this.proID = Number(pid);
    });
  }

  getUserDeatails() {
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
  }

  getProjectReleaseDetails(proId) {
    this.projectReleasesService.getByID(proId).subscribe((data) => {
      if (data) {
        this.projectRelease = data;
      }
    });
  }

  getProjectByID(id) {
    this.projectService.getByID(id).subscribe((data) => {
      this.project = data;
      this.groupBySelectedItems = this.project.users;
    });
  }

  updateProject() {
    this.projectEntity.id = this.project.id;
    this.projectEntity.name = this.project.name;
    // this.projectEntity.code = this.project.code;
    this.projectEntity.leaderName = this.project.leaderName;
    this.projectEntity.status = this.project.status;
    this.projectEntity.users = this.groupBySelectedItems;
    this.projectEntity.workWeek = this.project.workWeek;

    this.projectService.update(this.projectEntity).subscribe(
      (data) => {
        console.log(data);
      },
      (error) => console.log(error)
    );
  }

  createProjectRelease() {
    const project = {
      id: this.projectID,
    }
    this.projectRelease.name = this.projectRelease.name;
    this.projectRelease.project = project;
    this.projectRelease.description = this.projectRelease.description;
    this.projectRelease.targetDate = this.projectRelease.targetDate;

    this.projectReleasesService.create(this.projectID, this.projectRelease).subscribe(
      (data) => {
        console.log(data);
      },
      (error) => console.log(error)
    );
  }

  onSubmit() {
    if (this.formGroup.valid) {
      this.updateProject();
      this.createProjectRelease();
      this.toastrService.success(status || '', `Successfully Updated!`);
      this.router.navigate(['/projects']);
    } else {
      this.validateAllFormFields(this.formGroup);
      this.toastrService.danger(status || '', `Failed to Update!`);
    }
  }

  // Multiple Users Seletion
  onItemSelect(e) {
    this.groupBySelectedItems.push(e);
    console.log(this.groupBySelectedItems);
  }

  onDeSelectItem(e) {
    const index = this.groupBySelectedItems.indexOf(e.id);
    this.groupBySelectedItems.splice(index + 1, index + 2);
    console.log(this.groupBySelectedItems);
  }

  projectUserChange(event) { }

  cancel() {
    this.router.navigate(['/projects']);
  }

  // Validations new
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


  delete() {
    if (confirm('Are you sure to delete ' + ' '  + this.project.name + '?')) {
      this.projectService.delete(this.proID).subscribe(data => {
        this.router.navigate(['/projects']);
      });
    }
  }

}





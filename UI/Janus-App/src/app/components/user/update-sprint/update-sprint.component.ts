import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Project } from 'src/app/entity/project';
import { Sprint } from 'src/app/entity/sprint';
import { ProjectService } from 'src/app/service/project.service';
import { SprintService } from 'src/app/service/sprint.service';
import { NbDialogService, NbToastrService } from '@nebular/theme';
import { StateService } from 'src/app/service/state.service';
import { AuthenticationService } from 'src/app/service/authentication.service';
import { User } from 'src/app/entity/user';
import { Role } from 'src/app/entity/role';
import { TaskService } from 'src/app/service/task.service';
import { IssueService } from 'src/app/service/issue.service';
import { Issue } from 'src/app/entity/issue';

@Component({
  selector: 'app-update-sprint',
  templateUrl: './update-sprint.component.html',
  styleUrls: ['./update-sprint.component.css']
})
export class UpdateSprintComponent implements OnInit {

  sptID: any;
  minDate = new Date();
  sprint: Sprint = new Sprint();
  sprintEntity: Sprint = new Sprint();
  project: Project[];
  tasks: any[];
  issues: Issue[];
  currentUser: User;
  sprintForm: FormGroup;
  completeForm: FormGroup;
  id: number;
  id2: number
  retrieveData: any;
  isPMStartShowing = false;
  isPMCompleteShowing = false;
  isAllShowing = false;
  isStatusChecking = false;
  tasksANDIssuesDeatils = [];

  constructor(
    private sprintService: SprintService,
    private projectService: ProjectService,
    private route: ActivatedRoute,
    private formBuilder: FormBuilder,
    private stateService: StateService,
    private taskService: TaskService,
    private issueService: IssueService,
    private router: Router,
    private toastrService: NbToastrService,
    private authenticationService: AuthenticationService,
  ) { }

  ngOnInit() {
    this.projectService.getAll().subscribe(data => this.project = data);
    this.id2 = Number(this.route.snapshot.params.id)

    this.stateService.selectedProjectId.subscribe(data => {
      this.id = Number(data);
      if (this.id) {
        this.sprintService.getAllbyPid(this.id).subscribe((response) => {
          if (response) {
            response.find((element) => {
              if (element) {
                if (element.status === 'STARTED') {
                  this.isStatusChecking = true;
                }
              }
            })
          }
        });
      }
    });

    this.taskService.getAllPendingTaskINSprint(this.id2, 'OPEN').subscribe((data) => {
      if (data) {
        this.tasks = data;
        if (this.tasks) {
          this.tasks.forEach(element => {
            if (element) {
              const newObject = {
                id: element.id,
                type: 1,
              }
              this.tasksANDIssuesDeatils.push(newObject);
            }
          });
        }
      }
    });

    this.issueService.getAllPendingIssueINSprint(this.id2, 'OPEN').subscribe((data) => {
      if (data) {
        this.issues = data;
        this.issues.forEach(element => {
          if (element) {
            const newObject = {
              id: element.id,
              type: 2,
            }
            this.tasksANDIssuesDeatils.push(newObject);
          }
        });
      }
    });

    this.sprintForm = this.formBuilder.group({
      name: ['', Validators.required],
      status: ['', Validators.nullValidator],
      startAt: ['', Validators.required],
      endAt: ['', Validators.required],
      goals: ['', Validators.required],
    });

    this.completeForm = this.formBuilder.group({
      comment: ['', Validators.required],
    });

    this.sprintService.getByID(this.id, this.id2).subscribe((data) => {
      this.authenticationService.currentUser.subscribe(
        (x) => (this.currentUser = x)
      );
      if (data) {
        this.sprint = data;
      }
      this.currentUser.roles.find((element) => {
        if (element === Role.ROLE_PROJECT_MANAGER || element === Role.ROLE_ADMINISTRATOR && this.sprint.status === 'NOT_STARTED') {
          this.isAllShowing = false;
          this.isPMStartShowing = true;
          this.isPMCompleteShowing = false;
        } else if (
          element === Role.ROLE_PROJECT_MANAGER || element === Role.ROLE_ADMINISTRATOR && this.sprint.status === 'STARTED'
        ) {
          this.sprintFormDisabling();
          this.isAllShowing = false;
          this.isPMCompleteShowing = true;
          this.isPMStartShowing = false;
        } else if (
          element === Role.ROLE_PROJECT_MANAGER || element === Role.ROLE_ADMINISTRATOR && 
          (this.sprint.status === 'COMPLETED' ||
            this.sprint.status === 'CANCELLED')
        ) {
          this.sprintFormDisabling();
          this.isAllShowing = true;
          this.isPMCompleteShowing = false;
          this.isPMStartShowing = false;
        } else if (
          this.sprint.status === "NOT_STARTED" &&
          (element === Role.ROLE_DEVELOPER ||
            element === Role.ROLE_TESTER ||
            element === Role.ROLE_ADMINISTRATOR)
        ) {
          this.sprintFormDisabling();
          this.isAllShowing = true;
          this.isPMCompleteShowing = false;
          this.isPMStartShowing = false;
        } else if (
          this.sprint.status === 'STARTED' &&
          (element === Role.ROLE_DEVELOPER ||
            element === Role.ROLE_TESTER ||
            element === Role.ROLE_ADMINISTRATOR)
        ) {
          this.sprintFormDisabling();
          this.isAllShowing = true;
          this.isPMCompleteShowing = false;
          this.isPMStartShowing = false;
        } else if (
          this.sprint.status === 'COMPLETED' &&
          (element === Role.ROLE_DEVELOPER ||
            element === Role.ROLE_TESTER ||
            element === Role.ROLE_ADMINISTRATOR)
        ) {
          this.sprintFormDisabling();
          this.isAllShowing = true;
          this.isPMCompleteShowing = false;
          this.isPMStartShowing = false;
        } else if (
          this.sprint.status === 'CANCELLED' &&
          (element === Role.ROLE_DEVELOPER ||
            element === Role.ROLE_TESTER ||
            element === Role.ROLE_ADMINISTRATOR)
        ) {
          this.sprintFormDisabling();
          this.isAllShowing = true;
          this.isPMCompleteShowing = false;
          this.isPMStartShowing = false;
        } else if (
          this.sprint.status === 'NOT_STARTED' &&
          (element === Role.ROLE_DEVELOPER ||
            element === Role.ROLE_TESTER ||
            element === Role.ROLE_ADMINISTRATOR)
        ) {
          this.sprintFormDisabling();
          this.isAllShowing = true;
          this.isPMCompleteShowing = false;
          this.isPMStartShowing = false;
        }
      });
    });
  }

  sprintFormDisabling() {
    this.sprintForm.controls['name'].disable();
    this.sprintForm.controls['startAt'].disable();
    this.sprintForm.controls['endAt'].disable();
    this.sprintForm.controls['goals'].disable();
  }

  isFieldValid(field: string) {
    return !this.sprintForm.get(field).valid && this.sprintForm.get(field).touched;
  }

  isFieldValidCompletePopup(field: string) {
    return (
      !this.completeForm.get(field).valid && this.completeForm.get(field).touched
    );
  }

  displayFieldCss(field: string) {
    return {
      'has-error': this.isFieldValid(field),
      'has-feedback': this.isFieldValid(field)
    };
  }

  validateAllFormFields(spForms: FormGroup) {
    Object.keys(spForms.controls).forEach(field => {
      console.log(field);
      const control = spForms.get(field);
      if (control instanceof FormControl) {
        control.markAsTouched({ onlySelf: true });
      } else if (control instanceof FormGroup) {
        this.validateAllFormFields(control);
      }
    });
  }

  update() {
    if (this.sprintForm.valid) {
      this.sprint.id = this.id2;
      this.sprint.name = this.sprint.name;
      this.sprint.startAt = this.sprint.startAt;
      this.sprint.endAt = this.sprint.endAt;
      this.sprint.goals = this.sprint.goals;
      this.sprintService
        .updateSprint(this.id, this.sprint)
        .subscribe((data) => {
          this.toastrService.success(
            status || '',
            `Successfully updated sprint!`
          );
          this.router.navigate(['/sprint']);
        },
          (error) => {
            this.toastrService.danger(
              status || '',
              `Failed to updated sprint!`
            );
          }
        );
    }
  }

  start() {
    if (this.isStatusChecking === true) {
      this.toastrService.danger(
        status || '',
        `Cannot start this sprint as another sprint is already running!`
      );
    } else {
      const startData = {
        id: this.sprint.id,
        state: 1,
        comment: this.sprint.comment,
      };
      this.sprintService
        .updateSprintTransition(this.id, this.id2, startData)
        .subscribe(
          (res) => {
            this.toastrService.success(
              status || '',
              `Successfully started sprint!`
            );
            this.router.navigate(['/sprint']);
          },
          (error) => console.log(error)
        );
    }
  }

  complete() {
    console.log(this.tasksANDIssuesDeatils);
    this.sprintService
      .bulkUpdateForTaskANDIssues(this.id2, this.tasksANDIssuesDeatils)
      .subscribe((responseData) => {
        console.log(responseData);
      });

    const completeData = {
      id: this.sprint.id,
      state: 2,
      comment: this.sprint.comment,
    };
    this.sprintService
      .updateSprintTransition(this.id, this.id2, completeData)
      .subscribe(
        (res) => {
          this.toastrService.success(
            status || '',
            `Successfully completed sprint!`
          );
          this.router.navigate(['/sprint']);
        },
        (error) => console.log(error)
      );
  }

  cancel() {
    const cancelData = {
      id: this.sprint.id,
      state: 3,
      comment: 'Sprint Canceled',
    };
    this.sprintService
      .updateSprintTransition(this.id, this.id2, cancelData)
      .subscribe(
        (res) => {
          this.toastrService.success(
            status || '',
            `Successfully canceled sprint!`
          );
          this.router.navigate(['/sprint']);
        },
        (error) => console.log(error)
      );
  }

  close() {
    this.router.navigate(['/sprint']);
  }

  delete() {
    if (confirm('Are you sure to delete' + ' ' + this.sprint.name + '?')) {
      this.sprintService.delete(this.id2).subscribe(data => {
        this.router.navigate(['/sprint']);
      });
    }
  }

}

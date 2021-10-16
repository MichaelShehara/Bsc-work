import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { NbToastrService } from '@nebular/theme';
import { Attachments } from 'src/app/entity/attachments';
import { Comments } from 'src/app/entity/comments';
import { Role } from 'src/app/entity/role';
import { Task } from 'src/app/entity/task';
import { User } from 'src/app/entity/user';
import { AttachmentService } from 'src/app/service/attachment.service';
import { AuthenticationService } from 'src/app/service/authentication.service';
import { CommentsService } from 'src/app/service/comments.service';
import { StateService } from 'src/app/service/state.service';
import { TaskService } from 'src/app/service/task.service';
import { UserService } from 'src/app/service/user.service';
import { UserstoryService } from 'src/app/service/userstory.service';
import { HttpClient, HttpEventType } from '@angular/common/http';
import { map, catchError } from "rxjs/operators";
import { throwError } from "rxjs";
import { environment } from 'src/environments/environment';
@Component({
  selector: 'app-update-task',
  templateUrl: './update-task.component.html',
  styleUrls: ['./update-task.component.css']
})
export class UpdateTaskComponent implements OnInit {

  task: Task = new Task();
  story: any = [];
  userList: User[];
  currentUser: User;
  comments: Comments;
  attachments: Attachments;
  taskForms: FormGroup;
  inprogressForm: FormGroup;
  completeForm: FormGroup;
  reopenForm: FormGroup;
  id: number;
  id2: number;
  sprintID: number;
  pid: number;
  stoid: number
  assigneeId: any;
  minDate = new Date();
  isDevQAInProgressShowing = false;
  isDevQACompleteShowing = false;
  isAdminPMShowing = false;
  isDevQAReOpenShowing = false;
  isShowing = false;
  commentsCount: any;
  attachmentCount: any;
  uploadedFile: any;
  progress: number = 0;

  constructor(
    private formBuilder: FormBuilder,
    private taskService: TaskService,
    private router: Router,
    private route: ActivatedRoute,
    private userStoryService: UserstoryService,
    private toastrService: NbToastrService,
    private userService: UserService,
    private stateService: StateService,
    private authenticationService: AuthenticationService,
    private commentService: CommentsService,
    private attachmentService: AttachmentService,
    private http: HttpClient,
  ) { }


  ngOnInit() {
    this.userService.getUserList().subscribe(data => this.userList = data);
    this.id = Number(this.route.snapshot.params.id)

    this.stateService.selectedStoryId.subscribe(data => {
      this.id2 = Number(data);
    });

    this.stateService.selectedSprintId.subscribe((data) => {
      this.sprintID = Number(data);
    });

    this.userStoryService.getAllbyPid(this.id2).subscribe(
      (data) => {
        this.story.push(data);
      },
    );

    this.inprogressForm = this.formBuilder.group({
      assignee: ['', Validators.required],
      comment: ['', Validators.required],
    });

    this.completeForm = this.formBuilder.group({
      assignee: ['', Validators.required],
      comment: ['', Validators.required],
    });

    this.taskForms = this.formBuilder.group({
      summary: ['', Validators.required],
      description: ['', Validators.required],
      priority: ['', Validators.required],
      type: ['', Validators.required],
      status: ['', Validators.nullValidator],
      severity: ['', Validators.required],
      storyPoint: ['', Validators.required],
      impactedArea: ['', Validators.required],
      completedAt: ['', Validators.required],
      assignee: ['', Validators.nullValidator],
      comment: ['', Validators.required],
    });

    this.taskService.getByID(this.id2, this.id).subscribe((data) => {
      this.authenticationService.currentUser.subscribe(
        (x) => (this.currentUser = x)
      );
      if (data) {
        this.task = data;
        // Getting the comments list
        this.commentService.getAll(this.task.identifier).subscribe((commentsData) => {
          if (commentsData) {
            this.commentsCount = commentsData.length
            this.comments = commentsData;
          }
        });

        // Getting the Attachments list
        this.attachmentService.getAll(this.task.identifier).subscribe((attachmentsData) => {
          if (attachmentsData) {
            this.attachmentCount = attachmentsData.length
            this.attachments = attachmentsData;
          }
        });

        if (this.task.assignee) {
          this.taskForms.controls['assignee'].setValue(this.task.assignee.id);
        }
      }
      this.currentUser.roles.find((element) => {
        if (
          this.task.status === 'OPEN' &&
          (element === Role.ROLE_DEVELOPER || element === Role.ROLE_TESTER || element === Role.ROLE_ADMINISTRATOR || element === Role.ROLE_PROJECT_MANAGER)
        ) {
          this.taskForms.controls['assignee'].disable();
          this.isAdminPMShowing = false;
          this.isDevQAInProgressShowing = true;
          this.isDevQACompleteShowing = false;
         } //else if (
        //   element === Role.ROLE_ADMINISTRATOR &&
        //   (this.task.status === 'OPEN' ||
        //     this.task.status === 'IN_PROGRESS' ||
        //     this.task.status === 'COMPLETED')
        // ) {
        //   this.issueFormDisabling();
        //   this.isDevQACompleteShowing = false;
        //   this.isDevQAInProgressShowing = false;
        //   this.isAdminPMShowing = true;
        // } 
        // else if (
        //   element === Role.ROLE_PROJECT_MANAGER  &&
        //   (this.task.status === 'OPEN' ||
        //     this.task.status === 'IN_PROGRESS' ||
        //     this.task.status === 'COMPLETED')
        // ) {
        //   this.issueFormDisabling();
        //   this.isDevQACompleteShowing = false;
        //   this.isDevQAInProgressShowing = false;
        //   this.isAdminPMShowing = true;
        // } 
        else if (
          this.task.status === 'IN_PROGRESS' &&
          (element === Role.ROLE_DEVELOPER || element === Role.ROLE_TESTER || element === Role.ROLE_ADMINISTRATOR || element === Role.ROLE_PROJECT_MANAGER )
        ) {
          this.isDevQACompleteShowing = true;
          this.isAdminPMShowing = false;
          this.isDevQAInProgressShowing = false;
          this.taskForms.controls['assignee'].disable();
        } else if (
          this.task.status === 'COMPLETED' &&
          (element === Role.ROLE_DEVELOPER || element === Role.ROLE_TESTER || element === Role.ROLE_ADMINISTRATOR || element === Role.ROLE_PROJECT_MANAGER )
        ) {
          this.isAdminPMShowing = true;
          this.isDevQAInProgressShowing = false;
          this.isDevQACompleteShowing = false;
          this.issueFormDisabling();
        }
      });
    });
  }

  issueFormDisabling() {
    this.taskForms.controls['summary'].disable();
    this.taskForms.controls['description'].disable();
    this.taskForms.controls['priority'].disable();
    this.taskForms.controls['type'].disable();
    this.taskForms.controls['severity'].disable();
    this.taskForms.controls['storyPoint'].disable();
    this.taskForms.controls['impactedArea'].disable();
    this.taskForms.controls['completedAt'].disable();
    this.taskForms.controls['assignee'].disable();
  }

  onAssigneeChange(assignValue) {
    this.assigneeId = assignValue;
  }

  isFieldValid(field: string) {
    return !this.taskForms.get(field).valid && this.taskForms.get(field).touched;
  }

  isFieldValidinprogressPopup(field: string) {
    return !this.inprogressForm.get(field).valid && this.inprogressForm.get(field).touched;
  }

  isFieldValidInCompletePopup(field: string) {
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
      const control = spForms.get(field);
      if (control instanceof FormControl) {
        control.markAsTouched({ onlySelf: true });
      } else if (control instanceof FormGroup) {
        this.validateAllFormFields(control);
      }
    });
  }

  inProgress() {
    const inProgressData = {
      id: this.task.id,
      state: 1,
      assignee: this.assigneeId,
      comment: this.task.comment,
    };
    this.taskService
      .updateTaskTransition(this.sprintID, this.id, inProgressData)
      .subscribe(
        (inProgressData) => {
          console.log(inProgressData);
          this.toastrService.success(
            status || '',
            `Successfully updated task!`
          );
          this.router.navigate(['/task']);
        },
        (error) => console.log(error)
      );
  }

  updateTask() {
    this.task.id = this.task.id;
    this.task.story = {
      id: this.task.story.id,
    }
    this.task.summary = this.task.summary;
    this.task.description = this.task.description;
    this.task.type = this.task.type;
    this.task.priority = this.task.priority;
    this.task.severity = this.task.severity;
    this.task.storyPoint = this.task.storyPoint;
    this.task.completedAt = this.task.completedAt;
    this.task.impactedArea = this.task.impactedArea;
    this.task.assignee = {
      id: this.assigneeId
    },

      //  this.task.story = {
      //    id: this.id
      //  };
      this.taskService.updateTask(this.task.story.id, this.task).subscribe(
        data => {
          console.log(data);
        },
        error => console.log(error));
  }

  onSubmit() {
    if (this.taskForms) {
      this.updateTask();
      this.toastrService.success(status || '', `Successfully Updated!`);
      this.router.navigate(['/task']);
    } else {
      this.validateAllFormFields(this.taskForms);
      this.toastrService.danger(status || '', `Failed to Update!`);
    }
  }

  complete() {
    if (this.completeForm.valid) {
      const completeData = {
        id: this.task.id,
        state: 2,
        assignee: this.assigneeId,
        comment: this.task.comment,
      };
      this.taskService
        .updateTaskTransition(this.sprintID, this.id, completeData)
        .subscribe(
          (res) => {
            this.toastrService.success(
              status || '',
              `Successfully updated task!`
            );
            this.router.navigate(['/task']);
          },
          (error) => console.log(error)
        );
    } else {
      this.toastrService.danger(status || '', `Failed to Update!`);
    }
  }

  close() {
    this.router.navigate(['/task']);
  }

  cancel() {
    this.router.navigate(['/task']);
  }

  delete() {
    if (confirm('Are you sure to delete this ' + ' ' + this.task.type + '?')) {
      this.taskService.delete(this.id).subscribe(data => {
        this.router.navigate(['/task']);
      });
    }
  }

  downloadAttachment(event: any, item: any) {
    this.attachmentService.getAttachmentDownload(item.identifier, item.id).subscribe((downloadData) => {
      this.toastrService.success(status || '', `Successfully downloaded attachment!`);
    });
  }

  uploadAttachments(file) {
    this.attachments.description = 'Uploading File'
    this.progress = 1;
    const formData = new FormData();
    formData.append('file', file);
    formData.append('description', 'Uploading File')
    if (formData) {
      this.attachmentService.uploadAttachments(this.task.identifier, formData).subscribe(
        (data) => {
          this.toastrService.success(status || '', `Successfully uploaded attachment!`);
          this.progress = 100;
        },
        (error) => {
          console.log(error)
          this.progress = 0;
          this.toastrService.danger(status || '', `Failed to upload attachment!`);
        }
      );
    }
  }
}


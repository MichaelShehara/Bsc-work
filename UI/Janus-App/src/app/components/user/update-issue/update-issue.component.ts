import { Component, Input, OnInit } from '@angular/core';
import { Issue } from '../../../entity/issue';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '../../../entity/user';
import { UserService } from '../../../service/user.service';
import { Project } from '../../../entity/project';
import { StateService } from '../../../service/state.service';
import { NbToastrService } from '@nebular/theme';
import { IssueService } from 'src/app/service/issue.service';
import { ProjectReleasesService } from 'src/app/service/project-releases.service';
import { ProjectRelease } from 'src/app/entity/project_release';
import { AuthenticationService } from 'src/app/service/authentication.service';
import { Role } from 'src/app/entity/role';
import { CommentsService } from 'src/app/service/comments.service';
import { Attachments } from 'src/app/entity/attachments';
import { AttachmentService } from 'src/app/service/attachment.service';
import { Comments } from 'src/app/entity/comments';
import { HttpClient, HttpEventType } from '@angular/common/http';
import { map, catchError } from "rxjs/operators";
import { throwError } from "rxjs";
import { environment } from 'src/environments/environment';
@Component({
  selector: 'app-update-issue',
  templateUrl: './update-issue.component.html',
  styleUrls: ['./update-issue.component.css'],
})
export class UpdateIssueComponent implements OnInit {
  @Input() inputDisabled: boolean;

  assigneeId: any;
  resolvedID: any;
  verifiedId: any;
  fixVersion: any;
  affetedVersion: any;
  user: User[];
  comments: Comments;
  attachments: Attachments;
  currentUser: User;
  project: Project[];
  projectRelease: ProjectRelease[];
  issue: Issue = new Issue();
  issueForm: FormGroup;
  resolvedForm: FormGroup;
  reopenForm: FormGroup;
  rejectForm: FormGroup;
  closedForm: FormGroup;
  id: number;
  id2: number;
  sprintID: number;
  isDevDisabled = false;
  isQaShowing = false;
  isQARoleShowing = false;
  isAdminPMShowing = false;
  commentsCount: any;
  attachmentCount: any;
  uploadedFile: any;
  progress: number = 0;

  constructor(
    private issueService: IssueService,
    private route: ActivatedRoute,
    private formBuilder: FormBuilder,
    private router: Router,
    private stateService: StateService,
    private toastrService: NbToastrService,
    private userService: UserService,
    private projectReleasesService: ProjectReleasesService,
    private authenticationService: AuthenticationService,
    private commentService: CommentsService,
    private attachmentService: AttachmentService,
    private http: HttpClient,
  ) { }

  ngOnInit() {
    this.userService.getUserList().subscribe((data) => {
      this.user = data;
    });

    this.id = Number(this.route.snapshot.params.id);
    this.stateService.selectedProjectId.subscribe((data) => {
      this.id2 = Number(data);
    });

    this.stateService.selectedProjectId.subscribe((data) => {
      this.id2 = Number(data);
    });

    this.stateService.selectedSprintId.subscribe((data) => {
      this.sprintID = Number(data);
    });

    this.projectReleasesService.getAllByProjectID(this.id2).subscribe((data) => {
      if (data) {
        this.projectRelease = data;
      }
    });

    this.resolvedForm = this.formBuilder.group({
      assignee: ['', Validators.required],
      status: ['', Validators.required],
      fixVersion: ['', Validators.required],
      comment: ['', Validators.required],
    });

    this.rejectForm = this.formBuilder.group({
      assignee: ['', Validators.required],
      status: ['', Validators.required],
      comment: ['', Validators.required],
    });

    this.reopenForm = this.formBuilder.group({
      assignee: ['', Validators.required],
      comment: ['', Validators.required],
    });

    this.closedForm = this.formBuilder.group({
      assignee: ['', Validators.required],
      comment: ['', Validators.required],
    });

    this.issueForm = this.formBuilder.group({
      identifier: ['', Validators.required],
      summary: ['', Validators.required],
      priority: ['', Validators.required],
      severity: ['', Validators.required],
      status: ['', Validators.nullValidator],
      assignee: ['', Validators.required],
      resolvedBy: ['', Validators.required],
      verifiedBy: ['', Validators.required],
      storyPoint: ['', Validators.required],
      impactedArea: ['', Validators.required],
      fixVersion: ['', Validators.required],
      affectVersion: ['', Validators.required],
      description: ['', Validators.required],
    });

    this.issueService.getByID(this.id2, this.id).subscribe((data) => {
      this.authenticationService.currentUser.subscribe(
        (x) => (this.currentUser = x)
      );
      if (data) {
        this.issue = data;
        // Getting the comments list
        this.commentService.getAll(this.issue.identifier).subscribe((commentsData) => {
          if (commentsData) {
            this.commentsCount = commentsData.length
            this.comments = commentsData;
          }
        });

        // Getting the Attachments list
        this.attachmentService.getAll(this.issue.identifier).subscribe((attachmentsData) => {
          if (attachmentsData) {
            this.attachmentCount = attachmentsData.length
            this.attachments = attachmentsData;
          }
        });

        this.issueForm.controls['assignee'].setValue(this.issue.assignee.id);
        if (this.issue.resolvedBy) {
          this.issueForm.controls['resolvedBy'].setValue(
            this.issue.resolvedBy.id
          );
        }
        if (this.issue.verifiedBy) {
          this.issueForm.controls['verifiedBy'].setValue(
            this.issue.verifiedBy.id
          );
        }
        if (this.issue.fixVersion) {
          this.issueForm.controls['fixVersion'].setValue(
            this.issue.fixVersion.id
          );
        }
        if (this.issue.affectVersion) {
          this.issueForm.controls['affectVersion'].setValue(
            this.issue.affectVersion.id
          );
        }
      }
      this.currentUser.roles.find((element) => {
        if (
          element === Role.ROLE_DEVELOPER || Role.ROLE_ADMINISTRATOR && this.issue.status === 'OPEN'
        ) {
          this.issueFormDisabling();
          this.isQaShowing = false;
          this.isQARoleShowing = false;
          this.isDevDisabled = true;
          this.isAdminPMShowing = false;
        }
        else if (
          element === Role.ROLE_DEVELOPER || Role.ROLE_ADMINISTRATOR && this.issue.status === 'REOPENED'
        ) {
          this.issueFormDisabling();
          this.isQaShowing = false;
          this.isQARoleShowing = false;
          this.isDevDisabled = true;
          this.isAdminPMShowing = false;
        } else if (
          element === Role.ROLE_DEVELOPER || Role.ROLE_ADMINISTRATOR &&
          (this.issue.status === 'NOT_A_DEFECT' ||
            this.issue.status === 'AS_DESIGNED' ||
            this.issue.status === 'RESOLVED' ||
            this.issue.status === 'CLOSED')
        ) {
          this.issueFormDisabling();
          this.isQaShowing = false;
          this.isDevDisabled = false;
          this.isQARoleShowing = false;
          this.isAdminPMShowing = true;
        } //else if (
        //   element === Role.ROLE_ADMINISTRATOR &&
        //   (this.issue.status === 'OPEN' ||
        //     this.issue.status === 'REOPENED' ||
        //     this.issue.status === 'NOT_A_DEFECT' ||
        //     this.issue.status === 'AS_DESIGNED' ||
        //     this.issue.status === 'RESOLVED' ||
        //     this.issue.status === 'CLOSED')
        // ) {
        //   this.issueFormDisabling();
        //   this.isQaShowing = false;
        //   this.isDevDisabled = false;
        //   this.isQARoleShowing = false;
        //   this.isAdminPMShowing = true;
        // } 
        else if (
          element === Role.ROLE_PROJECT_MANAGER &&
          (this.issue.status === 'OPEN' ||
            this.issue.status === 'REOPENED' ||
            this.issue.status === 'NOT_A_DEFECT' ||
            this.issue.status === 'AS_DESIGNED' ||
            this.issue.status === 'RESOLVED' ||
            this.issue.status === 'CLOSED')
        ) {
          this.issueFormDisabling();
          this.isQaShowing = false;
          this.isDevDisabled = false;
          this.isQARoleShowing = false;
          this.isAdminPMShowing = true;
        } else if (
          element === Role.ROLE_TESTER &&
          (this.issue.status === 'NOT_A_DEFECT' ||
            this.issue.status === 'AS_DESIGNED' ||
            this.issue.status === 'RESOLVED' ||
            this.issue.status === 'REOPENED' ||
            this.issue.status === 'CLOSED')
        ) {
          this.isQaShowing = false;
          this.isDevDisabled = false;
          this.isQARoleShowing = true;
          this.isAdminPMShowing = false;
        } else if (
          element === Role.ROLE_TESTER &&
          this.issue.status === 'OPEN'
        ) {
          this.isQaShowing = true;
          this.isDevDisabled = false;
          this.isQARoleShowing = false;
          this.isAdminPMShowing = false;
        }
      });
    });
  }

  issueFormDisabling() {
    this.issueForm.controls['priority'].disable();
    this.issueForm.controls['severity'].disable();
    this.issueForm.controls['assignee'].disable();
    this.issueForm.controls['resolvedBy'].disable();
    this.issueForm.controls['verifiedBy'].disable();
    this.issueForm.controls['storyPoint'].disable();
    this.issueForm.controls['impactedArea'].disable();
    this.issueForm.controls['fixVersion'].disable();
    this.issueForm.controls['affectVersion'].disable();
    this.issueForm.controls['summary'].disable();
    this.issueForm.controls['description'].disable();
  }

  getAllProjectReleaseDetails(proId) {
    this.projectReleasesService.getAllByProjectID(proId).subscribe((data) => {
      if (data) {
        this.projectRelease = data;
      }
    });
  }

  onAssigneeChange(assignValue) {
    this.assigneeId = assignValue;
  }

  onResolvedByChange(ressolvedValue) {
    this.resolvedID = ressolvedValue;
  }

  onVerifiedByChange(verifiyValue) {
    this.verifiedId = verifiyValue;
  }

  onFixedVersionChange(fixedVersion) {
    this.fixVersion = fixedVersion;
  }
  onAffetedVersionChange(affetedVersion) {
    this.affetedVersion = affetedVersion;
  }

  isFieldValid(field: string) {
    return (
      !this.issueForm.get(field).valid && this.issueForm.get(field).touched
    );
  }

  isFieldValidInResolvedPopup(field: string) {
    return (
      !this.resolvedForm.get(field).valid &&
      this.resolvedForm.get(field).touched
    );
  }

  isFieldValidInRejectPopup(field: string) {
    return (
      !this.rejectForm.get(field).valid && this.rejectForm.get(field).touched
    );
  }

  isFieldValidInReopenPopup(field: string) {
    return (
      !this.reopenForm.get(field).valid && this.reopenForm.get(field).touched
    );
  }

  isFieldValidInClosedPopup(field: string) {
    return (
      !this.closedForm.get(field).valid && this.closedForm.get(field).touched
    );
  }

  displayFieldCss(field: string) {
    return {
      'has-error': this.isFieldValid(field),
      'has-feedback': this.isFieldValid(field),
    };
  }

  validateAllFormFields(formGroup: FormGroup) {
    Object.keys(formGroup.controls).forEach((field) => {
      const control = formGroup.get(field);
      if (control instanceof FormControl) {
        control.markAsTouched({ onlySelf: true });
      } else if (control instanceof FormGroup) {
        this.validateAllFormFields(control);
      }
    });
  }

  resolved() {
    const resolvedData = {
      id: this.issue.id,
      state:
        this.issue.status === 'RESOLVED'
          ? 3
          : this.issue.status === 'NOT_A_DEFECT'
            ? 5
            : this.issue.status === 'AS_DESIGNED'
              ? 6
              : '',
      assignee: this.assigneeId,
      comment: this.issue.comment,
    };
    this.issueService
      .updateResolvedTransition(this.sprintID, this.id, resolvedData)
      .subscribe(
        (resolvedData) => {
          this.toastrService.success(
            status || '',
            `Successfully updated issue!`
          );
          this.router.navigate(['/issue']);
        },
        (error) => console.log(error)
      );
  }

  reject() {
    const rejectedData = {
      id: this.issue.id,
      state:
        this.issue.status === 'NOT_A_DEFECT'
          ? 5
          : this.issue.status === 'AS_DESIGNED'
            ? 6
            : '',
      assignee: this.assigneeId,
      comment: this.issue.comment,
    };
    this.issueService
      .updateResolvedTransition(this.sprintID, this.id, rejectedData)
      .subscribe(
        (rejectedData) => {
          this.toastrService.success(
            status || '',
            `Successfully updated issue!`
          );
          this.router.navigate(['/issue']);
        },
        (error) => console.log(error)
      );
  }

  reopen() {
    const reopenData = {
      id: this.issue.id,
      state: 2,
      assignee: this.assigneeId,
      comment: this.issue.comment,
    };
    this.issueService
      .updateResolvedTransition(this.sprintID, this.id, reopenData)
      .subscribe(
        (reopenData) => {
          console.log(reopenData);
          this.toastrService.success(
            status || '',
            `Successfully updated issue!`
          );
          this.router.navigate(['/issue']);
        },
        (error) => console.log(error)
      );
  }

  closed() {
    if (this.closedForm.valid) {
      const closedData = {
        id: this.issue.id,
        state: 4,
        assignee: this.assigneeId,
        comment: this.issue.comment,
      };
      this.issueService
        .updateResolvedTransition(this.sprintID, this.id, closedData)
        .subscribe(
          (closedData) => {
            this.toastrService.success(
              status || '',
              `Successfully updated issue!`
            );
            this.router.navigate(['/issue']);
          },
          (error) => console.log(error)
        );
    } else {
      this.validateAllFormFields(this.closedForm);
      this.toastrService.danger(status || '', `Failed to Update!`);
    }
  }

  updateIssue() {
    this.issue.id = this.issue.id;
    this.issue.sprint = {
      id: this.id2,
    };
    this.issue.summary = this.issue.summary;
    this.issue.priority = this.issue.priority;
    this.issue.description = this.issue.description;
    this.issue.status = this.issue.status;
    this.issue.priority = this.issue.priority;
    this.issue.impactedArea = this.issue.impactedArea;
    this.issue.assignee = {
      id: this.assigneeId,
    };
    this.issue.resolvedBy = {
      id: this.resolvedID,
    };
    this.issue.verifiedBy = {
      id: this.verifiedId,
    };
    this.issue.fixVersion = {
      id: this.fixVersion,
    };
    this.issue.affectVersion = {
      id: this.affetedVersion,
    };
    this.issueService.update(this.id2, this.issue).subscribe(
      (data) => {
      },
      (error) => console.log(error)
    );
  }

  onSubmit() {
    if (this.issueForm.valid) {
      this.updateIssue();
      this.toastrService.success(status || '', `Successfully updated issue!`);
      this.router.navigate(['/issue']);
    } else {
      this.validateAllFormFields(this.issueForm);
      this.toastrService.danger(status || '', `Failed to update issue!`);
    }
  }

  delete() {
    if (confirm('Are you sure to delete this issue?')) {
      this.issueService.delete(this.id).subscribe((data) => {
        this.router.navigate(['/issue']);
      });
    }
  }

  close() {
    this.router.navigate(['/issue']);
  }

  cancel() {
    this.router.navigate(['/issue']);
  }

  downloadAttachment(event: any, item: any) {
    this.attachmentService.getAttachmentDownload(item.identifier, item.id).subscribe((downloadData) => {
      this.toastrService.success(status || '', `Successfully downloaded attachment!`);
    });
  }
  onFileChange(e: any) {
    this.uploadedFile = e[0];
    this.attachments.name = e[0].name;
    this.attachments.format = e[0].type;
    this.attachments.description = e[0].name;
    console.log(this.uploadedFile.File);
  }

  uploadAttachments(file) {
    this.attachments.description = 'Uploading File'
    this.progress = 1;
    const formData = new FormData();
    formData.append('file', file);
    formData.append('description', 'Uploading File')
    if (formData) {
      this.attachmentService.uploadAttachments(this.issue.identifier, formData).subscribe(
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
  // uploadAttachments(file) {
  //   this.attachments.description = 'Uploading File'
  //   this.progress = 1;
  //   const formData = new FormData();
  //   formData.append('file', file);
  //   formData.append('description', 'Uploading File')
  //   if (formData) {
  //     this.http
  //       .post(`${environment.baseUrl}attachments/${this.issue.identifier}/upload`, formData, {
  //         reportProgress: true,
  //         observe: 'events'
  //       })
  //       .pipe(
  //         map((event: any) => {
  //           if (event.type == HttpEventType.UploadProgress) {
  //             // this.progress = Math.round((100 / event.total) * event.loaded);
  //             this.toastrService.success(status || '', `Successfully uploaded attachment!`);
  //             this.progress = 100;
  //           } else if (event.type == HttpEventType.Response) {
  //             this.progress = null;
  //           }
  //         }),
  //         catchError((err: any) => {
  //           this.progress = null;
  //           this.toastrService.danger(status || '', `Failed to upload attachment!`);
  //           return throwError(err.message);
  //         })
  //       )
  //       .toPromise();
  //   }
  // }
}

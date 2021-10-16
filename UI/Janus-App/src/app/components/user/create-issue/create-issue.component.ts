import { Component, OnInit } from '@angular/core';
import { NbToastrService } from '@nebular/theme';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { IssueService } from 'src/app/service/issue.service';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '../../../entity/user';
import { Issue } from 'src/app/entity/issue';
import { ProjectReleasesService } from 'src/app/service/project-releases.service';
import { ProjectRelease } from 'src/app/entity/project_release';
import { UserService } from 'src/app/service/user.service';
@Component({
  selector: 'app-create-issue',
  templateUrl: './create-issue.component.html',
  styleUrls: ['./create-issue.component.css']
})
export class CreateIssueComponent implements OnInit {

  proId: number;
  user: User[];
  issueForm: FormGroup;
  issue: Issue = new Issue();
  projectRelease: ProjectRelease[];

  constructor(
    private issueService: IssueService,
    private route: ActivatedRoute,
    private formBuilder: FormBuilder,
    private router: Router,
    private toastrService: NbToastrService,
    private projectReleasesService: ProjectReleasesService,
    private userService: UserService,
  ) { }

  ngOnInit() {
    this.userService.getUserList().subscribe(data => this.user = data);
    this.proId = this.route.snapshot.params.id;

    this.issueForm = this.formBuilder.group({
    //  identifier: ['', Validators.required],
      summary: ['', Validators.required],
      description: ['', Validators.required],
      priority: ['', Validators.required],
      severity: ['', Validators.required],
      storyPoint: ['', Validators.required],
      //  status: ['', Validators.required],
      impactedArea: ['', Validators.required],
      comment: ['', Validators.required],
      assignee: ['', Validators.required],
      fixVersion: ['', Validators.required],
      affectVersion: ['', Validators.required],
    });
    this.getAllProjectReleaseDetails(this.proId);
  }
  getAllProjectReleaseDetails(proId) {
    this.projectReleasesService.getAllByProjectID(proId).subscribe((data) => {
      if (data) {
        this.projectRelease = data
      }
    });
  }
  saveIssue(): void {
    const data = {
      name: this.issue.identifier,
      summary: this.issue.summary,
      description: this.issue.description,
      priority: this.issue.priority,
      severity: this.issue.severity,
      storyPoint: this.issue.storyPoint,
      status: 0,
      impactedArea: this.issue.impactedArea,
      assignee: this.issue.assignee,
      fixVersion: this.issue.fixVersion,
      affectVersion: this.issue.affectVersion,
      comment: this.issue.comment,
    }

    this.issueService.createIssue(data, this.proId).subscribe(
      response => {
        console.log(response);
      }
    )
  }

  isFieldValid(field: string) {
    return !this.issueForm.get(field).valid && this.issueForm.get(field).touched;
  }

  validateAllFormFields(issueForm: FormGroup) {
    Object.keys(issueForm.controls).forEach(field => {
      console.log(field);
      const control = issueForm.get(field);
      if (control instanceof FormControl) {
        control.markAsTouched({ onlySelf: true });
      } else if (control instanceof FormGroup) {
        this.validateAllFormFields(control);
      }
    });
  }

  onSubmit() {
    if (this.issueForm.valid) {
      this.saveIssue();
      this.toastrService.success(status || '', `Successfully added issue!`);
      this.router.navigate(['/issue']);
    } else {
      this.validateAllFormFields(this.issueForm);
      this.toastrService.danger(status || '', `Failed to add issue!`);
    }
  }

  reset() {
    this.router.navigate(['/issue']);
  }
}

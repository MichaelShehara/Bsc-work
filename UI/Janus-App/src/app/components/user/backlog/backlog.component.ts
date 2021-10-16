import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NbToastrService } from '@nebular/theme';
import { Sprint } from 'src/app/entity/sprint';
import { LoaderService } from 'src/app/service/loader.service';
import { ProjectService } from 'src/app/service/project.service';
import { SprintService } from 'src/app/service/sprint.service';
import { StateService } from 'src/app/service/state.service';

@Component({
  selector: 'app-backlog',
  templateUrl: './backlog.component.html',
  styleUrls: ['./backlog.component.css']
})
export class BacklogComponent implements OnInit {

  public currentTask;
  public currentIssue;
  taskList: any = [];
  issueList: any = [];
  sprintList: Sprint[];
  selectedSprint: any;
  proID: any;
  data: any;
  id: number;
  sid: number;
  issid: any;
  taskid: any;
  sprintForm: FormGroup;
  visibleEmptyDashbord = false;
  visiblebacklog = false;

  constructor(
    private projectService: ProjectService,
    private sprintService: SprintService,
    private stateService: StateService,
    private formBuilder: FormBuilder,
    private toastrService: NbToastrService,
    private router: Router,
    private loaderService: LoaderService
  ) { }

  ngOnInit() {
    this.stateService.selectedProjectId.subscribe((pid) => {
      this.proID = Number(pid);
        this.getAllTasks(this.proID);
        this.getAllIssues(this.proID);
        this.getAllSprintList(this.proID);
    });

    this.sprintForm = this.formBuilder.group({
      id: ['', Validators.required],
    });
  }

  getAllTasks(projectID) {
    this.projectService
      .getallTasksNotinSprint(projectID)
      .subscribe((data) => {
        if(data.length !== 0){
          this.taskList = data.sort((a, b) => b - a);
        this.visibleEmptyDashbord = false;
        this.visiblebacklog = true;
        }
        else{
          this.visibleEmptyDashbord = true;
          this.visiblebacklog = false;
        }
      });
      console.log(this.taskList)
  }
  
  getAllIssues(projectID) {
    this.projectService
      .getallIssuesNotinSprint(projectID)
      .subscribe((data) => {
        if(data.length !== 0){
          this.issueList = data.sort((a, b) => b - a);
          this.visibleEmptyDashbord = false;
          this.visiblebacklog = true;
          }
          else{
            this.visibleEmptyDashbord = true;
            this.visiblebacklog = false;
          }
      });
  }
  
  getAllSprintList(projectID) {
    this.sprintService.getAllbyPid(projectID).subscribe(
      (data) => {
          this.sprintList = data.sort((a, b) => b - a);
      }
    );
    console.log(this.sprintList)
  }


  public selectsIssue(event: any, item: any) {
    this.issid = item.id;
    this.currentIssue = item.identifier;

  }

  public selectsTask(event: any, item: any) {
    this.taskid = item.id;
    this.currentTask = item.identifier;

  }

  onChange(event) {
    this.sprintList.forEach(element => {
      if (element.id === Number(event)) {
        this.selectedSprint = element
      }
    });
  }

  assignIssue() {
    this.sprintService.assignIssueById(this.selectedSprint.id, this.issid, this.data).subscribe(data => {
      this.toastrService.success(status || '', `Successfully Assigned an issue!`);
      this.router.navigate(['/dashboard']);
    });
  }

  close() {
  }

  assignTask() {
    this.sprintService.assignTaskById(this.selectedSprint.id, this.taskid, this.data).subscribe(data => {
      this.toastrService.success(status || '', `Successfully Assigned an issue!`);
      this.router.navigate(['/dashboard']);
    });

  }

  isFieldValid(field: string) {
    return !this.sprintForm.get(field).valid && this.sprintForm.get(field).touched;
  }

  displayFieldCss(field: string) {
    return {
      'has-error': this.isFieldValid(field),
      'has-feedback': this.isFieldValid(field)
    };
  }

  validateAllFormFields(sprintForm: FormGroup) {
    Object.keys(sprintForm.controls).forEach(field => {
      console.log(field);
      const control = sprintForm.get(field);
      if (control instanceof FormControl) {
        control.markAsTouched({ onlySelf: true });
      } else if (control instanceof FormGroup) {
        this.validateAllFormFields(control);
      }
    });
  }
}

import { Component, OnInit } from '@angular/core';
import { Task } from '../../entity/task';
import { SprintService } from '../../service/sprint.service';
import { TaskService } from '../../service/task.service';
import { StateService } from 'src/app/service/state.service';
import { Router } from '@angular/router';
import { NbToastrService } from '@nebular/theme';
import { LoaderService } from 'src/app/service/loader.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
})
export class DashboardComponent implements OnInit {

  public currentTask;
  public currentIssue;
  openSp: number;
  inprogressSp: number;
  closedSp: number;
  totalProgress: number;
  total: number;
  color: string;
  spID: any;
  sprintID: any;
  projectID: any;
  task: Task;
  taskList: any = [];
  taskListInprogress: any = [];
  taskListCompleted: any = [];
  issueList: any = [];
  totalProgressbar = 100;
  todoCount: number = 2;
  inProgressCount: number;
  completedCount: number;
  visibleEmptyDashbord = false;
  visibleScrumBoard = false;

  constructor(
    private taskService: TaskService,
    private sprintService: SprintService,
    private stateService: StateService,
    private router: Router,
    private toastrService: NbToastrService,
    private loaderService: LoaderService,
  ) { }

  ngOnInit() {
    this.stateService.selectedProjectId.subscribe((data) => {
      this.projectID = Number(data);
    });
    this.stateService.selectedSprintId.subscribe((data) => {
      this.sprintID = Number(data);
      if (this.sprintID !== 0) {
        this.getTaskdDetailsForSprint(this.sprintID);
        this.getIssuedetails(this.sprintID);
        this.getProgressdetails(this.sprintID);
        this.visibleScrumBoard = true;
        this.visibleEmptyDashbord = false;
      } else {
        this.visibleScrumBoard = false;
        this.visibleEmptyDashbord = true;
      }
    });
  }

  getTaskdDetailsForSprint(sprintID) {
    this.sprintService.getAllTasksFromSprint(sprintID).subscribe((data) => {
      if (data) {
        this.taskList = [];
        this.taskListInprogress = [];
        this.taskListInprogress = [];
        data.forEach((element) => {
          if (element.status === 'OPEN') {
            this.taskList.push(element);
          } else if (element.status === 'IN_PROGRESS') {
            this.taskListInprogress.push(element);
          } else if (element.status === 'COMPLETED') {
            this.taskListCompleted.push(element);
          } else {
            this.toastrService.danger(status || '', `Failed to get task list`);
          }
        });
      }
    });
  }

  getIssuedetails(sprintID) {
    this.sprintService.getAllIssuesFromSprint(sprintID).subscribe((data) => {
      if (data) {
        this.issueList = [];
        data.forEach((element) => {
          if (element.status !== 'CLOSED') {
            this.issueList.push(element);
          }
        });
      }
    });
  }

  getProgressdetails(sprintID) {
    this.sprintService.getProgressbar(sprintID).subscribe((data) => {
      if (data) {
        this.total = data.todo + data.inprogress + data.completed;
        this.openSp = (data.todo / this.total) * 100;
        this.inprogressSp = (data.inprogress / this.total) * 100;
        this.closedSp = (data.completed / this.total) * 100;

        this.totalProgress = this.openSp + this.inprogressSp + this.closedSp; // Entire progress bar
      }
    });
  }

  public selectsTask(event: any, item: any) {
    this.currentTask = item.identifier;
    this.router.navigate(['update-task', item.id]);
  }

  public selectsIssue(event: any, item: any) {
    this.currentIssue = item.identifier;
    this.router.navigate(['update-issue', item.id]);
  }
}

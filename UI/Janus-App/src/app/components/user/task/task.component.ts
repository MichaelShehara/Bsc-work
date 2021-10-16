import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NbToastrService } from '@nebular/theme';
import { ListPagination } from 'src/app/entity/listPagination';
import { Task } from 'src/app/entity/task';
import { LoaderService } from 'src/app/service/loader.service';
import { StateService } from 'src/app/service/state.service';
import { TaskService } from 'src/app/service/task.service';
import * as XLSX from 'xlsx';


@Component({
  selector: 'app-task',
  templateUrl: './task.component.html',
  styleUrls: ['./task.component.css']
})
export class TaskComponent implements OnInit {
  task: Task = new Task();
  public currentTask;
  listPagination: ListPagination;
  proID: any;
  taskList: Task[];
  tasks: any[];
  pageIndex: number;
  pageSize: number;
  pageCount: any;
  filterTerm: any;
  errorMsg: any;
  assigneeId: number;
  fileName = 'TASKCHECK_TaskList.xlsx';

  constructor(
    private taskService: TaskService,
    private toastrService: NbToastrService,
    private stateService: StateService,
    private router: Router,
    private loaderService: LoaderService
  ) { }

  ngOnInit() {
    this.pageIndex = 0;
    this.pageSize = 10;

    this.stateService.selectedProjectId.subscribe((pid) => {
      this.proID = Number(pid);
      this.getTaskList(this.proID, this.pageIndex, this.pageSize);
      this.getAllTasksWithoutPagination(this.proID);
    });
  }

  private getTaskList(proID, pageNo, pageSize) {
    if (this.proID !== 0) {
      this.taskService
        .getAllWithPagination(proID, pageNo, pageSize)
        .subscribe((data) => {
          this.listPagination = data;
          if (this.listPagination) {
            if (this.listPagination.content) {
              this.taskList = this.listPagination.content.sort((a, b) => b - a);
            }
            this.pageCount = this.listPagination.totalPages;
          }
        });
    } else {
      console.log('Project ID Not Found');
    }
  }

  getAllTasksWithoutPagination(proID) {
    this.taskService.getAllbyPid(proID).subscribe(
      (data) => {
        this.tasks = data;

      },
      (error) => (this.errorMsg = error)
    );
  }

  get isShowingTaskList() {
    return this.filterTerm;
  }

  exportCsv(): void {

    /* table id is passed over here */
    let element = document.getElementById('export-table');
    const ws: XLSX.WorkSheet = XLSX.utils.table_to_sheet(element);

    /* generate workbook and add the worksheet */
    const wb: XLSX.WorkBook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, 'Sheet1');

    /* save to file */
    XLSX.writeFile(wb, this.fileName);

  }

  onChange(event) {
    this.filterTerm = event.target.value
  }


  createTask() {
    this.router.navigate(['task-creation']);

  }


  public selectTask(event: any, item: any) {

    this.currentTask = item.identifier;
    this.router.navigate(['update-task', item.id]);
  }
  get canMoveToNextPage(): boolean {
    return this.pageIndex < this.pageCount - 1 ? true : false;
  }

  get canMoveToPreviousPage(): boolean {
    return this.pageIndex >= 1 ? true : false;
  }

  moveToNextPage() {
    if (this.canMoveToNextPage) {
      this.pageIndex++;
      this.getTaskList(this.proID, this.pageIndex, this.pageSize);
    }
  }

  moveToPreviousPage() {
    if (this.canMoveToPreviousPage) {
      this.pageIndex--;
      this.getTaskList(this.proID, this.pageIndex, this.pageSize);
    }
  }
}



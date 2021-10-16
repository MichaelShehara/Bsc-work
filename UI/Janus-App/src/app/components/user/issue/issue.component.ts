import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NbToastrService } from '@nebular/theme';
import { Issue } from 'src/app/entity/issue';
import { ListPagination } from 'src/app/entity/listPagination';
import { Project } from 'src/app/entity/project';
import { IssueService } from 'src/app/service/issue.service';
import { LoaderService } from 'src/app/service/loader.service';
import { StateService } from 'src/app/service/state.service';
import * as XLSX from 'xlsx'; 
@Component({
  selector: 'app-issue',
  templateUrl: './issue.component.html',
  styleUrls: ['./issue.component.css']
})
export class IssueComponent implements OnInit {

  public currentIssue;
  listPagination: ListPagination;
  proID: any;
  id: number;
  issues: Issue[];
  issueList: any[];
  project?: Project[];
  pageIndex: number;
  pageSize: number;
  pageCount: any;
  filterTerm: any;
  errorMsg: any;
  fileName= 'TASKCHECK_IssueList.xlsx'; 

  constructor(
    private issueService: IssueService,
    private router: Router,
    private stateService: StateService,
    private toastrService: NbToastrService,
    private loaderService: LoaderService,
  ) { }

  ngOnInit() {
    this.pageIndex = 0;
    this.pageSize = 10;
    this.stateService.selectedProjectId.subscribe((pid) => {
      this.proID = Number(pid);
     this.getIssueList(this.proID, this.pageIndex, this.pageSize);
     this.getAllIssuesWithoutPagination(this.proID);
    });
  }

  getIssueList(proID, pageNo, pageSize) {
    if (this.proID !== 0) {
      this.issueService
        .getAllWithPagenation(proID, pageNo, pageSize)
        .subscribe((data) => {
          this.listPagination = data;
          if (this.listPagination) {
            this.pageCount = this.listPagination.totalPages;
          }
        });
    } else {
      console.log('Project ID Not Found');
    }
  }

  exportCsv(): void{
 
    /* table id is passed over here */   
    let element = document.getElementById('export-table'); 
    const ws: XLSX.WorkSheet =XLSX.utils.table_to_sheet(element);

    /* generate workbook and add the worksheet */
    const wb: XLSX.WorkBook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, 'Sheet1');

    /* save to file */
    XLSX.writeFile(wb, this.fileName);

  }

  getAllIssuesWithoutPagination(proID) {
    this.issueService.getAllbyPid(proID).subscribe(
      (data) => {
        this.issueList = data;
      },
      (error) => (this.errorMsg = error)
    );
  }

  get isShowingIssueList() {
    return this.filterTerm;
  }

  onChange(event) {
    this.filterTerm = event.target.value
  }

  createIssue() {
    if (this.proID !== 0) {
      this.router.navigate(['create-issue', this.proID]);
    } else {
      this.toastrService.danger(status || '', `Please select your project!`);
    }
  }

  public selectIssue(event: any, item: any) {

    this.currentIssue = item.identifier;
    this.router.navigate(['update-issue', item.id]);
  }

  get canMoveToNextPage() : boolean {
    return this.pageIndex < this.pageCount - 1 ? true : false;
  }

  get canMoveToPreviousPage() : boolean {
    return this.pageIndex >= 1 ? true : false;
  }

  moveToNextPage() {
    if (this.canMoveToNextPage) {
      this.pageIndex++;
      this.getIssueList(this.proID, this.pageIndex, this.pageSize);
    }
  }

  moveToPreviousPage() {
    if (this.canMoveToPreviousPage) {
      this.pageIndex--;
      this.getIssueList(this.proID, this.pageIndex, this.pageSize);
    }
  }

  selectEpic(id) {
    this.router.navigate(['userStory-creation', id]);
  }
}

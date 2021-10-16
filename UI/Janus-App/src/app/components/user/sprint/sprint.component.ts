import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NbToastrService } from '@nebular/theme';
import { Sprint } from 'src/app/entity/sprint';
import { ListPagination } from 'src/app/entity/listPagination';
import { SprintService } from 'src/app/service/sprint.service';
import { StateService } from 'src/app/service/state.service';
import { User } from 'src/app/entity/user';
import { Role } from 'src/app/entity/role';
import { AuthenticationService } from 'src/app/service/authentication.service';
import { LoaderService } from 'src/app/service/loader.service';

@Component({
  selector: 'app-sprint',
  templateUrl: './sprint.component.html',
  styleUrls: ['./sprint.component.css']
})
export class SprintComponent implements OnInit {

  public currentSprint;
  listPagination: ListPagination;
  proID: any;
  sprints: Sprint[];
  sprintList: any[];
  currentUser: User;
  pageIndex: number;
  pageSize: number;
  pageCount: any;
  filterTerm: any;
  errorMsg: any;

  constructor(
    private sprintService: SprintService,
    private stateService: StateService,
    private toastrService: NbToastrService,
    private router: Router,
    private authenticationService: AuthenticationService,
    private loaderService: LoaderService
  ) { }

  ngOnInit() {
    this.authenticationService.currentUser.subscribe(
      (x) => (this.currentUser = x)
    );
    this.pageIndex = 0;
    this.pageSize = 10;
    this.stateService.selectedProjectId.subscribe((pid) => {
      this.proID = Number(pid);
      this.getSprintsList(this.proID, this.pageIndex, this.pageSize);
      this.getAllSprintsWithoutPagination(this.proID);
    });
  }

  getSprintsList(proID, pageNo, pageSize) {
    if (this.proID !== 0) {
      this.sprintService
        .getAllWithPagenation(proID, pageNo, pageSize)
        .subscribe((data) => {
          this.listPagination = data;
          if (this.listPagination) {
            if (this.listPagination.content) {
              this.sprintList = this.listPagination.content.sort((a, b) => b - a);
            }
            this.pageCount = this.listPagination.totalPages;
          }
        });
    } else {
      console.log('Project ID Not Found');
    }
  }

  getAllSprintsWithoutPagination(proID) {
    this.sprintService.getAllbyPid(proID).subscribe(
      (data) => {
        this.sprintList = data;
      },
      (error) => (this.errorMsg = error)
    );
  }

  get isProjectManager() {
    return this.currentUser.roles.find(
      (element) =>
        element === Role.ROLE_PROJECT_MANAGER || Role.ROLE_ADMINISTRATOR,
    );
  }

  get isShowingSprintList() {
    return this.filterTerm;
  }

  onChange(event) {
    this.filterTerm = event.target.value
  }

  createSprint() {
    if (this.proID !== 0) {
      this.router.navigate(['sprint-creation', this.proID]);
    } else {
      this.toastrService.danger(status || '', `Please select your project!`);
    }
  }

  public selectSprint(event: any, item: any) {

    this.currentSprint = item.name;
    this.router.navigate(['update-sprint', item.id]);
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
      this.getSprintsList(this.proID, this.pageIndex, this.pageSize);
    }
  }

  moveToPreviousPage() {
    if (this.canMoveToPreviousPage) {
      this.pageIndex--;
      this.getSprintsList(this.proID, this.pageIndex, this.pageSize);
    }
  }

}

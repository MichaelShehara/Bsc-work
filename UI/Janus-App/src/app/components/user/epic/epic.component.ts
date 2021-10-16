import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NbToastrService } from '@nebular/theme';
import { Epic } from 'src/app/entity/epic';
import { ListPagination } from 'src/app/entity/listPagination';
import { EpicsService } from 'src/app/service/epics.service';
import { LoaderService } from 'src/app/service/loader.service';
import { StateService } from '../../../service/state.service';

@Component({
  selector: 'app-epic',
  templateUrl: './epic.component.html',
  styleUrls: ['./epic.component.css']
})
export class EpicComponent implements OnInit {

  public currentEpic;
  listPagination: ListPagination;
  epicsData: any;
  id: number;
  proID: any;
  epics?: Epic[];
  epicList: any[];
  pageIndex: number;
  pageSize: number;
  pageCount: any;
  errorMsg: any;
  filterTerm: any;
  booleanValue: any = false;

  constructor(
    private epicService: EpicsService,
    private router: Router,
    private stateService: StateService,
    private toastrService: NbToastrService,
    private loaderService: LoaderService
  ) { }

  ngOnInit() {
    this.pageIndex = 0;
    this.pageSize = 10;

    this.stateService.selectedProjectId.subscribe((pid) => {
      this.proID = Number(pid);
      this.getEpicList(this.proID, this.pageIndex, this.pageSize);
      this.getAllEpicsWithoutPagination(this.proID);
    });
  }

  getEpicList(proID, pageNo, pageSize) {
    if (this.proID !== 0) {
      this.epicService
        .getAllWithPagenation(proID, pageNo, pageSize)
        .subscribe((data) => {
          this.listPagination = data;
          if (this.listPagination) {
            if (this.listPagination.content) {
              this.epicList = this.listPagination.content.sort((a, b) => b - a);
            }
            this.pageCount = this.listPagination.totalPages;
          }
        });
    } else {
      console.log('Project ID Not Found');
    }
  }

  getAllEpicsWithoutPagination(proID) {
    this.epicService.getAllbyPid(proID).subscribe(
      (data) => {
        this.epicList = data.sort((a, b) => a - b);
      },
      (error) => (this.errorMsg = error)
    );
  }

  get isShowingEpicList() {
    return this.filterTerm;
  }

  onChange(event) {
    this.filterTerm = event.target.value
  }

  createEpic() {
    if (this.proID !== 0) {
      this.router.navigate(['epic-creation', this.proID]);
    } else {
      this.toastrService.danger(status || '', `Please select your project!`);
    }
  }

  public selectsepic(event: any, item: any) {

    this.currentEpic = item.name;
    this.router.navigate(['epic-update', item.id]);
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
      this.getEpicList(this.proID, this.pageIndex, this.pageSize);
    }
  }

  moveToPreviousPage() {
    if (this.canMoveToPreviousPage) {
      this.pageIndex--;
      this.getEpicList(this.proID, this.pageIndex, this.pageSize);
    }
  }

  selectEpic(id) {
    this.router.navigate(['userStory-creation', id]);
  }
}

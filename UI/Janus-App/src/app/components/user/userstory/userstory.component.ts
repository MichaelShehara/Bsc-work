import { Component, OnInit } from '@angular/core';
import { Story } from 'src/app/entity/story';
import { Router } from '@angular/router';
import { UserstoryService } from 'src/app/service/userstory.service';
import { StateService } from 'src/app/service/state.service';
import { ProjectService } from 'src/app/service/project.service';
import { Project } from 'src/app/entity/project';
import { User } from 'src/app/entity/user';
import { UserService } from 'src/app/service/user.service';
import { ListPagination } from 'src/app/entity/listPagination';
import { LoaderService } from 'src/app/service/loader.service';

@Component({
  selector: 'app-userstory',
  templateUrl: './userstory.component.html',
  styleUrls: ['./userstory.component.css'],
})

export class UserstoryComponent implements OnInit {

  public currentStory;
  listPagination: ListPagination;
  proID: any;
  story: any[];
  storyList: any[];
  dataSource;
  pageIndex: number;
  pageSize: number;
  pageCount: any;
  filterTerm: any;
  errorMsg: any;

  constructor(
    private router: Router,
    private userstoryService: UserstoryService,
    private stateService: StateService,
    private loaderService: LoaderService,
  ) { }

  ngOnInit() {
    this.pageIndex = 0;
    this.pageSize = 10;

    this.stateService.selectedProjectId.subscribe((pid) => {
      this.proID = Number(pid);
      if (this.proID !== 0) {
        this.getStoryList(this.proID, this.pageIndex, this.pageSize);
        this.getAllStoriesWithoutPagination(this.proID);
      }
    });
  }

  getStoryList(proID, pageNo, pageSize) {
    this.userstoryService
      .getAllWithPagination(proID, pageNo, pageSize)
      .subscribe((data) => {
        this.listPagination = data;
        if (this.listPagination) {
          if (this.listPagination.content) {
            this.storyList = this.listPagination.content.sort((a, b) => b - a);
          }
          this.pageCount = this.listPagination.totalPages;
        }
      });
  }

  getAllStoriesWithoutPagination(proID) {
    this.userstoryService.getAllbyPid(proID).subscribe(
      (data) => {
        if (data) {
          this.storyList = data;
        } else {
          console.log('No Data');
        }
      },
      (error) => (this.errorMsg = error)
    );
  }

  get isShowingStoryList() {
    return this.filterTerm;
  }

  onChange(event) {
    this.filterTerm = event.target.value
  }

  public selectstory(event: any, item: any) {
    this.stateService.setprojectid(item.id);
    this.currentStory = item.identifier;
    this.router.navigate(['userStory-update', item.id]);
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
      this.getStoryList(this.proID, this.pageIndex, this.pageSize);
    }
  }

  moveToPreviousPage() {
    if (this.canMoveToPreviousPage) {
      this.pageIndex--;
      this.getStoryList(this.proID, this.pageIndex, this.pageSize);
    }
  }
}

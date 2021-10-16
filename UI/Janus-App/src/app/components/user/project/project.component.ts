import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ProjectService } from '../../../service/project.service';
import { UserService } from '../../../service/user.service';
import { User } from 'src/app/entity/user';
import { AuthenticationService } from 'src/app/service/authentication.service';
import { Role } from 'src/app/entity/role';
import { ListPagination } from 'src/app/entity/listPagination';
import { LoaderService } from 'src/app/service/loader.service';
import { StateService } from 'src/app/service/state.service';
import { Profile } from 'src/app/entity/profile';

@Component({
  selector: 'app-project',
  templateUrl: './project.component.html',
  styleUrls: ['./project.component.css']
})
export class ProjectComponent implements OnInit {
  public errorMsg;
  public currentProject;
  id: number;
  projects: any[];
  projectUsers: User;
  listPagination: ListPagination;
  isButtonShow: boolean;
  user: User[];
  currentUser: User;
  proo: any;
  pageIndex: number;
  pageSize: number;
  pageCount: any;
  filterTerm: any;
  proID: any;

  constructor(
    private projectService: ProjectService,
    private userService: UserService,
    private router: Router,
    private authenticationService: AuthenticationService,
    private loaderService: LoaderService,
    private stateService: StateService,
    
  ) { }

  ngOnInit() {
    this.pageIndex = 0;
    this.pageSize = 10;

    this.getProjectDetails(this.pageIndex, this.pageSize);
    this.userService.getUserList().subscribe((data) => (this.user = data));
    
    this.authenticationService.currentUser.subscribe(
      (x) => (this.currentUser = x)
    );
    this.getAllProjectDetailsWithoutPagination();
  }

  getAllProjectDetailsWithoutPagination() {
    this.projectService.getAll().subscribe(
      (data) => {
        this.projects = data;
        console.log(this.projects)
      },
      (error) => (this.errorMsg = error)
    );
  }

  getProjectDetails(pageNo, pageSize) {
    this.projectService
      .getAllWithPagenation(pageNo, pageSize)
      .subscribe((data) => {
        this.listPagination = data;
        console.log(this.listPagination)
        if (this.listPagination) {
          if (this.listPagination.content) {
            this.projects = this.listPagination.content.sort((a, b) => b - a);
          }
          this.pageCount = this.listPagination.totalPages;
        }
      });
  }

  get isAdmin() {
    return this.currentUser.roles.find(
      (element) =>
        element === Role.ROLE_ADMINISTRATOR ||
        element === Role.ROLE_PROJECT_MANAGER
    );
  }

  get isShowingProjectList() {
    return this.filterTerm;
  }

  onChange(event) {
    this.filterTerm = event.target.value
  }

  public selectProject(event: any, item: any) {
    this.currentUser.roles.forEach(element => {
      if (element === Role.ROLE_DEVELOPER || element === Role.ROLE_TESTER) {
        this.currentProject = item.name;
        this.router.navigate(['project-user', item.id])
      } else {
        this.currentProject = item.name;
        this.router.navigate(['update-projects', item.id]);
      }
    });
  }

  createProject() {
    this.router.navigate(['createprojects']);
  }

  selectEpic(id) {
    this.router.navigate(['epic-creation', id]);
  }

  selectSprint(id) {
    this.router.navigate(['sprint-creation', id]);
  }

  selectIssue(id) {
    this.router.navigate(['create-issue', id]);
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
      this.getProjectDetails(this.pageIndex, this.pageSize);
    }
  }

  moveToPreviousPage() {
    if (this.canMoveToPreviousPage) {
      this.pageIndex--;
      this.getProjectDetails(this.pageIndex, this.pageSize);
    }
  }
}

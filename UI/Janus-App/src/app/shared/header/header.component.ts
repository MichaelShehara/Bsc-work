import { AfterViewInit, Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ProjectService } from '../../service/project.service';
import { StateService } from '../../service/state.service';
import { AuthenticationService } from 'src/app/service/authentication.service';
import { NbToastrService } from '@nebular/theme';
import { SprintService } from 'src/app/service/sprint.service';
import { User } from 'src/app/entity/user';
import { Role } from 'src/app/entity/role';
import { UserService } from 'src/app/service/user.service';
import { Subject } from 'rxjs';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit, OnDestroy{

  private destroy$: Subject<void> = new Subject<void>();

  errMsg: any;
  selectedProject: any;
  selectedProjectName: any;
  selectedSprint: any;
  selectedSprintName: any;
  id: number;
  pro: any;
  projects: any;
  sprintList = [];
  isDisabledProject = false;
  userPictureOnly = false;
  isShowingSpritList = false;
  noActiveSprint = false;
  user: any;
  currentUser: User;
  currentUserProjects: User;
  userMenu = [
    // { title: 'Profile' },
    { title: 'Log out' },
  ];
  profileMargin: any;

  constructor(
    public router: Router,
    public projectService: ProjectService,
    private sprintService: SprintService,
    private userService: UserService,
    public stateService: StateService,
    private authenticationService: AuthenticationService,
    private toastrService: NbToastrService,
  ) { }
 

  ngOnInit() {
    this.authenticationService.currentUser.subscribe(x => this.currentUser = x)
    console.log(this.currentUser.roles)
    this.currentUser.roles.find((e) => {
      if (e === Role.ROLE_ADMINISTRATOR) {
        this.projectService.getAll().subscribe(
          (data) => {
            if (data.length !== 0) {
              this.projects = data;
              if (this.projects) {
                this.selectedProject = this.projects[0].id;
                this.selectedProjectName = this.projects[0].name;
                this.stateService.setprojectid(this.selectedProject);
                this.stateService.setprojectname(this.selectedProjectName);
                if (this.selectedProject) {
                  this.sprintService.getActiveSprintByProject(this.selectedProject).subscribe(
                    (activeSprintData) => {
                      if (activeSprintData) {
                        this.isShowingSpritList = true;
                        this.noActiveSprint = false;
                        this.sprintList.push(activeSprintData);
                        if (this.sprintList[0].length !== 0) {
                          this.isShowingSpritList = true;
                          this.selectedSprint = this.sprintList[0][0].id;
                          this.selectedSprintName = this.sprintList[0][0].name;
                          this.stateService.setsprintid(this.selectedSprint);
                        }
                      } else {
                        this.isShowingSpritList = false;
                        this.noActiveSprint = true;
                        this.stateService.setsprintid(0);
                      }
                    }
                  );
                } else {
                  this.isShowingSpritList = false;
                  this.noActiveSprint = true;
                  this.stateService.setsprintid(0);
                }
              }
            } else {
              this.isDisabledProject = !this.isDisabledProject;
            }
          },
          (error) => {
            this.toastrService.show('Failed to Get Projects!', 'Error', {
              status: 'danger',
            });
          }
        );
      } else {
        this.userService.getUser(this.currentUser.userId).subscribe(
          (data) => {
            if (data) {
              this.currentUser = data;
              this.projects = this.currentUser.projects;
              if (this.projects) {
                this.selectedProject = this.projects[0].id;
                this.selectedProjectName = this.projects[0].name
                this.stateService.setprojectid(this.selectedProject);
                if (this.selectedProject) {
                  this.sprintService.getActiveSprintByProject(this.selectedProject).subscribe(
                    (activeSprintData) => {
                      if (activeSprintData) {
                        this.sprintList.push(activeSprintData);
                        if (this.sprintList[0].length !== 0) {
                          this.isShowingSpritList = true;
                          this.noActiveSprint = false;
                          this.selectedSprint = this.sprintList[0][0].id;
                          this.selectedSprintName = this.sprintList[0][0].name;
                          this.stateService.setsprintid(this.selectedSprint);
                        }
                      } else {
                        this.isShowingSpritList = false;
                        this.noActiveSprint = true;
                        this.stateService.setsprintid(0);
                      }
                    }
                  );
                } else {
                  this.isShowingSpritList = false;
                  this.noActiveSprint = true;
                  this.stateService.setsprintid(0);
                }
              }
            } else {
              this.isDisabledProject = !this.isDisabledProject;
            }
          },
          (error) => {
            this.toastrService.show('Failed to Get Projects!', 'Error', {
              status: 'danger',
            });
          }
        );
      }
    });
  }

  get isAdmin() {
    return this.currentUser.roles.find(
      (element) => element === Role.ROLE_ADMINISTRATOR
    );
  }


  onChange(value, id, name) {
    this.selectedProjectName = name;
    this.selectedProject = id;
    console.log(this.selectedProject)
    this.stateService.setprojectid(this.selectedProject);
    if (this.selectedProject) {
      this.sprintService.getActiveSprintByProject(this.selectedProject).subscribe(
        (data) => {
          if (data.length !== 0) {
              this.isShowingSpritList = true;
              this.noActiveSprint = false;
              this.selectedSprint = data[0].id;
              this.selectedSprintName = data[0].name;
              this.stateService.setsprintid(this.selectedSprint);
          } else {
            this.isShowingSpritList = false;
            this.noActiveSprint = true;
            this.stateService.setsprintid(0);
          }
        }
      );
    } else {
      this.isShowingSpritList = false;
      this.noActiveSprint = true;
      this.stateService.setsprintid(0);
    }
  }

  onSprintChange(value, id, name) {
    this.selectedSprintName = name;
    this.selectedSprint = id;
    this.stateService.setsprintid(this.selectedSprint);
  }

  logout() {
    this.authenticationService.logout();
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}

import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuard } from './auth/auth-guard.guard';
import { UserRegistrationComponent } from './components/admin/user-registration/user-registration.component';
import { LoginComponent } from './components/login/login.component';
import { LoginRegisterComponent } from './components/login-register/login-register.component';
import { EpicComponent } from './components/user/epic/epic.component';
import { ProfileComponent } from './components/user/profile/profile.component';
import { ProjectComponent } from './components/user/project/project.component';

import { ProjectCreationComponent } from './components/user/project-creation/project-creation.component';

import { UpdateProjectComponent } from './components/user/update-project/update-project.component';
import { UserListComponent } from './components/admin/user-list/user-list.component';
import { EpicCreationComponent } from './components/user/epic-creation/epic-creation.component';
import { EpicUpdateComponent } from './components/user/epic-update/epic-update.component';
import { SprintComponent } from './components/user/sprint/sprint.component';
import { TaskComponent } from './components/user/task/task.component';
import { SprintCreationComponent } from './components/user/sprint-creation/sprint-creation.component';
import { UpdateSprintComponent } from './components/user/update-sprint/update-sprint.component';
import { UpdateTaskComponent } from './components/user/update-task/update-task.component';
import { UserstoryComponent } from './components/user/userstory/userstory.component';
import { UserstoryCreationComponent } from './components/user/userstory-creation/userstory-creation.component';
import { UserstoryUpdateComponent } from './components/user/userstory-update/userstory-update.component';
import { TaskCreationComponent } from './components/user/task-creation/task-creation.component';
import { LandingPageComponent } from './components/landing-page/landing-page.component';
import { CreateIssueComponent } from './components/user/create-issue/create-issue.component';
import { UpdateIssueComponent } from './components/user/update-issue/update-issue.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { IssueComponent } from './components/user/issue/issue.component';
// import { MainComponent } from './main/main.component';
import { Role } from './entity/role';
import { UpdatePasswordComponent } from './components/user/update-password/update-password.component';
import { ProjectUserComponent } from './components/user/project-user/project-user.component';
import { BacklogComponent } from './components/user/backlog/backlog.component';
import { BurndownChartComponent } from './components/burndown-chart/burndown-chart.component';
import { DefectTrendComponent } from './components/defect-trend/defect-trend.component';
import { SprintOverVelocityComponent } from './components/sprint-over-velocity/sprint-over-velocity.component';
import { MainComponent } from './main/main.component';
import { RiskAnalysisComponent } from './components/risk-analysis/risk-analysis.component';


const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'login-register',
    component: LoginRegisterComponent,
  },
  {
    path: '',
    redirectTo: '/login',
    pathMatch: 'full'
  },
  {
    path: '', component: MainComponent,
    children: [
      {
        path: 'user-profile/:id',
        component: ProfileComponent,
        canActivate: [AuthGuard],
        data: { roles: [Role.ROLE_ADMINISTRATOR] }
      },
      {
        path: 'profile',
        component: ProfileComponent,
        canActivate: [AuthGuard],
        data: { roles: [Role.ROLE_ADMINISTRATOR, Role.ROLE_DEVELOPER, Role.ROLE_PROJECT_MANAGER, Role.ROLE_TESTER] }
      },
      {
        path: 'register',
        component: UserRegistrationComponent,
        canActivate: [AuthGuard],
        data: { roles: [Role.ROLE_ADMINISTRATOR] }
      },

      {
        path: 'user-list',
        component: UserListComponent,
        canActivate: [AuthGuard],
        data: { roles: [Role.ROLE_ADMINISTRATOR] }
      },
      {
        path: 'projects',
        component: ProjectComponent,
        canActivate: [AuthGuard],
        data: { roles: [Role.ROLE_ADMINISTRATOR, Role.ROLE_DEVELOPER, Role.ROLE_PROJECT_MANAGER, Role.ROLE_TESTER] }
      },
      {
        path: 'update-projects/:id',
        component: UpdateProjectComponent,
        canActivate: [AuthGuard],
        data: { roles: [Role.ROLE_ADMINISTRATOR, Role.ROLE_DEVELOPER, Role.ROLE_PROJECT_MANAGER, Role.ROLE_TESTER] }
      },
      {
        path: 'epics',
        component: EpicComponent,
        canActivate: [AuthGuard],
        data: { roles: [Role.ROLE_ADMINISTRATOR, Role.ROLE_DEVELOPER, Role.ROLE_PROJECT_MANAGER, Role.ROLE_TESTER] }
      },
      {
        path: 'createprojects',
        component: ProjectCreationComponent,
        canActivate: [AuthGuard],
        data: { roles: [Role.ROLE_ADMINISTRATOR, Role.ROLE_PROJECT_MANAGER] }
      },
      {
        path: 'epic-creation/:id',
        component: EpicCreationComponent,
        canActivate: [AuthGuard],
        data: { roles: [Role.ROLE_ADMINISTRATOR, Role.ROLE_DEVELOPER, Role.ROLE_PROJECT_MANAGER, Role.ROLE_TESTER] }
      },
      {

        path: 'epic-update/:id',
        component: EpicUpdateComponent,
        canActivate: [AuthGuard],
        data: { roles: [Role.ROLE_ADMINISTRATOR, Role.ROLE_DEVELOPER, Role.ROLE_PROJECT_MANAGER, Role.ROLE_TESTER] }
      },
      {
        path: 'sprint',
        component: SprintComponent,
        canActivate: [AuthGuard],
        data: { roles: [Role.ROLE_ADMINISTRATOR, Role.ROLE_DEVELOPER, Role.ROLE_PROJECT_MANAGER, Role.ROLE_TESTER] }
      },
      {
        path: 'task',
        component: TaskComponent,
        canActivate: [AuthGuard],
        data: { roles: [Role.ROLE_ADMINISTRATOR, Role.ROLE_DEVELOPER, Role.ROLE_PROJECT_MANAGER, Role.ROLE_TESTER] }
      },
      {
        path: 'sprint-creation/:id',
        component: SprintCreationComponent,
        canActivate: [AuthGuard],
        data: { roles: [Role.ROLE_PROJECT_MANAGER,Role.ROLE_ADMINISTRATOR] }
      },
      {
        path: 'update-sprint/:id',
        component: UpdateSprintComponent,
        canActivate: [AuthGuard],
        data: { roles: [Role.ROLE_ADMINISTRATOR, Role.ROLE_DEVELOPER, Role.ROLE_PROJECT_MANAGER, Role.ROLE_TESTER] }
      },
      {
        path: 'update-task/:id',
        component: UpdateTaskComponent,
        canActivate: [AuthGuard],
        data: { roles: [Role.ROLE_ADMINISTRATOR, Role.ROLE_DEVELOPER, Role.ROLE_PROJECT_MANAGER, Role.ROLE_TESTER] }
      },
      {
        path: 'user-stories',
        component: UserstoryComponent,
        canActivate: [AuthGuard],
        data: { roles: [Role.ROLE_ADMINISTRATOR, Role.ROLE_DEVELOPER, Role.ROLE_PROJECT_MANAGER, Role.ROLE_TESTER] }
      },
      {
        path: 'userStory-creation',
        component: UserstoryCreationComponent,
        canActivate: [AuthGuard],
        data: { roles: [Role.ROLE_ADMINISTRATOR, Role.ROLE_DEVELOPER, Role.ROLE_PROJECT_MANAGER, Role.ROLE_TESTER] }
      },
      {
        path: 'userStory-update/:id',
        component: UserstoryUpdateComponent,
        canActivate: [AuthGuard],
        data: { roles: [Role.ROLE_ADMINISTRATOR, Role.ROLE_DEVELOPER, Role.ROLE_PROJECT_MANAGER, Role.ROLE_TESTER] }
      },
      {
        path: 'task-creation',
        component: TaskCreationComponent,
        canActivate: [AuthGuard],
        data: { roles: [Role.ROLE_ADMINISTRATOR, Role.ROLE_DEVELOPER, Role.ROLE_PROJECT_MANAGER, Role.ROLE_TESTER] }
      },
      {
        path: 'landing-page',
        component: LandingPageComponent,
        canActivate: [AuthGuard],
        data: { roles: [Role.ROLE_ADMINISTRATOR, Role.ROLE_DEVELOPER, Role.ROLE_PROJECT_MANAGER, Role.ROLE_TESTER] }
      },
      {
        path: 'create-issue/:id',
        component: CreateIssueComponent,
        canActivate: [AuthGuard],
        data: { roles: [Role.ROLE_ADMINISTRATOR, Role.ROLE_DEVELOPER, Role.ROLE_PROJECT_MANAGER, Role.ROLE_TESTER] }
      },
      {
        path: 'update-issue/:id',
        component: UpdateIssueComponent,
        canActivate: [AuthGuard],
        data: { roles: [Role.ROLE_ADMINISTRATOR, Role.ROLE_DEVELOPER, Role.ROLE_PROJECT_MANAGER, Role.ROLE_TESTER] }
      },
      {
        path: 'dashboard',
        component: DashboardComponent,
        canActivate: [AuthGuard],
        data: { roles: [Role.ROLE_ADMINISTRATOR, Role.ROLE_DEVELOPER, Role.ROLE_PROJECT_MANAGER, Role.ROLE_TESTER] }
      },
      {
        path: 'issue',
        component: IssueComponent,
        canActivate: [AuthGuard],
        data: { roles: [Role.ROLE_ADMINISTRATOR, Role.ROLE_DEVELOPER, Role.ROLE_PROJECT_MANAGER, Role.ROLE_TESTER] }
      },
      {
        path: 'profile/:id/update-password',
        component: UpdatePasswordComponent,
        canActivate: [AuthGuard],
        data: { roles: [Role.ROLE_ADMINISTRATOR, Role.ROLE_DEVELOPER, Role.ROLE_PROJECT_MANAGER, Role.ROLE_TESTER] }
      },
      {
        path: 'profile/update-password',
        component: UpdatePasswordComponent,
        canActivate: [AuthGuard],
        data: { roles: [Role.ROLE_ADMINISTRATOR, Role.ROLE_DEVELOPER, Role.ROLE_PROJECT_MANAGER, Role.ROLE_TESTER] }
      },
      {
        path: 'project-user/:id',
        component: ProjectUserComponent,
        canActivate: [AuthGuard],
        data: { roles: [Role.ROLE_ADMINISTRATOR, Role.ROLE_DEVELOPER, Role.ROLE_PROJECT_MANAGER, Role.ROLE_TESTER] }
      },
      {
        path: 'backlog',
        component: BacklogComponent,
        canActivate: [AuthGuard],
        data: { roles: [Role.ROLE_ADMINISTRATOR, Role.ROLE_DEVELOPER, Role.ROLE_PROJECT_MANAGER, Role.ROLE_TESTER] }

      },
      {
        path: 'burndownChart',
        component: BurndownChartComponent,
        canActivate: [AuthGuard],
        data: { roles: [Role.ROLE_ADMINISTRATOR, Role.ROLE_DEVELOPER, Role.ROLE_PROJECT_MANAGER, Role.ROLE_TESTER] }
      },
      {
        path: 'defect-trend',
        component: DefectTrendComponent,
        canActivate: [AuthGuard],
        data: { roles: [Role.ROLE_ADMINISTRATOR, Role.ROLE_DEVELOPER, Role.ROLE_PROJECT_MANAGER, Role.ROLE_TESTER] }
      },
      {
        path: 'sprint-over-velocity',
        component: SprintOverVelocityComponent,
        canActivate: [AuthGuard],
        data: { roles: [Role.ROLE_ADMINISTRATOR, Role.ROLE_DEVELOPER, Role.ROLE_PROJECT_MANAGER, Role.ROLE_TESTER] }
      },
      {
        path: 'risk-analysis',
        component: RiskAnalysisComponent,
        canActivate: [AuthGuard],
        data: { roles: [Role.ROLE_ADMINISTRATOR, Role.ROLE_DEVELOPER, Role.ROLE_PROJECT_MANAGER, Role.ROLE_TESTER] }
      }
    ]
  }];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }


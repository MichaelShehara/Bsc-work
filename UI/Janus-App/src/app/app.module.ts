import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { JwtInterceptor } from './auth/jwt.interceptor';
import { ErrorInterceptor } from './auth/error.interceptor';


import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { MainComponent } from './main/main.component';
import { LoginComponent } from './components/login/login.component';
import { LoginRegisterComponent } from './components/login-register/login-register.component';
import { ProfileComponent } from './components/user/profile/profile.component';
import { UserRegistrationComponent } from './components/admin/user-registration/user-registration.component';
import { UpdatePasswordComponent } from './components/user/update-password/update-password.component';
import { HeaderComponent } from './shared/header/header.component';
import { SidebarComponent } from './shared/sidebar/sidebar.component';
import { FooterComponent } from './shared/footer/footer.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { LandingPageComponent } from './components/landing-page/landing-page.component';
import { ProjectComponent } from './components/user/project/project.component';
import { EpicComponent } from './components/user/epic/epic.component';
import { IssueComponent } from './components/user/issue/issue.component';
import { TaskComponent } from './components/user/task/task.component';
import { SprintComponent } from './components/user/sprint/sprint.component';
import { EpicCreationComponent } from './components/user/epic-creation/epic-creation.component';
import { ProjectCreationComponent } from './components/user/project-creation/project-creation.component';
import { ProjectUserComponent } from './components/user/project-user/project-user.component';
import { UpdateProjectComponent } from './components/user/update-project/update-project.component';
import { UserListComponent } from './components/admin/user-list/user-list.component';
import { EpicUpdateComponent } from './components/user/epic-update/epic-update.component';
import { SprintCreationComponent } from './components/user/sprint-creation/sprint-creation.component';
import { UpdateSprintComponent } from './components/user/update-sprint/update-sprint.component';
import { UpdateTaskComponent } from './components/user/update-task/update-task.component';
import { UserstoryCreationComponent } from './components/user/userstory-creation/userstory-creation.component';
import { UserstoryUpdateComponent } from './components/user/userstory-update/userstory-update.component';
import { TaskCreationComponent } from './components/user/task-creation/task-creation.component';
import { UserstoryComponent } from './components/user/userstory/userstory.component';
import { CreateIssueComponent } from './components/user/create-issue/create-issue.component';
import { UpdateIssueComponent } from './components/user/update-issue/update-issue.component';
import { SelectFilterPipe } from './pipes/select-filter.pipe';
import { DateTimeFormatPipe } from './pipes/date-time-format.pipe';
import { FormValidationCommonComponent } from './components/user/form-validation-common/form-validation-common.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';


import { RolePipe } from './pipes/role.pipe';

import {
  NbCardModule,
  NbDialogModule,
  NbLayoutModule,
  NbSpinnerModule,
  NbThemeModule,
  NbToastrModule,
  NbWindowModule,
  NbSidebarModule,
  NbTabsetModule,
  NbProgressBarModule,
} from '@nebular/theme';

import { NbEvaIconsModule } from '@nebular/eva-icons';
import { NgMultiSelectDropDownModule } from 'ng-multiselect-dropdown';
import { BacklogComponent } from './components/user/backlog/backlog.component';
import { BurndownChartComponent } from './components/burndown-chart/burndown-chart.component';
import { DefectTrendComponent } from './components/defect-trend/defect-trend.component';
import { SprintOverVelocityComponent } from './components/sprint-over-velocity/sprint-over-velocity.component';
import { ChartsModule, ThemeService } from 'ng2-charts';
import { RiskAnalysisComponent } from './components/risk-analysis/risk-analysis.component';
import { NgxSpinnerModule } from 'ngx-spinner';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { InterceptorService } from './service/interceptor.service';
import { MatProgressSpinnerModule } from '@angular/material';


@NgModule({
  declarations: [
    RolePipe,
    SelectFilterPipe,
    DateTimeFormatPipe,
    AppComponent,
    HeaderComponent,
    SidebarComponent,
    FooterComponent,
    MainComponent,
    LandingPageComponent,
    DashboardComponent,
    LoginComponent,
    ProfileComponent,
    UserRegistrationComponent,
    UpdatePasswordComponent,
    UserListComponent,
    ProjectComponent,
    ProjectUserComponent,
    ProjectCreationComponent,
    UpdateProjectComponent,
    EpicComponent,
    EpicCreationComponent,
    EpicUpdateComponent,
    SprintComponent,
    SprintCreationComponent,
    UpdateSprintComponent,
    TaskComponent,
    TaskCreationComponent,
    UpdateTaskComponent,
    UserstoryComponent,
    UserstoryCreationComponent,
    UserstoryUpdateComponent,
    IssueComponent,
    CreateIssueComponent,
    UpdateIssueComponent,
    FormValidationCommonComponent,
    DashboardComponent,
    MainComponent,
    ProjectUserComponent,
    BacklogComponent,
    BurndownChartComponent,
    DefectTrendComponent,
    SprintOverVelocityComponent,
    RiskAnalysisComponent,
    LoginRegisterComponent,
    
  ],

  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    NbLayoutModule,
    NbEvaIconsModule,
    NbTabsetModule,
    NbSpinnerModule,
    NbProgressBarModule,
    NbThemeModule.forRoot({ name: 'default' }),
    NgMultiSelectDropDownModule.forRoot(),
    NbToastrModule.forRoot(),
    NbDialogModule.forRoot(),
    NbWindowModule.forRoot(), 
    AppRoutingModule,
    NbCardModule,
    ChartsModule,
    NgxSpinnerModule,
    MatProgressBarModule,
    MatProgressSpinnerModule
  ],

  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: InterceptorService, multi: true },
    { provide: 'Window', useFactory: getWindow },
    ThemeService,
  ],

  bootstrap: [AppComponent],
})



export class AppModule { }

export function getWindow() {
  return (typeof window !== 'undefined') ? window : null;
}


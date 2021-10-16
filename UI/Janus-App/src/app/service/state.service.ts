import { Injectable, OnDestroy } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
@Injectable({
  providedIn: 'root',
})
export class StateService implements OnDestroy{
  constructor() { }
  

  private roles = new BehaviorSubject<[]>([]);
  private projectidSource = new BehaviorSubject<Number>(null);
  private projectidNameSource = new BehaviorSubject<String>(null);
  private sprintidSource = new BehaviorSubject<Number>(null);
  private epicidSource = new BehaviorSubject<Number>(new Number());
  private taskidSource = new BehaviorSubject<Number>(new Number());
  private issueidSource = new BehaviorSubject<Number>(new Number());
  private storyidSource = new BehaviorSubject<Number>(new Number());


  userRoles = this.roles.asObservable();
  selectedProjectId = this.projectidSource.asObservable();
  selectedProjectName = this.projectidNameSource.asObservable();
  selectedSprintId = this.sprintidSource.asObservable();
  selectedEpicId = this.epicidSource.asObservable();
  selectedTaskId = this.taskidSource.asObservable();
  selectedIssueId = this.issueidSource.asObservable();
  selectedStoryId = this.storyidSource.asObservable();


  setUserRoles(roles) { this.roles.next(roles) }
  setprojectid(projectid) { this.projectidSource.next(projectid) }
  setprojectname(projectname) { this.projectidNameSource.next(projectname) }
  setsprintid(sprintid) { this.sprintidSource.next(sprintid) }
  setepicid(epicid) { this.epicidSource.next(epicid) }
  settaskid(taskid) { this.taskidSource.next(taskid) }
  setissueid(issueid) { this.issueidSource.next(issueid) }
  setstoryid(storyid) { this.storyidSource.next(storyid) }

  ngOnDestroy(): void {
   this.projectidSource.next(null);
   this.projectidSource.complete();
  }
}

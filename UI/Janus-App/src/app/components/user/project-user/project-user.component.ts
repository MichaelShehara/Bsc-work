import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Project } from 'src/app/entity/project';
import { ProjectService } from 'src/app/service/project.service';
import { ActivatedRoute, Router } from '@angular/router';
import { StateService } from 'src/app/service/state.service';
import { User } from 'src/app/entity/user';
import { ProjectReleasesService } from 'src/app/service/project-releases.service';
import { ProjectRelease } from 'src/app/entity/project_release';


@Component({
  selector: 'app-project-user',
  templateUrl: './project-user.component.html',
  styleUrls: ['./project-user.component.css'],
})
export class ProjectUserComponent implements OnInit {
  user: User[];
  projectRelease: ProjectRelease = new ProjectRelease();
  project: Project = new Project();
  id: number;

  constructor(
    private projectService: ProjectService,
    private route: ActivatedRoute,
    private router: Router,
    public stateService: StateService,
    private projectReleasesService: ProjectReleasesService,
  ) {}

  ngOnInit() {
    this.id = Number(this.route.snapshot.params.id);
    this.getProjectByID(this.id);
    this.getProjectReleaseDetails(this.id);
  }

  getProjectByID(id) {
    this.projectService.getByID(id).subscribe((data) => {
      this.project = data;
      console.log(this.project)
    });
  }

  getProjectReleaseDetails(proId) {
    this.projectReleasesService.getByID(proId).subscribe((data) => {
      if (data && data !== undefined && data !== null) {
        this.projectRelease = data;
      }
    });
  }

  close() {
    this.router.navigate(['/projects']);
  }
}

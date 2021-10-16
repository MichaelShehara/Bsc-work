import { TestBed } from '@angular/core/testing';

import { ProjectReleasesService } from './project-releases.service';

describe('ProjectReleasesService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ProjectReleasesService = TestBed.get(ProjectReleasesService);
    expect(service).toBeTruthy();
  });
});

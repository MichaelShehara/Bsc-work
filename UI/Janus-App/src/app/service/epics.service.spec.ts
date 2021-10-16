import { TestBed } from '@angular/core/testing';

import { EpicsService } from './epics.service';

describe('EpicsService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: EpicsService = TestBed.get(EpicsService);
    expect(service).toBeTruthy();
  });
});

import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SprintOverVelocityComponent } from './sprint-over-velocity.component';

describe('SprintOverVelocityComponent', () => {
  let component: SprintOverVelocityComponent;
  let fixture: ComponentFixture<SprintOverVelocityComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SprintOverVelocityComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SprintOverVelocityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

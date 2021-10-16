import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserstoryCreationComponent } from './userstory-creation.component';

describe('UserstoryCreationComponent', () => {
  let component: UserstoryCreationComponent;
  let fixture: ComponentFixture<UserstoryCreationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserstoryCreationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserstoryCreationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

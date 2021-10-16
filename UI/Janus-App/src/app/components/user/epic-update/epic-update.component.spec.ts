import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EpicUpdateComponent } from './epic-update.component';

describe('EpicUpdateComponent', () => {
  let component: EpicUpdateComponent;
  let fixture: ComponentFixture<EpicUpdateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EpicUpdateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EpicUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

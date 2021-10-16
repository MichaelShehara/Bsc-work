import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DefectTrendComponent } from './defect-trend.component';

describe('DefectTrendComponent', () => {
  let component: DefectTrendComponent;
  let fixture: ComponentFixture<DefectTrendComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DefectTrendComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DefectTrendComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

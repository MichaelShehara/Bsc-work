import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FormValidationCommonComponent } from './form-validation-common.component';

describe('FormValidationCommonComponent', () => {
  let component: FormValidationCommonComponent;
  let fixture: ComponentFixture<FormValidationCommonComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FormValidationCommonComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FormValidationCommonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

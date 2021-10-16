import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-form-validation-common',
  templateUrl: './form-validation-common.component.html',
  styleUrls: ['./form-validation-common.component.css']
})
export class FormValidationCommonComponent {
  @Input() errorMsg: string;
  @Input() displayError: boolean;
}

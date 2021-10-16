import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Sprint } from 'src/app/entity/sprint';
import { SprintService } from 'src/app/service/sprint.service';
import { first } from 'rxjs/operators';
import { Project } from 'src/app/entity/project';
import { ProjectService } from 'src/app/service/project.service';
import { NbToastrService } from '@nebular/theme';
import * as moment from 'moment';

@Component({
  selector: 'app-sprint-creation',
  templateUrl: './sprint-creation.component.html',
  styleUrls: ['./sprint-creation.component.css']
})
export class SprintCreationComponent implements OnInit {
  proid: number;
  projects: Project[];
  minDate = new Date();
  // sprint: Sprint = new Sprint();
  sprint: Sprint = {
    name: '',
    status: '0',
    startAt: null,
    endAt: null,
    goals: '',

  }

  sprintForm: FormGroup;


  submitted = false;

  constructor(
    private formBuilder: FormBuilder,
    private sprintService: SprintService,
    private router: Router,
    private route: ActivatedRoute,
    private projectService: ProjectService,
    private toastrService: NbToastrService
  ) {}

  ngOnInit() {
    this.projectService.getAll().subscribe(data => this.projects = data);
    this.proid = this.route.snapshot.params.id;

    this.sprintForm = this.formBuilder.group({
      name: ['', Validators.required],
      st_date: ['', Validators.required],
      e_date: ['', Validators.required],
      goal: ['', Validators.required],

    });
  }

  // convenience getter for easy access to form fields
  get f() { return this.sprintForm.controls; }

  saveSprint(): void {
    const data = {
      name: this.sprint.name,
      status: this.sprint.status,
      startAt: this.sprint.startAt,
      endAt: this.sprint.endAt,
      goals: this.sprint.goals,

    }

    this.sprintService.createSprint(data, this.proid).subscribe(
      response => {
        console.log(response);
        this.submitted = true;
      }
    )

  }
  // check the validity in feilds entered
  isFieldValid(field: string) {
    return !this.sprintForm.get(field).valid && this.sprintForm.get(field).touched;
  }
  // css changes that occur when entered feilds are wrong
  displayFieldCss(field: string) {
    return {
      'has-error': this.isFieldValid(field),
      'has-feedback': this.isFieldValid(field)
    };
  }
  // validate the feilds
  validateAllFormFields(formGroup: FormGroup) {
    Object.keys(formGroup.controls).forEach(field => {
      console.log(field);
      const control = formGroup.get(field);
      if (control instanceof FormControl) {
        control.markAsTouched({ onlySelf: true });
      } else if (control instanceof FormGroup) {
        this.validateAllFormFields(control);
      }
    });
  }


  // cancle button
  cancel() {
    this.router.navigate(['/sprint']);
  }


  onSubmit() {
    if (this.sprintForm.valid) {
      this.saveSprint();
      this.toastrService.success(status || '', `Successfully added sprint!`);
      this.router.navigate(['/sprint']);
    } else {
      this.validateAllFormFields(this.sprintForm);
      this.toastrService.danger(status || '', `Failed to add sprint!`);
    }
  }

  dateValidation(){
    var today = moment().format('YYYY-MM-DD');
    return today;
  }


}

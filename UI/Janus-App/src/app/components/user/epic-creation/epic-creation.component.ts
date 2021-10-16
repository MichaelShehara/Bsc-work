import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Epic } from 'src/app/entity/epic';
import { Project } from 'src/app/entity/project';
import { EpicsService } from 'src/app/service/epics.service';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '../../../entity/user';
import { UserService } from '../../../service/user.service';
import { NbToastrService } from '@nebular/theme';
@Component({
  selector: 'app-epic-creation',
  templateUrl: './epic-creation.component.html',
  styleUrls: ['./epic-creation.component.css'],
})
export class EpicCreationComponent implements OnInit {

  id: number;
  user: User[];
  epicForm: FormGroup;


  epic: Epic = {
    name: '',
    summary: '',
    description: '',
    status: '0',
    priority: '',
    assignee: null,

  }
  submitted = false;

  constructor(
    private epicService: EpicsService,
    private route: ActivatedRoute,
    private userService: UserService,
    private formBuilder: FormBuilder,
    private router: Router,
    private toastrService: NbToastrService,
  ) { }

  ngOnInit(): void {
    this.userService.getUserList().subscribe(data => this.user = data);
    this.id = this.route.snapshot.params.id;

    this.epicForm = this.formBuilder.group({
      name: ['', Validators.required],
      summary: ['', Validators.required],
      description: ['', Validators.required],
      priority: ['', Validators.required],
      assignee: ['', Validators.required]
    });
  }

  saveEpic(): void {
    const data = {
      name: this.epic.name,
      summary: this.epic.summary,
      description: this.epic.description,
      status: this.epic.status,
      priority: this.epic.priority,
      assignee: this.epic.assignee
    }

    this.epicService.createEpic(data, this.id).subscribe(
      response => {
        console.log(response);
        this.submitted = true;
      }
    )
  }

  // check the validity in feilds entered
  isFieldValid(field: string) {
    return !this.epicForm.get(field).valid && this.epicForm.get(field).touched;
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
  // on trigerring the cancel button the form resets
  reset() {
    this.router.navigate(['/epics']);
  }
  // on trigerring the create button the form validates and then checks the errors and if no
  // error the form create the project
  onSubmit() {
    if (this.epicForm.valid) {
      console.log(this.epic);
      this.saveEpic();
      this.toastrService.success(status || '', `Successfully added epic!`);
       this.router.navigate(['/epics']);
    } else {
      this.validateAllFormFields(this.epicForm);
      this.toastrService.danger(status || '', `Failed to add epic!`);
    }
  }
}

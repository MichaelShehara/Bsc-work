import { Component, OnInit } from '@angular/core';
import { Epic } from '../../../entity/epic';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { EpicsService } from '../../../service/epics.service';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '../../../entity/user';
import { UserService } from '../../../service/user.service';
import { Project } from '../../../entity/project';
import { StateService } from '../../../service/state.service';
import { ProjectService } from '../../../service/project.service';
import { NbToastrService } from '@nebular/theme';
@Component({
  selector: 'app-epic-update',
  templateUrl: './epic-update.component.html',
  styleUrls: ['./epic-update.component.css']
})
export class EpicUpdateComponent implements OnInit {
  user: User[];
  assigneeId: any;
  epic: Epic = new Epic();
  epicEntity: Epic = new Epic();
  epicForm: FormGroup
  id: number
  id2: number
  constructor(
    private epicsService: EpicsService,
    private route: ActivatedRoute,
    private formBuilder: FormBuilder,
    private router: Router,
    private userService: UserService,
    private stateService: StateService,
    private toastrService: NbToastrService,
  ) { }

  ngOnInit() {
    this.userService.getUserList().subscribe((data) => {
      this.user = data;
    });
    this.id = Number(this.route.snapshot.params.id)
    this.stateService.selectedProjectId.subscribe(data => {
      this.id2 = Number(data);
    });
    this.getEpicByID(this.id2, this.id);
    this.epicForm = this.formBuilder.group({
      name: ['', Validators.required],
      summary: ['', Validators.required],
      description: ['', Validators.required],
      status: ['', Validators.required],
      priority: ['', Validators.required],
      assignee: ['', Validators.required]
    });

  }

  onChange(value) {
    if (value) {
      this.assigneeId = value;
    }

  }
  updateEpic() {
    this.epic.id = this.epic.id;
    this.epic.name = this.epic.name;
    this.epic.priority = this.epic.priority;
    this.epic.summary = this.epic.summary;
    this.epic.status = this.epic.status;
    this.epic.priority = this.epic.priority;
    this.epic.assignee = {
      id: this.assigneeId,
    };
    console.log(this.epic);

    this.epicsService.update(this.id2, this.epic).subscribe(
      data => {
        console.log(data);
      },
      error => console.log(error));
  }

  getEpicByID(id2, id) {
    this.epicsService.getByID(id2, id).subscribe(data => {
      this.epic = data;
      this.epicForm.controls['assignee'].setValue(this.epic.assignee.id);
    });
  }

  // Validations new
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


  // on trigerring the update button the form validates and then checks the errors and if no
  // error the form create the project
  onSubmit() {
    if (this.epicForm.valid) {
      this.updateEpic();
      this.toastrService.success(status || '', `Successfully updated epic!`);
      this.router.navigate(['/epics']);
    } else {
      this.validateAllFormFields(this.epicForm);
      this.toastrService.danger(status || '', `Failed to update epic!`);
    }
  }

  cancel() {
    this.router.navigate(['/epics']);
  }

  delete() {
    if (confirm('Are you sure to delete ' + ' ' + this.epic.name + '?')) {
      this.epicsService.delete(this.id).subscribe(data => {
        this.router.navigate(['/epics']);
      });
    }
  }
}

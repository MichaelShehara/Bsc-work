import { Component, OnInit } from '@angular/core';
import { Story } from 'src/app/entity/story';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { UserstoryService } from 'src/app/service/userstory.service';
import { UserService } from 'src/app/service/user.service';
import { User } from 'src/app/entity/user';
import { Epic } from 'src/app/entity/epic';
import { NbToastrService } from '@nebular/theme';
import { ActivatedRoute, Router } from '@angular/router';
import { EpicsService } from 'src/app/service/epics.service';
import { StateService } from 'src/app/service/state.service';


@Component({
  selector: 'app-userstory-creation',
  templateUrl: './userstory-creation.component.html',
  styleUrls: ['./userstory-creation.component.css']
})
export class UserstoryCreationComponent implements OnInit {

  proID: any;
  assigneeId: any;
  angForm: FormGroup;
  epicId: number;
  id: number;
  epic: Epic[];
  user: User[];
  story: Story = {
    identifier: '',
    summary: '',
    description: '',
    priority: null,
    status: 0,
    assignee: null,
    epic: null

  };
  submitted = false;

  constructor(
    private userStoryService: UserstoryService,
    private userService: UserService,
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private toastrService: NbToastrService,
    private epicService: EpicsService,
    private stateService: StateService) { }

  ngOnInit() {
    // this.id = this.route.snapshot.params['id']
    this.id = Number(this.route.snapshot.params.id);
    this.stateService.selectedProjectId.subscribe((pid) => {
      this.proID = Number(pid);
    });
    this.epicService.getAllbyPid(this.proID).subscribe(
      (data) => {
        this.epic = data;
      },
    );
    this.userService.getUserList().subscribe(data => this.user = data);

    this.angForm = this.formBuilder.group({
      epic: ['', Validators.required],
      summary: ['', Validators.required],
      description: ['', Validators.required],
      priority: ['', Validators.required],
      type: ['', Validators.required],
      assignee: ['', Validators.required]
    });
  }

  onAssigneeChange(assignValue) {
    this.assigneeId = assignValue;
  }
  // save story function
  saveStory() {
    const data = {
      epic: {
        id: this.id
      },
      project: {
        id: this.proID
      },
      summary: this.story.summary,
      description: this.story.description,
      priority: this.story.priority,
      type: this.story.type,
      status: this.story.status,

      assignee: {
        id: this.assigneeId,
      },

    }
    this.userStoryService.create(this.id, data).subscribe(
      response => {
        console.log(response);
        this.submitted = true;
      }
    )
  }

  onChange(event) {
    this.id = event;
  }

  // check the validity in feilds entered
  isFieldValid(field: string) {
    return !this.angForm.get(field).valid && this.angForm.get(field).touched;
  }

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
  reset() {
    this.angForm.reset();
  }

  onSubmit() {
    if (this.angForm.valid) {
      console.log(this.story);
      this.saveStory();
      this.toastrService.success(status || '', `Sucessfully added userstory!`);
      this.router.navigate(['/user-stories']);

    } else {
      this.validateAllFormFields(this.angForm);
      this.toastrService.danger(status || '', `Failed to add userstory!`);
    }
  }

  cancel() {
    this.router.navigate(['/user-stories']);
  }

}

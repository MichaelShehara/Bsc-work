import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { Story } from 'src/app/entity/story';
import { Task } from 'src/app/entity/task';
import { User } from 'src/app/entity/user';
import { TaskService } from 'src/app/service/task.service';
import { UserstoryService } from 'src/app/service/userstory.service';
import { NbToastrService } from '@nebular/theme';
import { UserService } from 'src/app/service/user.service';
import { StateService } from 'src/app/service/state.service';
import * as moment from 'moment';

@Component({
  selector: 'app-task-creation',
  templateUrl: './task-creation.component.html',
  styleUrls: ['./task-creation.component.css']
})
export class TaskCreationComponent implements OnInit {

  proID: any;
  id: number;
  storyList: Story[];
  userList: User[];
  minDate = new Date();
  task: Task = {
    identifier: '',
    summary: '',
    description: '',
    type: null,
    priority: null,
    severity: null,
    status: null,
    storyPoint: null,
    completedAt: null,
    impactedArea: '',
    story: null,
    assignee: null
  }

  submitted = false;
  taskForm: FormGroup;
  constructor(
    private formBuilder: FormBuilder,
    private taskService: TaskService,
    private router: Router,
    private route: ActivatedRoute,
    private userStoryService: UserstoryService,
    private toastrService: NbToastrService,
    private userService: UserService,
    private stateService: StateService) { }

  ngOnInit() {
    this.id = Number(this.route.snapshot.params.id)
    this.stateService.selectedProjectId.subscribe((pid) => {
      this.proID = Number(pid);
      this.userStoryService.getAllbyPid(this.proID).subscribe(
        (data) => {
          if (data) {
            this.storyList = data;
          }
        },
      );
    });
    // this.userStoryService.getAllbyPid(75).subscribe(data => this.story = data);

    this.userService.getUserList().subscribe(data => this.userList = data);
    this.taskForm = this.formBuilder.group({
      story: ['', Validators.required],
      summary: ['', Validators.required],
      description: ['', Validators.required],
      priority: ['', Validators.required],
      type: ['', Validators.required],
      severity: ['', Validators.required],
      completedAt: ['', Validators.required],
      impactedArea: ['', Validators.required],
      storyPoint: ['', Validators.required],
      assignee: ['', Validators.required]

    });
  }

  createTask() {
    const data = {
      story: {
        id: this.id
      },
      project: {
        id: this.proID
      },
      summary: this.task.summary,
      description: this.task.description,
      priority: this.task.priority,
      type: this.task.type,
      severity: this.task.severity,
      completedAt: this.task.completedAt,
      status: 0,
      impactedArea: this.task.impactedArea,
      storyPoint: this.task.storyPoint,
      assignee: {
        id: this.task.assignee
      },


    }
    this.taskService.createTask(data, this.id).subscribe(
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
    return !this.taskForm.get(field).valid && this.taskForm.get(field).touched;
  }
  // css changes that occur when entered feilds are wrong
  displayFieldCss(field: string) {
    return {
      'has-error': this.isFieldValid(field),
      'has-feedback': this.isFieldValid(field)
    };
  }
  // validate the feilds
  validateAllFormFields(taskForm: FormGroup) {
    Object.keys(taskForm.controls).forEach(field => {
      console.log(field);
      const control = taskForm.get(field);
      if (control instanceof FormControl) {
        control.markAsTouched({ onlySelf: true });
      } else if (control instanceof FormGroup) {
        this.validateAllFormFields(control);
      }
    });
  }


  //cancle button
  cancel() {
    this.router.navigate(['/task']);
  }


  onSubmit() {

    if (this.taskForm.valid) {
      console.log(this.task);
      this.createTask();
      this.toastrService.success(status || '', `Successfully added task!`);
      this.router.navigate(['/task']);
    } else {
      this.validateAllFormFields(this.taskForm);
      console.log(this.task);
      this.toastrService.danger(status || '', `Failed to add task!`);
    }
  }

  dateValidation(){
    var today = moment().format('YYYY-MM-DD');
    return today;
  }

}

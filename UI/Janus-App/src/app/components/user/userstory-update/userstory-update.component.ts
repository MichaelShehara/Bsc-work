import { Component, OnInit } from '@angular/core';
import { Story } from 'src/app/entity/story';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { UserstoryService } from 'src/app/service/userstory.service';
import { UserService } from 'src/app/service/user.service';
import { User } from 'src/app/entity/user';
import { Epic } from 'src/app/entity/epic';
import { NbToastrService } from '@nebular/theme';
import { ActivatedRoute, Router } from '@angular/router';
@Component({
  selector: 'app-userstory-update',
  templateUrl: './userstory-update.component.html',
  styleUrls: ['./userstory-update.component.css']
})
export class UserstoryUpdateComponent implements OnInit {
  assigneeId: any;
  angForm: FormGroup;
  user: User[];
  epicId: number;
  id: number;
  epic: Epic;
  story: Story = new Story();
  submitted = false;

  constructor(
    private userStoryService: UserstoryService,
    private userService: UserService,
    private formBuilder: FormBuilder,
    private toastrService: NbToastrService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit() {
    this.id = this.route.snapshot.params.id

    this.userService.getUserList().subscribe(data => this.user = data);
    this.getStoryByProject(this.id);

    this.angForm = this.formBuilder.group({
      type: ['', Validators.required],
      status: ['', Validators.required],
      summary: ['', Validators.required],
      description: [''],
      priority: [''],
      assignee: ['', Validators.required],
    });

  }

  // get story by Id
  getStoryByProject(id) {
    this.userStoryService.getById(id).subscribe((story) => {
      if (story) {
        this.story = story;
        if (this.story.epic) {
          this.epicId = this.story.epic.id;
        }
      }
      this.angForm.controls['assignee'].setValue(this.story.assignee.id);
    });
  }

  onAssigneeChange(assignValue) {
    this.assigneeId = assignValue;
  }

  // update story function
  updateUserStory() {
    this.story.id = this.story.id;
    this.story.status = this.story.status;
    this.story.type = this.story.type;
    this.story.summary = this.story.summary;
    this.story.description = this.story.description;
    this.story.priority = this.story.priority;
    this.story.assignee = {
      id: this.assigneeId,
    },
      this.userStoryService.updateStory(this.epicId, this.story).subscribe(
        (data) => {
          console.log(data);
          this.submitted = true;
        },
        (error) => console.log(error)
      );
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
      const control = formGroup.get(field);
      if (control instanceof FormControl) {
        control.markAsTouched({ onlySelf: true });
      } else if (control instanceof FormGroup) {
        this.validateAllFormFields(control);
      }
    });
  }

  onSubmit() {
    if (this.angForm.valid) {
      this.updateUserStory();
      this.toastrService.success(status || '', `Successfully Updated!`);
      this.router.navigate(['/user-stories']);
    } else {
      this.validateAllFormFields(this.angForm);
      this.toastrService.danger(status || '', `Failed to Update!`);
    }
  }

  delete() {
    if (confirm("Are you sure to delete this " + " " + this.story.type + "?")) {
      this.userStoryService.delete(this.id).subscribe(data => {
        this.router.navigate(['/user-stories']);
      });
    }
  }

  //cancle button
  cancel() {
    this.router.navigate(['/user-stories']);
  }
}

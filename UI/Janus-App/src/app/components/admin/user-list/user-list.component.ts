import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ListPagination } from 'src/app/entity/listPagination';
import { Role } from 'src/app/entity/role';
import { User } from 'src/app/entity/user';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css'],
})
export class UserListComponent implements OnInit {
  // roles : Role[];
  listPagination: ListPagination;
  roles = Role;
  userList: User[];
  public currentUsers;
  pageIndex: number;
  pageSize: number;
  pageCount: any;
  filterTerm: any;
  constructor(private userService: UserService,

    private router: Router
  ) { }

  ngOnInit() {
    this.pageIndex = 0;
    this.pageSize = 10;
    this.getUserLists();
  }

  get UserRole() {
    return Role;
  }

  private getUserLists() {
    this.userService.getUserList().subscribe((data) => {
      this.userList = data.sort((a, b) => b - a);
    });
  }

  public selectUser(event: any, item: any) {

    this.currentUsers = item.fullName;
    this.router.navigate(['user-profile', item.id]);
  }

  onChange(event) {
    this.filterTerm = event.target.value
  }

  get canMoveToNextPage(): boolean {
    return this.pageIndex < this.pageCount - 1 ? true : false;
  }

  get canMoveToPreviousPage(): boolean {
    return this.pageIndex >= 1 ? true : false;
  }

  moveToNextPage() {
    if (this.canMoveToNextPage) {
      this.pageIndex++;
    }
  }

  moveToPreviousPage() {
    if (this.canMoveToPreviousPage) {
      this.pageIndex--;
    }
  }
}

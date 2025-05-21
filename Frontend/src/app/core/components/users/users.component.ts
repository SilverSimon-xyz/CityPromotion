import { Component, Input, OnInit } from '@angular/core';
import { User } from '../../interfaces/user';
import { UserService } from '../../services/user/user.service';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-users',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './users.component.html',
  styleUrl: './users.component.scss'
})
export class UsersComponent implements OnInit {
  users: User[] = [];
  userForm!: FormGroup;
  userId!: number | null;
  newUserForm?: FormGroup;

  constructor(
    private userService: UserService,
    private formBuilder: FormBuilder, 
    private route: ActivatedRoute,
    private router: Router) {

  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.userId = params.get('id') ? Number(params.get('id')) : null;
      if(this.userId) {
        this.loadUserDetails();
      } else {
        this.loadUsers();
      }
    });

    this.userForm = this.formBuilder.group({
      firstname: [''],
      lastname: [''],
      email: [''],
      password: ['']
    });
  }

  loadUsers(): void {
    this.userService.getUsers().subscribe({
      next: (data) => this.users = data,
      error: (err) => console.error('Error during loading user ', err)
    })
  }

  loadUserDetails(): void {
    this.userService.getUserById(this.userId!).subscribe({
      next: (user) => this.userForm.patchValue(user),
      error: (err) => console.error('Error during loading', err)
    });
  }

  openAddUser(): void {
    this.newUserForm = this.formBuilder.group({
      firstname: [''],
      lastname: [''],
      email: [''],
      password: [''],
      roleName: ['']
    });
  }

  addUser() {
    if(this.newUserForm?.valid) {
      this.userService.createUser(this.newUserForm.value).subscribe(() => {
        this.cancelAddUser();
        this.loadUsers();
      });
    }
  }

  cancelAddUser(): void {
    this.newUserForm = undefined;
  }

  updateUser() {
    if(this.userForm.valid) {
      this.userService.updateUser(this.userId!, this.userForm.value).subscribe(() => {
      this.router.navigate(['/users']);
      });
    }
  }

  editUser(userId: number): void {
    this.router.navigate(['/users', userId]);
  }

  cancelUpdate() {
    this.router.navigate(['/users']);
  }

  deleteUser(id: number) {
    if (confirm(`Sei sicuro di voler eliminare questo utente?`)) {
      this.userService.deleteUser(id).subscribe(() => {
        this.loadUsers();
      })
    }
  }

}

import { Component, Input, OnInit } from '@angular/core';
import { User } from '../../interfaces/user';
import { UserService } from '../../services/user/user.service';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-users',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './users.component.html',
  styleUrl: './users.component.scss'
})
export class UsersComponent implements OnInit {
  users: User[] = [];
  selectedUser?: User;
  userForm!: FormGroup;
  isEditing: boolean = false;
  isAdding: boolean = false;

  constructor(
    private userService: UserService,
    private formBuilder: FormBuilder, 
    private route: ActivatedRoute,
    private router: Router) {
    this.userForm = this.formBuilder.group({
      firstname: ['', Validators.required],
      lastname: ['', Validators.required],
      email: ['', Validators.required],
      password: ['', Validators.required],
      roleName: ['', Validators.required],
    });
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      this.isEditing = this.router.url.includes('/edit');
      this.isAdding = this.router.url.includes('/add');
      if(this.isAdding) {
        this.selectedUser = undefined;
      } else if(id) {
        this.loadUserDetails(Number(id));
      } else {
        this.loadUsers();
      }
    });
  }

  loadUsers(): void {
    this.userService.getUsers().subscribe({
      next: (response) => this.users = response,
      error: (err) => console.error('Error during loading user ', err)
    })
  }

  loadUserDetails(id: number): void {
    this.userService.getUserById(id).subscribe({
      next: (response) => {
        this.selectedUser = response;
        if(this.isEditing) {
          this.userForm.patchValue(response);
        }
      },
      error: (err) => console.error('Error during loading', err)
    });
  }

  viewUserDetails(id: number) {
    this.router.navigate(['/users', id])
  }

  addUser() {
    this.router.navigate(['/users/add'])
  }

  updateUser(id: number): void {
    this.router.navigate(['/users', id, 'edit']);
  }

  saveChanges() {
    if(this.userForm.valid) {
      if(this.isAdding) {
        this.userService.createUser(this.userForm.value).subscribe({
          next: () => this.router.navigate(['/users']),
          error: (err) => console.error('Error during creation', err)
        });
      } else if(this.selectedUser) {
        this.userService.updateUser(this.selectedUser.id, this.userForm.value).subscribe({
          next: () => this.router.navigate(['/users', this.selectedUser?.id]),
          error: (err) => console.error('Error during saving', err)
        });
      }
    }
  }

  deleteUser(id: number) {
    if (confirm(`Sei sicuro di voler eliminare questo utente?`)) {
      this.userService.deleteUser(id).subscribe(() => {
        this.loadUsers();
      })
    }
  }

  goBack(): void {
    this.router.navigate(['/users'])
  }

  goToDashboard(): void {
    this.router.navigate(['/dashboard'])
  }

}

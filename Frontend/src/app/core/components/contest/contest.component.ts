import { Component } from '@angular/core';
import { ContestService } from '../../services/contest/contest.service';
import { Contest } from '../../interfaces/contest';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-contest',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './contest.component.html',
  styleUrl: './contest.component.scss'
})
export class ContestComponent {
  contests: Contest[] = [];
  selectedContest?: Contest;
  contestForm!: FormGroup;
  isEditing: boolean = false;
  isAdding: boolean = false;
  filteredContestList: Contest[] = [];
  
  
  constructor(
    private contestService: ContestService,
    private formBuilder: FormBuilder, 
    private route: ActivatedRoute,
    private router: Router
  ) {

    this.contestForm = this.formBuilder.group({
      searchName: [''],
      name: ['', Validators.required],
      description: ['', Validators.required],
      authorFirstname: ['', Validators.required],
      authorLastname: ['', Validators.required],
      rules: ['', Validators.required],
      goal: ['', Validators.required], 
      prize: ['', Validators.required],
      deadline: ['', Validators.required], 
      active: [false, Validators.required],
    });
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      this.isEditing = this.router.url.includes('/edit');
      this.isAdding = this.router.url.includes('/add');
      if(this.isAdding) {
        this.selectedContest = undefined;
      } else if(id){
        this.loadContestDetails(Number(id));
      } else {
        this.loadContests();
      }
    });
  }

  loadContests(): void {
    this.contestService.getAllContest().subscribe({
      next: (response) => this.contests = response,
      error: (err) => console.error('Error during loading user ', err)
    })
  }

  loadContestDetails(id: number): void {
    this.contestService.getContest(id).subscribe({
      next: (response) => {
        this.selectedContest = response;
        if(this.isEditing) {
          this.contestForm.patchValue(response);
        }
      },
      error: (err) => console.error('Error during loading', err)
    });
  }

  searchContest(): void {
    const name = this.contestForm.value.searchName?.toLowerCase();
    this.contestService.searchContest(name).subscribe({
      next: (results) => {
        this.filteredContestList = results;
      },
      error: (err) => console.error('Error during search...', err)
    });
  }

  viewContestDetails(id: number) {
    this.router.navigate(['/contest', id])
  }

  addContest() {
    this.router.navigate(['/contest/add'])
  }

  updateContest(id: number) {
    this.router.navigate(['/contest', id, 'edit'])
  }


  deleteContest(id: number) {
    if (confirm(`Sei sicuro di voler eliminare questo contest?`)) {
      this.contestService.deleteContest(id).subscribe(() => {
        this.loadContests();
      })
    }
  }

  saveChanges() {
    if(this.contestForm.valid) {
      if(this.isAdding) {
        this.contestService.createContest(this.contestForm.value).subscribe({
          next: () => {
          this.router.navigate(['/contest']);
        },
        error: (err) => console.error('Error during creation...', err)
        });
      } else if(this.selectedContest){
        this.contestService.updateContest(this.selectedContest.id, this.contestForm.value).subscribe({
        next: () => { this.router.navigate(['/contest', this.selectedContest?.id]);
        },
        error: (err) => console.error('Error during creation...', err)
        });
      }
    }
  }

  goBack() {
    this.router.navigate(['/contest'])
  }

  goToDashboard(): void {
    this.router.navigate(['/dashboard'])
  }
}

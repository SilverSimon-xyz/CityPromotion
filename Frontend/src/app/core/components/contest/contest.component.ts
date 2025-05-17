import { Component } from '@angular/core';
import { ContestService } from '../../services/contest/contest.service';
import { Contest } from '../../interfaces/contest';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-contest',
  imports: [CommonModule],
  templateUrl: './contest.component.html',
  styleUrl: './contest.component.scss'
})
export class ContestComponent {
    contests: Contest[] = [];
  
    constructor(private contestService: ContestService) {}
  
    ngOnInit(): void {
      this.contestService.getAllContest().subscribe({
        next: (data) => {
          this.contests = data;
          console.log('Contest recived');
        },
        error: (error) => {
          console.error('Error during retriving of Contest', error);
        }
      }); 
    }
}

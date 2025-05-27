import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ContestParticipant } from '../../interfaces/contest.participant';
import { MediaFile } from '../../interfaces/media.file';
import { ContestService } from '../../services/contest/contest.service';
import { MediaFileService } from '../../services/media.file/media.file.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-contest.participant',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './contest.participant.component.html',
  styleUrl: './contest.participant.component.scss'
})
export class ContestParticipantComponent implements OnInit{

  participantList: ContestParticipant[] = [];
  idContest!: number;
  selectedParticipant?: ContestParticipant;
  selectedFile?: File;
  previewUrl?: string;
  isParticipate?: boolean = false;
  isValuate: boolean = false;
  quoteCriterionForm!: FormGroup;

  constructor(
    private contestService: ContestService,
    private mediaFileService: MediaFileService,
    private formBuilder: FormBuilder, 
    private route: ActivatedRoute,
    private router: Router) {

    this.quoteCriterionForm = this.formBuilder.group({
      vote: ['', [Validators.required, Validators.min(1), Validators.max(10)]],
      description: ['', [Validators.required, Validators.minLength(10)]], 
    });
  }
  
  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.idContest = params['id'];
      this.loadAllParticipant();
    })
  }

  loadAllParticipant() {
    this.contestService.getAllParticipantsContest(this.idContest).subscribe(
      response => this.participantList = response
    );
  }

  getParticipant(id: number) {
    this.contestService.getParticipant(id).subscribe(
      (participant) => {
        this.selectedParticipant = participant;
      }
    );
  }

  selectFile(event: any): void {
    const inputElement = event.target as HTMLInputElement;
    if (!inputElement.files || inputElement.files.length === 0) {
      return;
    }
    this.selectedFile = inputElement.files[0];
    const reader = new FileReader();
    reader.onload = (e) => {
      if(e.target) {
        this.previewUrl = e.target.result as string;
      }
    }
    reader.readAsDataURL(this.selectedFile);
  }

  getMediaFile(mediaFile: MediaFile) {
    this.mediaFileService.getFile(mediaFile.id).subscribe(blob => this.previewUrl = URL.createObjectURL(blob));
  }

  signUpParticipant() {
    if(this.selectedFile) {
      this.contestService.participateContest(this.idContest, this.selectedFile).subscribe(() => {
        this.isParticipate = false;
        this.router.navigate(['/contest', this?.idContest])
      });
    }
  }

  deleteContest(idParticipant: number) {
    if (confirm(`Sei sicuro?`)) {
      this.contestService.deleteParticipation(this?.idContest, idParticipant).subscribe(() => {
        this.router.navigate(['/contest', this?.idContest])
      })
    }
  }

  startEvaluation(participant: ContestParticipant) {
    this.selectedParticipant = participant;
    this.isValuate = true;
  }

  sendEvaluation() {
    if(this.quoteCriterionForm.valid && this.selectedParticipant) {
      this.contestService.evaluateParticipant(this.idContest, this.selectedParticipant?.id, this.quoteCriterionForm.value).subscribe({
        next: () => { 
          this.isValuate = false;
          this.router.navigate(['/contest', this.idContest]);
        },
        error: (err) => console.error('Error during evaluation...', err)
        }
      );
    }
  }

  endContest() {
    this.contestService.declareWinners(this.idContest).subscribe(
        () => this.router.navigate(['/contest']));
  }

  goBack() {
    this.router.navigate(['/contest'])
  }

  goToDashboard(): void {
    this.router.navigate(['/dashboard'])
  }
}

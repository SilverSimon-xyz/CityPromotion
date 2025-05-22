import { Component, OnInit } from '@angular/core';
import { PointOfInterest } from '../../interfaces/point.of.interest';
import { PoiService } from '../../services/poi/poi.service';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { FormGroup, FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-poi',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './poi.component.html',
  styleUrl: './poi.component.scss'
})
export class PoiComponent implements OnInit {
  pois: PointOfInterest[] = [];
  selectedPoi?: PointOfInterest;
  poiForm!: FormGroup;
  isEditing: boolean = false;
  isAdding: boolean = false;
  filteredPoiList: PointOfInterest[] = [];
  
  constructor(
    private poiService: PoiService,
    private formBuilder: FormBuilder, 
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.poiForm = this.formBuilder.group({
      searchName: [''],
      searchType: [''],
      name: ['', Validators.required],
      description: ['', Validators.required],
      authorFirstname: ['', Validators.required],
      authorLastname: ['', Validators.required],
      latitude: ['', Validators.required],
      longitude: ['', Validators.required], 
      type: ['', Validators.required],
      openTime: ['', Validators.required], 
      closeTime: ['', Validators.required],
    });
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      this.isEditing = this.router.url.includes('/edit');
      this.isAdding = this.router.url.includes('/add');
      if(this.isAdding) {
        this.selectedPoi = undefined;
      } else if(id) {
        this.loadPoiDetails(Number(id));
      } else {
        this.loadPois();
      }
    });
  }

  loadPois(): void {
    this.poiService.getAllPointOfInterest().subscribe({
      next: (response) => this.pois = response,
      error: (err) => console.error('Error during loading user ', err)
    })
  }

  loadPoiDetails(id: number): void {
    this.poiService.getPointOfInterest(id).subscribe({
      next: (response) => {
        this.selectedPoi = response;
        if(this.isEditing) {
          this.poiForm.patchValue(response);
        }
      },
      error: (err) => console.error('Error during loading', err)
    });
  }

  searchPois(): void {
    const name = this.poiForm.value.searchName?.toLowerCase();
    const type = this.poiForm.value.searchType;

    if (!name && !type) {
      this.filteredPoiList = []; 
      return;
    }

    this.poiService.searchPointOfIntersts(name, type).subscribe( {
      next: (results) => {
        this.filteredPoiList = results;
    },
      error: (err) => {
        console.error('Error during search...', err);
      }
    });
  }

  viewPoiDetails(id: number) {
    this.router.navigate(['/poi', id])
  }

  addPoi() {
    this.router.navigate(['/poi/add'])
  }

  updatePoi(id: number): void {
    this.router.navigate(['/poi', id, 'edit']);
  }

  deletePoi(id: number) {
    if(confirm(`Sei sicuro di voler eliminare questo punto di Interesse?`)) {
      this.poiService.deletePointOfInterest(id).subscribe(() => {
        this.loadPois();
      })
    }
  }

  saveChanges() {
    if(this.poiForm.valid) {
      if(this.isAdding) {
        this.poiService.createPointOfInterest(this.poiForm.value).subscribe({
          next: () => this.router.navigate(['/poi']),
          error: (err) => console.error('Error during creation', err)
        });
      } else if(this.selectedPoi) {
        this.poiService.updatePointOfInterest(this.selectedPoi.id, this.poiForm.value).subscribe({
          next: () => this.router.navigate(['/poi', this.selectedPoi?.id]),
          error: (err) => console.error('Error during saving', err)
        });
      }
    }
  }

  goBack() {
    this.router.navigate(['/poi'])
  }

  goToDashboard(): void {
    this.router.navigate(['/dashboard'])
  }
}

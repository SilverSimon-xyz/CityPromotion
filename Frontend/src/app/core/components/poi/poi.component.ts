import { Component, OnInit } from '@angular/core';
import { PointOfInterest } from '../../interfaces/point.of.interest';
import { PoiService } from '../../services/poi/poi.service';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { FormGroup, FormBuilder, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-poi',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './poi.component.html',
  styleUrl: './poi.component.scss'
})
export class PoiComponent implements OnInit {
  pois: PointOfInterest[] = [];
  poiForm!: FormGroup;
  poiId!: number | null;
  newPoiForm?: FormGroup;
  
  constructor(
    private poiService: PoiService,
    private formBuilder: FormBuilder, 
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.poiId = params.get('id') ? Number(params.get('id')) : null;
      if(this.poiId) {
        this.loadPoiDetails();
      } else {
        this.loadPois();
      }
    });

    this.poiForm = this.formBuilder.group({
      name: [''],
      description: [''],
      authorFirstName: [''],
      authorLastName: [''],
      latitude: [''],
      longitude: [''], 
      type: [''],
      openTime: [''], 
      closeTime: [''],
    });
  }

  loadPois(): void {
    this.poiService.getAllPointOfInterest().subscribe({
      next: (data) => this.pois = data,
      error: (err) => console.error('Error during loading user ', err)
    })
  }

  loadPoiDetails(): void {
    this.poiService.getPointOfInterest(this.poiId!).subscribe({
      next: (user) => this.poiForm.patchValue(user),
      error: (err) => console.error('Error during loading', err)
    });
  }

  openAddPoi(): void {
    this.newPoiForm = this.formBuilder.group({
      name: [''],
      description: [''],
      authorFirstname: [''],
      authorLastname: [''],
      latitude: [''],
      longitude: [''], 
      type: [''],
      openTime: [''], 
      closeTime: [''],
    });
  }

  addPoi() {
    if(this.newPoiForm?.valid) {
      this.poiService.createPointOfInterest(this.newPoiForm.value).subscribe(() => {
        this.cancelAddPoi();
        this.loadPois();
      });
    }
  }

  cancelAddPoi(): void {
    this.newPoiForm = undefined;
  }

  updatePoi() {
    if(this.poiForm.valid) {
      this.poiService.updatePointOfInterest(this.poiId!, this.poiForm.value).subscribe(() => {
      this.router.navigate(['/poi']);
      });
    }
  }

  editPoi(poiId: number): void {
    this.router.navigate(['/poi', poiId]);
  }

  cancelUpdate() {
    this.router.navigate(['/poi']);
  }

  deletePoi(id: number) {
    if (confirm(`Sei sicuro di voler eliminare questo punto di Interesse?`)) {
      this.poiService.deletePointOfInterest(id).subscribe(() => {
        this.loadPois();
      })
    }
  }
}

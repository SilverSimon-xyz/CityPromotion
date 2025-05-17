import { Component, OnInit } from '@angular/core';
import { PointOfInterest } from '../../interfaces/point.of.interest';
import { PoiService } from '../../services/poi/poi.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-poi',
  imports: [CommonModule],
  templateUrl: './poi.component.html',
  styleUrl: './poi.component.scss'
})
export class PoiComponent implements OnInit {
  pois: PointOfInterest[] = [];
  
    constructor(private poiService: PoiService) {}
  
    ngOnInit(): void {
      this.poiService.getAllPointOfInterest().subscribe({
        next: (data) => {
          this.pois = data;
          console.log('Poi recived');
        },
        error: (error) => {
          console.error('Error during retriving of poi', error);
        }
      }); 
    }
}

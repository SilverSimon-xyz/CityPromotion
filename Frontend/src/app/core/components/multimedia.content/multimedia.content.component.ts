import { Component, OnInit } from '@angular/core';
import { MultimediaContent } from '../../interfaces/multimedia.content';
import { MultimediaContentService } from '../../services/multimedia.content/multimedia.content.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-multimedia.content',
  imports: [CommonModule],
  templateUrl: './multimedia.content.component.html',
  styleUrl: './multimedia.content.component.scss'
})
export class MultimediaContentComponent implements OnInit {
  multimediaContents: MultimediaContent[] = [];
    
  constructor(private multimediaContentService: MultimediaContentService) {}
    
      ngOnInit(): void {
        this.multimediaContentService.getAllMultimediaContent().subscribe({
          next: (data) => {
            this.multimediaContents = data;
            console.log('MultimediaContent recived');
          },
          error: (error) => {
            console.error('Error during retriving of MultimediaContent', error);
          }
        }); 
      }
}

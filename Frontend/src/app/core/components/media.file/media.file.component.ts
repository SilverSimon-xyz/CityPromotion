import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { MediaFile } from '../../interfaces/media.file';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-media.file',
  imports: [CommonModule],
  templateUrl: './media.file.component.html',
  styleUrl: './media.file.component.scss'
})
export class MediaFileComponent implements OnChanges {

  @Input() mediaFile!: MediaFile;
  fileUrl: string | null = null;

  constructor() {}

  ngOnChanges(): void {
    if(this.mediaFile && this.mediaFile.data) {
      const blob = new Blob([this.mediaFile.data], {type: this.mediaFile.type});
      this.fileUrl = URL.createObjectURL(blob);
    }
  }

}

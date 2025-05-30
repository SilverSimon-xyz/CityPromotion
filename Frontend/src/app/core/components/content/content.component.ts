import { Component, OnInit } from '@angular/core';
import { Content, Status } from '../../interfaces/content';
import { ContentService } from '../../services/content/content.service';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { PoiService } from '../../services/poi/poi.service';
import { MediaFileService } from '../../services/media.file/media.file.service';
import { PointOfInterest } from '../../interfaces/point.of.interest';
import { RolePermissionService } from '../../services/role.permission/role.permission.service';

@Component({
  selector: 'app-content',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './content.component.html',
  styleUrl: './content.component.scss'
})
export class ContentComponent implements OnInit {
  contentForm!: FormGroup;
  contents: Content[] = [];
  pois: PointOfInterest[] = [];
  selectedFile?: File;
  previewUrl?: string;
  selectedContent?: Content;
  Status = Status;
  isCreatingContent?: boolean;
  isUpdateContent?: boolean;
    
  constructor(
    private contentService: ContentService,
    private poiService: PoiService,
    private mediaFileService: MediaFileService,
    private formBuilder: FormBuilder, 
    private route: ActivatedRoute,
    private router: Router,
    public rolePermission: RolePermissionService
  ) {
    this.initForm();
  }
    
  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      this.isCreatingContent = this.router.url.includes('/add');
      this.isUpdateContent = this.router.url.includes('/edit');
      if(this.isCreatingContent) {
        this.selectedContent = undefined;
      } else if(id){
        this.loadContentDetails(Number(id));
      } else if(!this.isCreatingContent) {
      this.loadContents();
    }
      this.loadAllPOI();
    });
    
  }

  initForm() {
    this.contentForm = this.formBuilder.group({
      title: ['', [Validators.required, Validators.minLength(5)]],
      content: ['', [Validators.required, Validators.minLength(10)]],
      hashtag: ['', Validators.required],
      idPoi:['', Validators.required],
    });
  }

  loadAllPOI(): void {
    this.poiService.getAllPointOfInterest().subscribe({
      next: (response) => this.pois = response,
      error: (err) => console.error('Error during loading poi ', err)
    })
  }

  loadContents(): void {
    this.contentService.getAllContent().subscribe({
      next: (response) => this.contents = response,
      error: (err) => console.error('Error during loading contents ', err)
    })
  }

  loadContentDetails(id: number): void {
    if(id) {
      this.contentService.getContentDetails(id).subscribe({
        next: (response) => {
          this.selectedContent = response;
          if(response.mediaFile) {
            this.mediaFileService.getFile(response.mediaFile.id).subscribe(blob => {
            this.previewUrl = URL.createObjectURL(blob);
        })} else if(this.isUpdateContent) {
          this.contentForm.patchValue(response);
        }
        },
        error: (err) => console.error('Error during loading contents ', err)
      })
    }
  }

  navigateToContent(id: number) {
    this.router.navigate(['/contents', id]);
  }

  createContent() {
    this.router.navigate(['/contents/add'])
  }

  updateContent(id: number) {
    this.router.navigate(['/contents', id, 'edit'])
  }

  approveContent(id: number) {
    this.contentService.validateContent(id, Status.APPROVED).subscribe( {
          next:() =>  this.router.navigate(['/contents']), 
          error: (err) => console.error('Error during validation...', err)});
  }

  rejectContent(id: number) {
    this.contentService.validateContent(id, Status.REJECTED).subscribe( {
          next:() => this.router.navigate(['/contents']), 
          error: (err) => console.error('Error during validation...', err)});
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

  saveContent() {
    if(this.contentForm.valid) {
      const request = this.contentForm.value;
      const formData = new FormData();
      formData.append('data', new Blob([JSON.stringify(request)], { type: 'application/json'}));
      if(this.selectedFile) formData.append('file', this.selectedFile);
      
      if(this.isCreatingContent) {
        this.contentService.createContent(formData).subscribe( {
          next:() =>  this.router.navigate(['/contents']), 
          error: (err) => console.error('Error during creation...', err)});
      } else if(this.selectedContent) {
        this.contentService.updateContent(this.selectedContent.id, this.contentForm.value).subscribe({
          next: () => this.router.navigate(['/contents', this.selectedContent?.id]),
          error: (err) => console.error('Error during update...', err)
        });
      } 
    }
  }

  deleteContent(id: number) {
    if(confirm(`Sei sicuro di voler eliminare questo contenuto?`)) {
      this.contentService.deleteContent(id).subscribe(() => {
        this.loadContents();
      })
    }
  }

  deleteAllRejectContent() {
    if(confirm(`Sei sicuro di voler eliminare i contenuti rigettati?`)) {
      this.contentService.deleteRejectedContent().subscribe(() => {
        this.loadContents();
      })
    }
  }

  goBack() {
    this.router.navigate(['/contents'])
  }

  goToDashboard(): void {
    this.router.navigate(['/dashboard'])
  }
}

import { TestBed } from '@angular/core/testing';

import { MediaFileService } from './media.file.service';

describe('MediaFileService', () => {
  let service: MediaFileService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MediaFileService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

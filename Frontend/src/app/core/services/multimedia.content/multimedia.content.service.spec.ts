import { TestBed } from '@angular/core/testing';
import { MultimediaContentService } from './multimedia.content.service';

describe('MultimediaContentService', () => {
  let service: MultimediaContentService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MultimediaContentService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

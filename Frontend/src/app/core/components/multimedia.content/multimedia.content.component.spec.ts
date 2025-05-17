import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MultimediaContentComponent } from './multimedia.content.component';

describe('MultimediaContentComponent', () => {
  let component: MultimediaContentComponent;
  let fixture: ComponentFixture<MultimediaContentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MultimediaContentComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MultimediaContentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

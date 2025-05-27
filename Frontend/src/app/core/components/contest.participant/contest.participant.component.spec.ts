import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ContestParticipantComponent } from './contest.participant.component';

describe('ContestParticipantComponent', () => {
  let component: ContestParticipantComponent;
  let fixture: ComponentFixture<ContestParticipantComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ContestParticipantComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ContestParticipantComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EventParticipantDetailComponent } from './event-participant-detail.component';

describe('EventParticipant Management Detail Component', () => {
  let comp: EventParticipantDetailComponent;
  let fixture: ComponentFixture<EventParticipantDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EventParticipantDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ eventParticipant: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(EventParticipantDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(EventParticipantDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load eventParticipant on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.eventParticipant).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});

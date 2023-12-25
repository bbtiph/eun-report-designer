import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EventReferencesParticipantsCategoryDetailComponent } from './event-references-participants-category-detail.component';

describe('EventReferencesParticipantsCategory Management Detail Component', () => {
  let comp: EventReferencesParticipantsCategoryDetailComponent;
  let fixture: ComponentFixture<EventReferencesParticipantsCategoryDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EventReferencesParticipantsCategoryDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ eventReferencesParticipantsCategory: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(EventReferencesParticipantsCategoryDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(EventReferencesParticipantsCategoryDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load eventReferencesParticipantsCategory on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.eventReferencesParticipantsCategory).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});

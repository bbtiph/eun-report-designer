import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EventReferencesDetailComponent } from './event-references-detail.component';

describe('EventReferences Management Detail Component', () => {
  let comp: EventReferencesDetailComponent;
  let fixture: ComponentFixture<EventReferencesDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EventReferencesDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ eventReferences: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(EventReferencesDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(EventReferencesDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load eventReferences on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.eventReferences).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});

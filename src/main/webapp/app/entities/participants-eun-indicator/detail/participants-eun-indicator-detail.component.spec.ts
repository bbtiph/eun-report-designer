import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ParticipantsEunIndicatorDetailComponent } from './participants-eun-indicator-detail.component';

describe('ParticipantsEunIndicator Management Detail Component', () => {
  let comp: ParticipantsEunIndicatorDetailComponent;
  let fixture: ComponentFixture<ParticipantsEunIndicatorDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ParticipantsEunIndicatorDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ participantsEunIndicator: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ParticipantsEunIndicatorDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ParticipantsEunIndicatorDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load participantsEunIndicator on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.participantsEunIndicator).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});

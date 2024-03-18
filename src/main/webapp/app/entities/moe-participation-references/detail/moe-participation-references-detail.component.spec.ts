import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MOEParticipationReferencesDetailComponent } from './moe-participation-references-detail.component';

describe('MOEParticipationReferences Management Detail Component', () => {
  let comp: MOEParticipationReferencesDetailComponent;
  let fixture: ComponentFixture<MOEParticipationReferencesDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MOEParticipationReferencesDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ mOEParticipationReferences: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(MOEParticipationReferencesDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(MOEParticipationReferencesDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load mOEParticipationReferences on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.mOEParticipationReferences).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});

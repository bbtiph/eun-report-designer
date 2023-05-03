import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MinistryDetailComponent } from './ministry-detail.component';

describe('Ministry Management Detail Component', () => {
  let comp: MinistryDetailComponent;
  let fixture: ComponentFixture<MinistryDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MinistryDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ ministry: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(MinistryDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(MinistryDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load ministry on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.ministry).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FundingDetailComponent } from './funding-detail.component';

describe('Funding Management Detail Component', () => {
  let comp: FundingDetailComponent;
  let fixture: ComponentFixture<FundingDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FundingDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ funding: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(FundingDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(FundingDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load funding on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.funding).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});

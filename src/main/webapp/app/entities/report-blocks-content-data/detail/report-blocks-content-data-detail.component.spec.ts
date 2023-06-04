import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ReportBlocksContentDataDetailComponent } from './report-blocks-content-data-detail.component';

describe('ReportBlocksContentData Management Detail Component', () => {
  let comp: ReportBlocksContentDataDetailComponent;
  let fixture: ComponentFixture<ReportBlocksContentDataDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ReportBlocksContentDataDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ reportBlocksContentData: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ReportBlocksContentDataDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ReportBlocksContentDataDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load reportBlocksContentData on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.reportBlocksContentData).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});

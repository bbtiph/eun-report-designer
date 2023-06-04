import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ReportBlocksContentDetailComponent } from './report-blocks-content-detail.component';

describe('ReportBlocksContent Management Detail Component', () => {
  let comp: ReportBlocksContentDetailComponent;
  let fixture: ComponentFixture<ReportBlocksContentDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ReportBlocksContentDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ reportBlocksContent: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ReportBlocksContentDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ReportBlocksContentDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load reportBlocksContent on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.reportBlocksContent).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});

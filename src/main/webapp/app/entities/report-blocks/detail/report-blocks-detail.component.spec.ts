import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ReportBlocksDetailComponent } from './report-blocks-detail.component';

describe('ReportBlocks Management Detail Component', () => {
  let comp: ReportBlocksDetailComponent;
  let fixture: ComponentFixture<ReportBlocksDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ReportBlocksDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ reportBlocks: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ReportBlocksDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ReportBlocksDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load reportBlocks on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.reportBlocks).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});

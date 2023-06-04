import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ReportBlocksContentService } from '../service/report-blocks-content.service';

import { ReportBlocksContentComponent } from './report-blocks-content.component';

describe('ReportBlocksContent Management Component', () => {
  let comp: ReportBlocksContentComponent;
  let fixture: ComponentFixture<ReportBlocksContentComponent>;
  let service: ReportBlocksContentService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'report-blocks-content', component: ReportBlocksContentComponent }]),
        HttpClientTestingModule,
      ],
      declarations: [ReportBlocksContentComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'id,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'id,desc',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(ReportBlocksContentComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ReportBlocksContentComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ReportBlocksContentService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.reportBlocksContents?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to reportBlocksContentService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getReportBlocksContentIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getReportBlocksContentIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});

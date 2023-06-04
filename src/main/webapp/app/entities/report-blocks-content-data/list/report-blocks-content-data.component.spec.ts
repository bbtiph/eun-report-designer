import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ReportBlocksContentDataService } from '../service/report-blocks-content-data.service';

import { ReportBlocksContentDataComponent } from './report-blocks-content-data.component';

describe('ReportBlocksContentData Management Component', () => {
  let comp: ReportBlocksContentDataComponent;
  let fixture: ComponentFixture<ReportBlocksContentDataComponent>;
  let service: ReportBlocksContentDataService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'report-blocks-content-data', component: ReportBlocksContentDataComponent }]),
        HttpClientTestingModule,
      ],
      declarations: [ReportBlocksContentDataComponent],
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
      .overrideTemplate(ReportBlocksContentDataComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ReportBlocksContentDataComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ReportBlocksContentDataService);

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
    expect(comp.reportBlocksContentData?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to reportBlocksContentDataService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getReportBlocksContentDataIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getReportBlocksContentDataIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});

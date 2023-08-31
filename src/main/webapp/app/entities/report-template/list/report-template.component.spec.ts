import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ReportTemplateService } from '../service/report-template.service';

import { ReportTemplateComponent } from './report-template.component';

describe('ReportTemplate Management Component', () => {
  let comp: ReportTemplateComponent;
  let fixture: ComponentFixture<ReportTemplateComponent>;
  let service: ReportTemplateService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'report-template', component: ReportTemplateComponent }]), HttpClientTestingModule],
      declarations: [ReportTemplateComponent],
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
      .overrideTemplate(ReportTemplateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ReportTemplateComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ReportTemplateService);

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
    expect(comp.reportTemplates?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to reportTemplateService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getReportTemplateIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getReportTemplateIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});

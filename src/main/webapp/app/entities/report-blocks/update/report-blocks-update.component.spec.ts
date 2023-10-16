import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ReportBlocksFormService } from './report-blocks-form.service';
import { ReportBlocksService } from '../service/report-blocks.service';
import { IReportBlocks } from '../report-blocks.model';
import { ICountries } from 'app/entities/countries/countries.model';
import { CountriesService } from 'app/entities/countries/service/countries.service';
import { IReport } from 'app/entities/report/report.model';
import { ReportService } from 'app/entities/report/service/report.service';

import { ReportBlocksUpdateComponent } from './report-blocks-update.component';

describe('ReportBlocks Management Update Component', () => {
  let comp: ReportBlocksUpdateComponent;
  let fixture: ComponentFixture<ReportBlocksUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let reportBlocksFormService: ReportBlocksFormService;
  let reportBlocksService: ReportBlocksService;
  let countriesService: CountriesService;
  let reportService: ReportService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ReportBlocksUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(ReportBlocksUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ReportBlocksUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    reportBlocksFormService = TestBed.inject(ReportBlocksFormService);
    reportBlocksService = TestBed.inject(ReportBlocksService);
    countriesService = TestBed.inject(CountriesService);
    reportService = TestBed.inject(ReportService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Countries query and add missing value', () => {
      const reportBlocks: IReportBlocks = { id: 456 };
      const countryIds: ICountries[] = [{ id: 54319 }];
      reportBlocks.countryIds = countryIds;

      const countriesCollection: ICountries[] = [{ id: 62378 }];
      jest.spyOn(countriesService, 'query').mockReturnValue(of(new HttpResponse({ body: countriesCollection })));
      const additionalCountries = [...countryIds];
      const expectedCollection: ICountries[] = [...additionalCountries, ...countriesCollection];
      jest.spyOn(countriesService, 'addCountriesToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ reportBlocks });
      comp.ngOnInit();

      expect(countriesService.query).toHaveBeenCalled();
      expect(countriesService.addCountriesToCollectionIfMissing).toHaveBeenCalledWith(
        countriesCollection,
        ...additionalCountries.map(expect.objectContaining)
      );
      expect(comp.countriesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Report query and add missing value', () => {
      const reportBlocks: IReportBlocks = { id: 456 };
      const reportIds: IReport[] = [{ id: 98678 }];
      reportBlocks.reportIds = reportIds;

      const reportCollection: IReport[] = [{ id: 53248 }];
      jest.spyOn(reportService, 'query').mockReturnValue(of(new HttpResponse({ body: reportCollection })));
      const additionalReports = [...reportIds];
      const expectedCollection: IReport[] = [...additionalReports, ...reportCollection];
      jest.spyOn(reportService, 'addReportToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ reportBlocks });
      comp.ngOnInit();

      expect(reportService.query).toHaveBeenCalled();
      expect(reportService.addReportToCollectionIfMissing).toHaveBeenCalledWith(
        reportCollection,
        ...additionalReports.map(expect.objectContaining)
      );
      expect(comp.reportsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const reportBlocks: IReportBlocks = { id: 456 };
      const countryId: ICountries = { id: 32508 };
      reportBlocks.countryIds = [countryId];
      const reportId: IReport = { id: 18079 };
      reportBlocks.reportIds = [reportId];

      activatedRoute.data = of({ reportBlocks });
      comp.ngOnInit();

      expect(comp.countriesSharedCollection).toContain(countryId);
      expect(comp.reportsSharedCollection).toContain(reportId);
      expect(comp.reportBlocks).toEqual(reportBlocks);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IReportBlocks>>();
      const reportBlocks = { id: 123 };
      jest.spyOn(reportBlocksFormService, 'getReportBlocks').mockReturnValue(reportBlocks);
      jest.spyOn(reportBlocksService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ reportBlocks });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: reportBlocks }));
      saveSubject.complete();

      // THEN
      expect(reportBlocksFormService.getReportBlocks).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(reportBlocksService.update).toHaveBeenCalledWith(expect.objectContaining(reportBlocks));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IReportBlocks>>();
      const reportBlocks = { id: 123 };
      jest.spyOn(reportBlocksFormService, 'getReportBlocks').mockReturnValue({ id: null });
      jest.spyOn(reportBlocksService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ reportBlocks: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: reportBlocks }));
      saveSubject.complete();

      // THEN
      expect(reportBlocksFormService.getReportBlocks).toHaveBeenCalled();
      expect(reportBlocksService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IReportBlocks>>();
      const reportBlocks = { id: 123 };
      jest.spyOn(reportBlocksService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ reportBlocks });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(reportBlocksService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareCountries', () => {
      it('Should forward to countriesService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(countriesService, 'compareCountries');
        comp.compareCountries(entity, entity2);
        expect(countriesService.compareCountries).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareReport', () => {
      it('Should forward to reportService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(reportService, 'compareReport');
        comp.compareReport(entity, entity2);
        expect(reportService.compareReport).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});

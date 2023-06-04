import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ReportBlocksContentDataFormService } from './report-blocks-content-data-form.service';
import { ReportBlocksContentDataService } from '../service/report-blocks-content-data.service';
import { IReportBlocksContentData } from '../report-blocks-content-data.model';
import { IReportBlocksContent } from 'app/entities/report-blocks-content/report-blocks-content.model';
import { ReportBlocksContentService } from 'app/entities/report-blocks-content/service/report-blocks-content.service';
import { ICountries } from 'app/entities/countries/countries.model';
import { CountriesService } from 'app/entities/countries/service/countries.service';

import { ReportBlocksContentDataUpdateComponent } from './report-blocks-content-data-update.component';

describe('ReportBlocksContentData Management Update Component', () => {
  let comp: ReportBlocksContentDataUpdateComponent;
  let fixture: ComponentFixture<ReportBlocksContentDataUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let reportBlocksContentDataFormService: ReportBlocksContentDataFormService;
  let reportBlocksContentDataService: ReportBlocksContentDataService;
  let reportBlocksContentService: ReportBlocksContentService;
  let countriesService: CountriesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ReportBlocksContentDataUpdateComponent],
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
      .overrideTemplate(ReportBlocksContentDataUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ReportBlocksContentDataUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    reportBlocksContentDataFormService = TestBed.inject(ReportBlocksContentDataFormService);
    reportBlocksContentDataService = TestBed.inject(ReportBlocksContentDataService);
    reportBlocksContentService = TestBed.inject(ReportBlocksContentService);
    countriesService = TestBed.inject(CountriesService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call ReportBlocksContent query and add missing value', () => {
      const reportBlocksContentData: IReportBlocksContentData = { id: 456 };
      const reportBlocksContent: IReportBlocksContent = { id: 76548 };
      reportBlocksContentData.reportBlocksContent = reportBlocksContent;

      const reportBlocksContentCollection: IReportBlocksContent[] = [{ id: 41676 }];
      jest.spyOn(reportBlocksContentService, 'query').mockReturnValue(of(new HttpResponse({ body: reportBlocksContentCollection })));
      const additionalReportBlocksContents = [reportBlocksContent];
      const expectedCollection: IReportBlocksContent[] = [...additionalReportBlocksContents, ...reportBlocksContentCollection];
      jest.spyOn(reportBlocksContentService, 'addReportBlocksContentToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ reportBlocksContentData });
      comp.ngOnInit();

      expect(reportBlocksContentService.query).toHaveBeenCalled();
      expect(reportBlocksContentService.addReportBlocksContentToCollectionIfMissing).toHaveBeenCalledWith(
        reportBlocksContentCollection,
        ...additionalReportBlocksContents.map(expect.objectContaining)
      );
      expect(comp.reportBlocksContentsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Countries query and add missing value', () => {
      const reportBlocksContentData: IReportBlocksContentData = { id: 456 };
      const country: ICountries = { id: 51442 };
      reportBlocksContentData.country = country;

      const countriesCollection: ICountries[] = [{ id: 19801 }];
      jest.spyOn(countriesService, 'query').mockReturnValue(of(new HttpResponse({ body: countriesCollection })));
      const additionalCountries = [country];
      const expectedCollection: ICountries[] = [...additionalCountries, ...countriesCollection];
      jest.spyOn(countriesService, 'addCountriesToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ reportBlocksContentData });
      comp.ngOnInit();

      expect(countriesService.query).toHaveBeenCalled();
      expect(countriesService.addCountriesToCollectionIfMissing).toHaveBeenCalledWith(
        countriesCollection,
        ...additionalCountries.map(expect.objectContaining)
      );
      expect(comp.countriesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const reportBlocksContentData: IReportBlocksContentData = { id: 456 };
      const reportBlocksContent: IReportBlocksContent = { id: 86601 };
      reportBlocksContentData.reportBlocksContent = reportBlocksContent;
      const country: ICountries = { id: 3336 };
      reportBlocksContentData.country = country;

      activatedRoute.data = of({ reportBlocksContentData });
      comp.ngOnInit();

      expect(comp.reportBlocksContentsSharedCollection).toContain(reportBlocksContent);
      expect(comp.countriesSharedCollection).toContain(country);
      expect(comp.reportBlocksContentData).toEqual(reportBlocksContentData);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IReportBlocksContentData>>();
      const reportBlocksContentData = { id: 123 };
      jest.spyOn(reportBlocksContentDataFormService, 'getReportBlocksContentData').mockReturnValue(reportBlocksContentData);
      jest.spyOn(reportBlocksContentDataService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ reportBlocksContentData });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: reportBlocksContentData }));
      saveSubject.complete();

      // THEN
      expect(reportBlocksContentDataFormService.getReportBlocksContentData).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(reportBlocksContentDataService.update).toHaveBeenCalledWith(expect.objectContaining(reportBlocksContentData));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IReportBlocksContentData>>();
      const reportBlocksContentData = { id: 123 };
      jest.spyOn(reportBlocksContentDataFormService, 'getReportBlocksContentData').mockReturnValue({ id: null });
      jest.spyOn(reportBlocksContentDataService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ reportBlocksContentData: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: reportBlocksContentData }));
      saveSubject.complete();

      // THEN
      expect(reportBlocksContentDataFormService.getReportBlocksContentData).toHaveBeenCalled();
      expect(reportBlocksContentDataService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IReportBlocksContentData>>();
      const reportBlocksContentData = { id: 123 };
      jest.spyOn(reportBlocksContentDataService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ reportBlocksContentData });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(reportBlocksContentDataService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareReportBlocksContent', () => {
      it('Should forward to reportBlocksContentService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(reportBlocksContentService, 'compareReportBlocksContent');
        comp.compareReportBlocksContent(entity, entity2);
        expect(reportBlocksContentService.compareReportBlocksContent).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareCountries', () => {
      it('Should forward to countriesService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(countriesService, 'compareCountries');
        comp.compareCountries(entity, entity2);
        expect(countriesService.compareCountries).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});

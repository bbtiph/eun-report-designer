import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ReportFormService } from './report-form.service';
import { ReportService } from '../service/report.service';
import { IReport } from '../report.model';
import { IReportTemplate } from 'app/entities/report-template/report-template.model';
import { ReportTemplateService } from 'app/entities/report-template/service/report-template.service';

import { ReportUpdateComponent } from './report-update.component';

describe('Report Management Update Component', () => {
  let comp: ReportUpdateComponent;
  let fixture: ComponentFixture<ReportUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let reportFormService: ReportFormService;
  let reportService: ReportService;
  let reportTemplateService: ReportTemplateService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ReportUpdateComponent],
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
      .overrideTemplate(ReportUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ReportUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    reportFormService = TestBed.inject(ReportFormService);
    reportService = TestBed.inject(ReportService);
    reportTemplateService = TestBed.inject(ReportTemplateService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call ReportTemplate query and add missing value', () => {
      const report: IReport = { id: 456 };
      const reportTemplate: IReportTemplate = { id: 97365 };
      report.reportTemplate = reportTemplate;

      const reportTemplateCollection: IReportTemplate[] = [{ id: 45874 }];
      jest.spyOn(reportTemplateService, 'query').mockReturnValue(of(new HttpResponse({ body: reportTemplateCollection })));
      const additionalReportTemplates = [reportTemplate];
      const expectedCollection: IReportTemplate[] = [...additionalReportTemplates, ...reportTemplateCollection];
      jest.spyOn(reportTemplateService, 'addReportTemplateToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ report });
      comp.ngOnInit();

      expect(reportTemplateService.query).toHaveBeenCalled();
      expect(reportTemplateService.addReportTemplateToCollectionIfMissing).toHaveBeenCalledWith(
        reportTemplateCollection,
        ...additionalReportTemplates.map(expect.objectContaining)
      );
      expect(comp.reportTemplatesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const report: IReport = { id: 456 };
      const reportTemplate: IReportTemplate = { id: 45135 };
      report.reportTemplate = reportTemplate;

      activatedRoute.data = of({ report });
      comp.ngOnInit();

      expect(comp.reportTemplatesSharedCollection).toContain(reportTemplate);
      expect(comp.report).toEqual(report);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IReport>>();
      const report = { id: 123 };
      jest.spyOn(reportFormService, 'getReport').mockReturnValue(report);
      jest.spyOn(reportService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ report });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: report }));
      saveSubject.complete();

      // THEN
      expect(reportFormService.getReport).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(reportService.update).toHaveBeenCalledWith(expect.objectContaining(report));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IReport>>();
      const report = { id: 123 };
      jest.spyOn(reportFormService, 'getReport').mockReturnValue({ id: null });
      jest.spyOn(reportService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ report: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: report }));
      saveSubject.complete();

      // THEN
      expect(reportFormService.getReport).toHaveBeenCalled();
      expect(reportService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IReport>>();
      const report = { id: 123 };
      jest.spyOn(reportService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ report });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(reportService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareReportTemplate', () => {
      it('Should forward to reportTemplateService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(reportTemplateService, 'compareReportTemplate');
        comp.compareReportTemplate(entity, entity2);
        expect(reportTemplateService.compareReportTemplate).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});

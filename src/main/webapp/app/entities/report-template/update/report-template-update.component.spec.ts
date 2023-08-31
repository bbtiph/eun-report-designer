import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ReportTemplateFormService } from './report-template-form.service';
import { ReportTemplateService } from '../service/report-template.service';
import { IReportTemplate } from '../report-template.model';

import { ReportTemplateUpdateComponent } from './report-template-update.component';

describe('ReportTemplate Management Update Component', () => {
  let comp: ReportTemplateUpdateComponent;
  let fixture: ComponentFixture<ReportTemplateUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let reportTemplateFormService: ReportTemplateFormService;
  let reportTemplateService: ReportTemplateService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ReportTemplateUpdateComponent],
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
      .overrideTemplate(ReportTemplateUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ReportTemplateUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    reportTemplateFormService = TestBed.inject(ReportTemplateFormService);
    reportTemplateService = TestBed.inject(ReportTemplateService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const reportTemplate: IReportTemplate = { id: 456 };

      activatedRoute.data = of({ reportTemplate });
      comp.ngOnInit();

      expect(comp.reportTemplate).toEqual(reportTemplate);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IReportTemplate>>();
      const reportTemplate = { id: 123 };
      jest.spyOn(reportTemplateFormService, 'getReportTemplate').mockReturnValue(reportTemplate);
      jest.spyOn(reportTemplateService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ reportTemplate });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: reportTemplate }));
      saveSubject.complete();

      // THEN
      expect(reportTemplateFormService.getReportTemplate).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(reportTemplateService.update).toHaveBeenCalledWith(expect.objectContaining(reportTemplate));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IReportTemplate>>();
      const reportTemplate = { id: 123 };
      jest.spyOn(reportTemplateFormService, 'getReportTemplate').mockReturnValue({ id: null });
      jest.spyOn(reportTemplateService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ reportTemplate: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: reportTemplate }));
      saveSubject.complete();

      // THEN
      expect(reportTemplateFormService.getReportTemplate).toHaveBeenCalled();
      expect(reportTemplateService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IReportTemplate>>();
      const reportTemplate = { id: 123 };
      jest.spyOn(reportTemplateService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ reportTemplate });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(reportTemplateService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { GeneratedReportFormService } from './generated-report-form.service';
import { GeneratedReportService } from '../service/generated-report.service';
import { IGeneratedReport } from '../generated-report.model';

import { GeneratedReportUpdateComponent } from './generated-report-update.component';

describe('GeneratedReport Management Update Component', () => {
  let comp: GeneratedReportUpdateComponent;
  let fixture: ComponentFixture<GeneratedReportUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let generatedReportFormService: GeneratedReportFormService;
  let generatedReportService: GeneratedReportService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [GeneratedReportUpdateComponent],
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
      .overrideTemplate(GeneratedReportUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(GeneratedReportUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    generatedReportFormService = TestBed.inject(GeneratedReportFormService);
    generatedReportService = TestBed.inject(GeneratedReportService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const generatedReport: IGeneratedReport = { id: 456 };

      activatedRoute.data = of({ generatedReport });
      comp.ngOnInit();

      expect(comp.generatedReport).toEqual(generatedReport);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGeneratedReport>>();
      const generatedReport = { id: 123 };
      jest.spyOn(generatedReportFormService, 'getGeneratedReport').mockReturnValue(generatedReport);
      jest.spyOn(generatedReportService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ generatedReport });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: generatedReport }));
      saveSubject.complete();

      // THEN
      expect(generatedReportFormService.getGeneratedReport).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(generatedReportService.update).toHaveBeenCalledWith(expect.objectContaining(generatedReport));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGeneratedReport>>();
      const generatedReport = { id: 123 };
      jest.spyOn(generatedReportFormService, 'getGeneratedReport').mockReturnValue({ id: null });
      jest.spyOn(generatedReportService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ generatedReport: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: generatedReport }));
      saveSubject.complete();

      // THEN
      expect(generatedReportFormService.getGeneratedReport).toHaveBeenCalled();
      expect(generatedReportService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGeneratedReport>>();
      const generatedReport = { id: 123 };
      jest.spyOn(generatedReportService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ generatedReport });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(generatedReportService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});

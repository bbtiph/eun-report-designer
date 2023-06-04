import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ReportBlocksContentFormService } from './report-blocks-content-form.service';
import { ReportBlocksContentService } from '../service/report-blocks-content.service';
import { IReportBlocksContent } from '../report-blocks-content.model';
import { IReportBlocks } from 'app/entities/report-blocks/report-blocks.model';
import { ReportBlocksService } from 'app/entities/report-blocks/service/report-blocks.service';

import { ReportBlocksContentUpdateComponent } from './report-blocks-content-update.component';

describe('ReportBlocksContent Management Update Component', () => {
  let comp: ReportBlocksContentUpdateComponent;
  let fixture: ComponentFixture<ReportBlocksContentUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let reportBlocksContentFormService: ReportBlocksContentFormService;
  let reportBlocksContentService: ReportBlocksContentService;
  let reportBlocksService: ReportBlocksService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ReportBlocksContentUpdateComponent],
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
      .overrideTemplate(ReportBlocksContentUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ReportBlocksContentUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    reportBlocksContentFormService = TestBed.inject(ReportBlocksContentFormService);
    reportBlocksContentService = TestBed.inject(ReportBlocksContentService);
    reportBlocksService = TestBed.inject(ReportBlocksService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call ReportBlocks query and add missing value', () => {
      const reportBlocksContent: IReportBlocksContent = { id: 456 };
      const reportBlocks: IReportBlocks = { id: 64214 };
      reportBlocksContent.reportBlocks = reportBlocks;

      const reportBlocksCollection: IReportBlocks[] = [{ id: 18563 }];
      jest.spyOn(reportBlocksService, 'query').mockReturnValue(of(new HttpResponse({ body: reportBlocksCollection })));
      const additionalReportBlocks = [reportBlocks];
      const expectedCollection: IReportBlocks[] = [...additionalReportBlocks, ...reportBlocksCollection];
      jest.spyOn(reportBlocksService, 'addReportBlocksToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ reportBlocksContent });
      comp.ngOnInit();

      expect(reportBlocksService.query).toHaveBeenCalled();
      expect(reportBlocksService.addReportBlocksToCollectionIfMissing).toHaveBeenCalledWith(
        reportBlocksCollection,
        ...additionalReportBlocks.map(expect.objectContaining)
      );
      expect(comp.reportBlocksSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const reportBlocksContent: IReportBlocksContent = { id: 456 };
      const reportBlocks: IReportBlocks = { id: 22045 };
      reportBlocksContent.reportBlocks = reportBlocks;

      activatedRoute.data = of({ reportBlocksContent });
      comp.ngOnInit();

      expect(comp.reportBlocksSharedCollection).toContain(reportBlocks);
      expect(comp.reportBlocksContent).toEqual(reportBlocksContent);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IReportBlocksContent>>();
      const reportBlocksContent = { id: 123 };
      jest.spyOn(reportBlocksContentFormService, 'getReportBlocksContent').mockReturnValue(reportBlocksContent);
      jest.spyOn(reportBlocksContentService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ reportBlocksContent });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: reportBlocksContent }));
      saveSubject.complete();

      // THEN
      expect(reportBlocksContentFormService.getReportBlocksContent).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(reportBlocksContentService.update).toHaveBeenCalledWith(expect.objectContaining(reportBlocksContent));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IReportBlocksContent>>();
      const reportBlocksContent = { id: 123 };
      jest.spyOn(reportBlocksContentFormService, 'getReportBlocksContent').mockReturnValue({ id: null });
      jest.spyOn(reportBlocksContentService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ reportBlocksContent: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: reportBlocksContent }));
      saveSubject.complete();

      // THEN
      expect(reportBlocksContentFormService.getReportBlocksContent).toHaveBeenCalled();
      expect(reportBlocksContentService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IReportBlocksContent>>();
      const reportBlocksContent = { id: 123 };
      jest.spyOn(reportBlocksContentService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ reportBlocksContent });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(reportBlocksContentService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareReportBlocks', () => {
      it('Should forward to reportBlocksService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(reportBlocksService, 'compareReportBlocks');
        comp.compareReportBlocks(entity, entity2);
        expect(reportBlocksService.compareReportBlocks).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});

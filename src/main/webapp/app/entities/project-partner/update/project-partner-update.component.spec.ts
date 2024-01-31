import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ProjectPartnerFormService } from './project-partner-form.service';
import { ProjectPartnerService } from '../service/project-partner.service';
import { IProjectPartner } from '../project-partner.model';
import { IJobInfo } from 'app/entities/job-info/job-info.model';
import { JobInfoService } from 'app/entities/job-info/service/job-info.service';

import { ProjectPartnerUpdateComponent } from './project-partner-update.component';

describe('ProjectPartner Management Update Component', () => {
  let comp: ProjectPartnerUpdateComponent;
  let fixture: ComponentFixture<ProjectPartnerUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let projectPartnerFormService: ProjectPartnerFormService;
  let projectPartnerService: ProjectPartnerService;
  let jobInfoService: JobInfoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ProjectPartnerUpdateComponent],
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
      .overrideTemplate(ProjectPartnerUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProjectPartnerUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    projectPartnerFormService = TestBed.inject(ProjectPartnerFormService);
    projectPartnerService = TestBed.inject(ProjectPartnerService);
    jobInfoService = TestBed.inject(JobInfoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call JobInfo query and add missing value', () => {
      const projectPartner: IProjectPartner = { id: 456 };
      const jobInfo: IJobInfo = { id: 85434 };
      projectPartner.jobInfo = jobInfo;

      const jobInfoCollection: IJobInfo[] = [{ id: 91366 }];
      jest.spyOn(jobInfoService, 'query').mockReturnValue(of(new HttpResponse({ body: jobInfoCollection })));
      const additionalJobInfos = [jobInfo];
      const expectedCollection: IJobInfo[] = [...additionalJobInfos, ...jobInfoCollection];
      jest.spyOn(jobInfoService, 'addJobInfoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ projectPartner });
      comp.ngOnInit();

      expect(jobInfoService.query).toHaveBeenCalled();
      expect(jobInfoService.addJobInfoToCollectionIfMissing).toHaveBeenCalledWith(
        jobInfoCollection,
        ...additionalJobInfos.map(expect.objectContaining)
      );
      expect(comp.jobInfosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const projectPartner: IProjectPartner = { id: 456 };
      const jobInfo: IJobInfo = { id: 31184 };
      projectPartner.jobInfo = jobInfo;

      activatedRoute.data = of({ projectPartner });
      comp.ngOnInit();

      expect(comp.jobInfosSharedCollection).toContain(jobInfo);
      expect(comp.projectPartner).toEqual(projectPartner);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProjectPartner>>();
      const projectPartner = { id: 123 };
      jest.spyOn(projectPartnerFormService, 'getProjectPartner').mockReturnValue(projectPartner);
      jest.spyOn(projectPartnerService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ projectPartner });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: projectPartner }));
      saveSubject.complete();

      // THEN
      expect(projectPartnerFormService.getProjectPartner).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(projectPartnerService.update).toHaveBeenCalledWith(expect.objectContaining(projectPartner));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProjectPartner>>();
      const projectPartner = { id: 123 };
      jest.spyOn(projectPartnerFormService, 'getProjectPartner').mockReturnValue({ id: null });
      jest.spyOn(projectPartnerService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ projectPartner: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: projectPartner }));
      saveSubject.complete();

      // THEN
      expect(projectPartnerFormService.getProjectPartner).toHaveBeenCalled();
      expect(projectPartnerService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProjectPartner>>();
      const projectPartner = { id: 123 };
      jest.spyOn(projectPartnerService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ projectPartner });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(projectPartnerService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareJobInfo', () => {
      it('Should forward to jobInfoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(jobInfoService, 'compareJobInfo');
        comp.compareJobInfo(entity, entity2);
        expect(jobInfoService.compareJobInfo).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});

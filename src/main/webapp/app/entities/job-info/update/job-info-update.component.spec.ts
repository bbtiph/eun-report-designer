import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { JobInfoFormService } from './job-info-form.service';
import { JobInfoService } from '../service/job-info.service';
import { IJobInfo } from '../job-info.model';

import { JobInfoUpdateComponent } from './job-info-update.component';

describe('JobInfo Management Update Component', () => {
  let comp: JobInfoUpdateComponent;
  let fixture: ComponentFixture<JobInfoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let jobInfoFormService: JobInfoFormService;
  let jobInfoService: JobInfoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [JobInfoUpdateComponent],
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
      .overrideTemplate(JobInfoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(JobInfoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    jobInfoFormService = TestBed.inject(JobInfoFormService);
    jobInfoService = TestBed.inject(JobInfoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const jobInfo: IJobInfo = { id: 456 };

      activatedRoute.data = of({ jobInfo });
      comp.ngOnInit();

      expect(comp.jobInfo).toEqual(jobInfo);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IJobInfo>>();
      const jobInfo = { id: 123 };
      jest.spyOn(jobInfoFormService, 'getJobInfo').mockReturnValue(jobInfo);
      jest.spyOn(jobInfoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ jobInfo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: jobInfo }));
      saveSubject.complete();

      // THEN
      expect(jobInfoFormService.getJobInfo).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(jobInfoService.update).toHaveBeenCalledWith(expect.objectContaining(jobInfo));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IJobInfo>>();
      const jobInfo = { id: 123 };
      jest.spyOn(jobInfoFormService, 'getJobInfo').mockReturnValue({ id: null });
      jest.spyOn(jobInfoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ jobInfo: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: jobInfo }));
      saveSubject.complete();

      // THEN
      expect(jobInfoFormService.getJobInfo).toHaveBeenCalled();
      expect(jobInfoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IJobInfo>>();
      const jobInfo = { id: 123 };
      jest.spyOn(jobInfoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ jobInfo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(jobInfoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});

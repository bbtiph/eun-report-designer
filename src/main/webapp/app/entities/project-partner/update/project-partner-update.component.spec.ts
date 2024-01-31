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

import { ProjectPartnerUpdateComponent } from './project-partner-update.component';

describe('ProjectPartner Management Update Component', () => {
  let comp: ProjectPartnerUpdateComponent;
  let fixture: ComponentFixture<ProjectPartnerUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let projectPartnerFormService: ProjectPartnerFormService;
  let projectPartnerService: ProjectPartnerService;

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

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const projectPartner: IProjectPartner = { id: 456 };

      activatedRoute.data = of({ projectPartner });
      comp.ngOnInit();

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
});

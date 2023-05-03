import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { FundingFormService } from './funding-form.service';
import { FundingService } from '../service/funding.service';
import { IFunding } from '../funding.model';
import { IProject } from 'app/entities/project/project.model';
import { ProjectService } from 'app/entities/project/service/project.service';

import { FundingUpdateComponent } from './funding-update.component';

describe('Funding Management Update Component', () => {
  let comp: FundingUpdateComponent;
  let fixture: ComponentFixture<FundingUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let fundingFormService: FundingFormService;
  let fundingService: FundingService;
  let projectService: ProjectService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [FundingUpdateComponent],
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
      .overrideTemplate(FundingUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FundingUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    fundingFormService = TestBed.inject(FundingFormService);
    fundingService = TestBed.inject(FundingService);
    projectService = TestBed.inject(ProjectService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Project query and add missing value', () => {
      const funding: IFunding = { id: 456 };
      const project: IProject = { id: 53181 };
      funding.project = project;

      const projectCollection: IProject[] = [{ id: 29726 }];
      jest.spyOn(projectService, 'query').mockReturnValue(of(new HttpResponse({ body: projectCollection })));
      const additionalProjects = [project];
      const expectedCollection: IProject[] = [...additionalProjects, ...projectCollection];
      jest.spyOn(projectService, 'addProjectToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ funding });
      comp.ngOnInit();

      expect(projectService.query).toHaveBeenCalled();
      expect(projectService.addProjectToCollectionIfMissing).toHaveBeenCalledWith(
        projectCollection,
        ...additionalProjects.map(expect.objectContaining)
      );
      expect(comp.projectsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const funding: IFunding = { id: 456 };
      const project: IProject = { id: 81547 };
      funding.project = project;

      activatedRoute.data = of({ funding });
      comp.ngOnInit();

      expect(comp.projectsSharedCollection).toContain(project);
      expect(comp.funding).toEqual(funding);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFunding>>();
      const funding = { id: 123 };
      jest.spyOn(fundingFormService, 'getFunding').mockReturnValue(funding);
      jest.spyOn(fundingService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ funding });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: funding }));
      saveSubject.complete();

      // THEN
      expect(fundingFormService.getFunding).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(fundingService.update).toHaveBeenCalledWith(expect.objectContaining(funding));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFunding>>();
      const funding = { id: 123 };
      jest.spyOn(fundingFormService, 'getFunding').mockReturnValue({ id: null });
      jest.spyOn(fundingService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ funding: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: funding }));
      saveSubject.complete();

      // THEN
      expect(fundingFormService.getFunding).toHaveBeenCalled();
      expect(fundingService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFunding>>();
      const funding = { id: 123 };
      jest.spyOn(fundingService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ funding });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(fundingService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareProject', () => {
      it('Should forward to projectService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(projectService, 'compareProject');
        comp.compareProject(entity, entity2);
        expect(projectService.compareProject).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});

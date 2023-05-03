import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ProjectFormService } from './project-form.service';
import { ProjectService } from '../service/project.service';
import { IProject } from '../project.model';
import { IFunding } from 'app/entities/funding/funding.model';
import { FundingService } from 'app/entities/funding/service/funding.service';

import { ProjectUpdateComponent } from './project-update.component';

describe('Project Management Update Component', () => {
  let comp: ProjectUpdateComponent;
  let fixture: ComponentFixture<ProjectUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let projectFormService: ProjectFormService;
  let projectService: ProjectService;
  let fundingService: FundingService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ProjectUpdateComponent],
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
      .overrideTemplate(ProjectUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProjectUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    projectFormService = TestBed.inject(ProjectFormService);
    projectService = TestBed.inject(ProjectService);
    fundingService = TestBed.inject(FundingService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Funding query and add missing value', () => {
      const project: IProject = { id: 456 };
      const funding: IFunding = { id: 77933 };
      project.funding = funding;

      const fundingCollection: IFunding[] = [{ id: 54659 }];
      jest.spyOn(fundingService, 'query').mockReturnValue(of(new HttpResponse({ body: fundingCollection })));
      const additionalFundings = [funding];
      const expectedCollection: IFunding[] = [...additionalFundings, ...fundingCollection];
      jest.spyOn(fundingService, 'addFundingToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ project });
      comp.ngOnInit();

      expect(fundingService.query).toHaveBeenCalled();
      expect(fundingService.addFundingToCollectionIfMissing).toHaveBeenCalledWith(
        fundingCollection,
        ...additionalFundings.map(expect.objectContaining)
      );
      expect(comp.fundingsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const project: IProject = { id: 456 };
      const funding: IFunding = { id: 32943 };
      project.funding = funding;

      activatedRoute.data = of({ project });
      comp.ngOnInit();

      expect(comp.fundingsSharedCollection).toContain(funding);
      expect(comp.project).toEqual(project);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProject>>();
      const project = { id: 123 };
      jest.spyOn(projectFormService, 'getProject').mockReturnValue(project);
      jest.spyOn(projectService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ project });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: project }));
      saveSubject.complete();

      // THEN
      expect(projectFormService.getProject).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(projectService.update).toHaveBeenCalledWith(expect.objectContaining(project));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProject>>();
      const project = { id: 123 };
      jest.spyOn(projectFormService, 'getProject').mockReturnValue({ id: null });
      jest.spyOn(projectService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ project: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: project }));
      saveSubject.complete();

      // THEN
      expect(projectFormService.getProject).toHaveBeenCalled();
      expect(projectService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProject>>();
      const project = { id: 123 };
      jest.spyOn(projectService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ project });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(projectService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareFunding', () => {
      it('Should forward to fundingService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(fundingService, 'compareFunding');
        comp.compareFunding(entity, entity2);
        expect(fundingService.compareFunding).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});

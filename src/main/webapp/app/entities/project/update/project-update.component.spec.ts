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
import { IOrganizationInProject } from 'app/entities/organization-in-project/organization-in-project.model';
import { OrganizationInProjectService } from 'app/entities/organization-in-project/service/organization-in-project.service';
import { IPersonInProject } from 'app/entities/person-in-project/person-in-project.model';
import { PersonInProjectService } from 'app/entities/person-in-project/service/person-in-project.service';

import { ProjectUpdateComponent } from './project-update.component';

describe('Project Management Update Component', () => {
  let comp: ProjectUpdateComponent;
  let fixture: ComponentFixture<ProjectUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let projectFormService: ProjectFormService;
  let projectService: ProjectService;
  let organizationInProjectService: OrganizationInProjectService;
  let personInProjectService: PersonInProjectService;

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
    organizationInProjectService = TestBed.inject(OrganizationInProjectService);
    personInProjectService = TestBed.inject(PersonInProjectService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call OrganizationInProject query and add missing value', () => {
      const project: IProject = { id: 456 };
      const organizationInProject: IOrganizationInProject = { id: 48541 };
      project.organizationInProject = organizationInProject;

      const organizationInProjectCollection: IOrganizationInProject[] = [{ id: 48172 }];
      jest.spyOn(organizationInProjectService, 'query').mockReturnValue(of(new HttpResponse({ body: organizationInProjectCollection })));
      const additionalOrganizationInProjects = [organizationInProject];
      const expectedCollection: IOrganizationInProject[] = [...additionalOrganizationInProjects, ...organizationInProjectCollection];
      jest.spyOn(organizationInProjectService, 'addOrganizationInProjectToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ project });
      comp.ngOnInit();

      expect(organizationInProjectService.query).toHaveBeenCalled();
      expect(organizationInProjectService.addOrganizationInProjectToCollectionIfMissing).toHaveBeenCalledWith(
        organizationInProjectCollection,
        ...additionalOrganizationInProjects.map(expect.objectContaining)
      );
      expect(comp.organizationInProjectsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call PersonInProject query and add missing value', () => {
      const project: IProject = { id: 456 };
      const personInProject: IPersonInProject = { id: 37760 };
      project.personInProject = personInProject;

      const personInProjectCollection: IPersonInProject[] = [{ id: 345 }];
      jest.spyOn(personInProjectService, 'query').mockReturnValue(of(new HttpResponse({ body: personInProjectCollection })));
      const additionalPersonInProjects = [personInProject];
      const expectedCollection: IPersonInProject[] = [...additionalPersonInProjects, ...personInProjectCollection];
      jest.spyOn(personInProjectService, 'addPersonInProjectToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ project });
      comp.ngOnInit();

      expect(personInProjectService.query).toHaveBeenCalled();
      expect(personInProjectService.addPersonInProjectToCollectionIfMissing).toHaveBeenCalledWith(
        personInProjectCollection,
        ...additionalPersonInProjects.map(expect.objectContaining)
      );
      expect(comp.personInProjectsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const project: IProject = { id: 456 };
      const organizationInProject: IOrganizationInProject = { id: 50973 };
      project.organizationInProject = organizationInProject;
      const personInProject: IPersonInProject = { id: 14558 };
      project.personInProject = personInProject;

      activatedRoute.data = of({ project });
      comp.ngOnInit();

      expect(comp.organizationInProjectsSharedCollection).toContain(organizationInProject);
      expect(comp.personInProjectsSharedCollection).toContain(personInProject);
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
    describe('compareOrganizationInProject', () => {
      it('Should forward to organizationInProjectService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(organizationInProjectService, 'compareOrganizationInProject');
        comp.compareOrganizationInProject(entity, entity2);
        expect(organizationInProjectService.compareOrganizationInProject).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('comparePersonInProject', () => {
      it('Should forward to personInProjectService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(personInProjectService, 'comparePersonInProject');
        comp.comparePersonInProject(entity, entity2);
        expect(personInProjectService.comparePersonInProject).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});

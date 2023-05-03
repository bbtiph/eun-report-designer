import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { OrganizationInProjectFormService } from './organization-in-project-form.service';
import { OrganizationInProjectService } from '../service/organization-in-project.service';
import { IOrganizationInProject } from '../organization-in-project.model';
import { IProject } from 'app/entities/project/project.model';
import { ProjectService } from 'app/entities/project/service/project.service';
import { IOrganization } from 'app/entities/organization/organization.model';
import { OrganizationService } from 'app/entities/organization/service/organization.service';

import { OrganizationInProjectUpdateComponent } from './organization-in-project-update.component';

describe('OrganizationInProject Management Update Component', () => {
  let comp: OrganizationInProjectUpdateComponent;
  let fixture: ComponentFixture<OrganizationInProjectUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let organizationInProjectFormService: OrganizationInProjectFormService;
  let organizationInProjectService: OrganizationInProjectService;
  let projectService: ProjectService;
  let organizationService: OrganizationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [OrganizationInProjectUpdateComponent],
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
      .overrideTemplate(OrganizationInProjectUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(OrganizationInProjectUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    organizationInProjectFormService = TestBed.inject(OrganizationInProjectFormService);
    organizationInProjectService = TestBed.inject(OrganizationInProjectService);
    projectService = TestBed.inject(ProjectService);
    organizationService = TestBed.inject(OrganizationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Project query and add missing value', () => {
      const organizationInProject: IOrganizationInProject = { id: 456 };
      const project: IProject = { id: 93741 };
      organizationInProject.project = project;

      const projectCollection: IProject[] = [{ id: 82833 }];
      jest.spyOn(projectService, 'query').mockReturnValue(of(new HttpResponse({ body: projectCollection })));
      const additionalProjects = [project];
      const expectedCollection: IProject[] = [...additionalProjects, ...projectCollection];
      jest.spyOn(projectService, 'addProjectToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ organizationInProject });
      comp.ngOnInit();

      expect(projectService.query).toHaveBeenCalled();
      expect(projectService.addProjectToCollectionIfMissing).toHaveBeenCalledWith(
        projectCollection,
        ...additionalProjects.map(expect.objectContaining)
      );
      expect(comp.projectsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Organization query and add missing value', () => {
      const organizationInProject: IOrganizationInProject = { id: 456 };
      const organization: IOrganization = { id: 99895 };
      organizationInProject.organization = organization;

      const organizationCollection: IOrganization[] = [{ id: 34036 }];
      jest.spyOn(organizationService, 'query').mockReturnValue(of(new HttpResponse({ body: organizationCollection })));
      const additionalOrganizations = [organization];
      const expectedCollection: IOrganization[] = [...additionalOrganizations, ...organizationCollection];
      jest.spyOn(organizationService, 'addOrganizationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ organizationInProject });
      comp.ngOnInit();

      expect(organizationService.query).toHaveBeenCalled();
      expect(organizationService.addOrganizationToCollectionIfMissing).toHaveBeenCalledWith(
        organizationCollection,
        ...additionalOrganizations.map(expect.objectContaining)
      );
      expect(comp.organizationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const organizationInProject: IOrganizationInProject = { id: 456 };
      const project: IProject = { id: 25698 };
      organizationInProject.project = project;
      const organization: IOrganization = { id: 86195 };
      organizationInProject.organization = organization;

      activatedRoute.data = of({ organizationInProject });
      comp.ngOnInit();

      expect(comp.projectsSharedCollection).toContain(project);
      expect(comp.organizationsSharedCollection).toContain(organization);
      expect(comp.organizationInProject).toEqual(organizationInProject);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOrganizationInProject>>();
      const organizationInProject = { id: 123 };
      jest.spyOn(organizationInProjectFormService, 'getOrganizationInProject').mockReturnValue(organizationInProject);
      jest.spyOn(organizationInProjectService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ organizationInProject });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: organizationInProject }));
      saveSubject.complete();

      // THEN
      expect(organizationInProjectFormService.getOrganizationInProject).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(organizationInProjectService.update).toHaveBeenCalledWith(expect.objectContaining(organizationInProject));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOrganizationInProject>>();
      const organizationInProject = { id: 123 };
      jest.spyOn(organizationInProjectFormService, 'getOrganizationInProject').mockReturnValue({ id: null });
      jest.spyOn(organizationInProjectService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ organizationInProject: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: organizationInProject }));
      saveSubject.complete();

      // THEN
      expect(organizationInProjectFormService.getOrganizationInProject).toHaveBeenCalled();
      expect(organizationInProjectService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOrganizationInProject>>();
      const organizationInProject = { id: 123 };
      jest.spyOn(organizationInProjectService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ organizationInProject });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(organizationInProjectService.update).toHaveBeenCalled();
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

    describe('compareOrganization', () => {
      it('Should forward to organizationService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(organizationService, 'compareOrganization');
        comp.compareOrganization(entity, entity2);
        expect(organizationService.compareOrganization).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PersonInProjectFormService } from './person-in-project-form.service';
import { PersonInProjectService } from '../service/person-in-project.service';
import { IPersonInProject } from '../person-in-project.model';
import { IPerson } from 'app/entities/person/person.model';
import { PersonService } from 'app/entities/person/service/person.service';
import { IProject } from 'app/entities/project/project.model';
import { ProjectService } from 'app/entities/project/service/project.service';

import { PersonInProjectUpdateComponent } from './person-in-project-update.component';

describe('PersonInProject Management Update Component', () => {
  let comp: PersonInProjectUpdateComponent;
  let fixture: ComponentFixture<PersonInProjectUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let personInProjectFormService: PersonInProjectFormService;
  let personInProjectService: PersonInProjectService;
  let personService: PersonService;
  let projectService: ProjectService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PersonInProjectUpdateComponent],
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
      .overrideTemplate(PersonInProjectUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PersonInProjectUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    personInProjectFormService = TestBed.inject(PersonInProjectFormService);
    personInProjectService = TestBed.inject(PersonInProjectService);
    personService = TestBed.inject(PersonService);
    projectService = TestBed.inject(ProjectService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Person query and add missing value', () => {
      const personInProject: IPersonInProject = { id: 456 };
      const person: IPerson = { id: 36830 };
      personInProject.person = person;

      const personCollection: IPerson[] = [{ id: 1689 }];
      jest.spyOn(personService, 'query').mockReturnValue(of(new HttpResponse({ body: personCollection })));
      const additionalPeople = [person];
      const expectedCollection: IPerson[] = [...additionalPeople, ...personCollection];
      jest.spyOn(personService, 'addPersonToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ personInProject });
      comp.ngOnInit();

      expect(personService.query).toHaveBeenCalled();
      expect(personService.addPersonToCollectionIfMissing).toHaveBeenCalledWith(
        personCollection,
        ...additionalPeople.map(expect.objectContaining)
      );
      expect(comp.peopleSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Project query and add missing value', () => {
      const personInProject: IPersonInProject = { id: 456 };
      const project: IProject = { id: 98165 };
      personInProject.project = project;

      const projectCollection: IProject[] = [{ id: 19984 }];
      jest.spyOn(projectService, 'query').mockReturnValue(of(new HttpResponse({ body: projectCollection })));
      const additionalProjects = [project];
      const expectedCollection: IProject[] = [...additionalProjects, ...projectCollection];
      jest.spyOn(projectService, 'addProjectToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ personInProject });
      comp.ngOnInit();

      expect(projectService.query).toHaveBeenCalled();
      expect(projectService.addProjectToCollectionIfMissing).toHaveBeenCalledWith(
        projectCollection,
        ...additionalProjects.map(expect.objectContaining)
      );
      expect(comp.projectsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const personInProject: IPersonInProject = { id: 456 };
      const person: IPerson = { id: 66366 };
      personInProject.person = person;
      const project: IProject = { id: 94611 };
      personInProject.project = project;

      activatedRoute.data = of({ personInProject });
      comp.ngOnInit();

      expect(comp.peopleSharedCollection).toContain(person);
      expect(comp.projectsSharedCollection).toContain(project);
      expect(comp.personInProject).toEqual(personInProject);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPersonInProject>>();
      const personInProject = { id: 123 };
      jest.spyOn(personInProjectFormService, 'getPersonInProject').mockReturnValue(personInProject);
      jest.spyOn(personInProjectService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ personInProject });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: personInProject }));
      saveSubject.complete();

      // THEN
      expect(personInProjectFormService.getPersonInProject).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(personInProjectService.update).toHaveBeenCalledWith(expect.objectContaining(personInProject));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPersonInProject>>();
      const personInProject = { id: 123 };
      jest.spyOn(personInProjectFormService, 'getPersonInProject').mockReturnValue({ id: null });
      jest.spyOn(personInProjectService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ personInProject: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: personInProject }));
      saveSubject.complete();

      // THEN
      expect(personInProjectFormService.getPersonInProject).toHaveBeenCalled();
      expect(personInProjectService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPersonInProject>>();
      const personInProject = { id: 123 };
      jest.spyOn(personInProjectService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ personInProject });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(personInProjectService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('comparePerson', () => {
      it('Should forward to personService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(personService, 'comparePerson');
        comp.comparePerson(entity, entity2);
        expect(personService.comparePerson).toHaveBeenCalledWith(entity, entity2);
      });
    });

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

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

import { PersonInProjectUpdateComponent } from './person-in-project-update.component';

describe('PersonInProject Management Update Component', () => {
  let comp: PersonInProjectUpdateComponent;
  let fixture: ComponentFixture<PersonInProjectUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let personInProjectFormService: PersonInProjectFormService;
  let personInProjectService: PersonInProjectService;

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

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const personInProject: IPersonInProject = { id: 456 };

      activatedRoute.data = of({ personInProject });
      comp.ngOnInit();

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
});

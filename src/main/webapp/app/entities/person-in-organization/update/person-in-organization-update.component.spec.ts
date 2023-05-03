import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PersonInOrganizationFormService } from './person-in-organization-form.service';
import { PersonInOrganizationService } from '../service/person-in-organization.service';
import { IPersonInOrganization } from '../person-in-organization.model';

import { PersonInOrganizationUpdateComponent } from './person-in-organization-update.component';

describe('PersonInOrganization Management Update Component', () => {
  let comp: PersonInOrganizationUpdateComponent;
  let fixture: ComponentFixture<PersonInOrganizationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let personInOrganizationFormService: PersonInOrganizationFormService;
  let personInOrganizationService: PersonInOrganizationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PersonInOrganizationUpdateComponent],
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
      .overrideTemplate(PersonInOrganizationUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PersonInOrganizationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    personInOrganizationFormService = TestBed.inject(PersonInOrganizationFormService);
    personInOrganizationService = TestBed.inject(PersonInOrganizationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const personInOrganization: IPersonInOrganization = { id: 456 };

      activatedRoute.data = of({ personInOrganization });
      comp.ngOnInit();

      expect(comp.personInOrganization).toEqual(personInOrganization);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPersonInOrganization>>();
      const personInOrganization = { id: 123 };
      jest.spyOn(personInOrganizationFormService, 'getPersonInOrganization').mockReturnValue(personInOrganization);
      jest.spyOn(personInOrganizationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ personInOrganization });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: personInOrganization }));
      saveSubject.complete();

      // THEN
      expect(personInOrganizationFormService.getPersonInOrganization).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(personInOrganizationService.update).toHaveBeenCalledWith(expect.objectContaining(personInOrganization));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPersonInOrganization>>();
      const personInOrganization = { id: 123 };
      jest.spyOn(personInOrganizationFormService, 'getPersonInOrganization').mockReturnValue({ id: null });
      jest.spyOn(personInOrganizationService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ personInOrganization: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: personInOrganization }));
      saveSubject.complete();

      // THEN
      expect(personInOrganizationFormService.getPersonInOrganization).toHaveBeenCalled();
      expect(personInOrganizationService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPersonInOrganization>>();
      const personInOrganization = { id: 123 };
      jest.spyOn(personInOrganizationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ personInOrganization });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(personInOrganizationService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});

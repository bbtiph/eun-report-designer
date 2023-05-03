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
import { IPerson } from 'app/entities/person/person.model';
import { PersonService } from 'app/entities/person/service/person.service';
import { IOrganization } from 'app/entities/organization/organization.model';
import { OrganizationService } from 'app/entities/organization/service/organization.service';

import { PersonInOrganizationUpdateComponent } from './person-in-organization-update.component';

describe('PersonInOrganization Management Update Component', () => {
  let comp: PersonInOrganizationUpdateComponent;
  let fixture: ComponentFixture<PersonInOrganizationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let personInOrganizationFormService: PersonInOrganizationFormService;
  let personInOrganizationService: PersonInOrganizationService;
  let personService: PersonService;
  let organizationService: OrganizationService;

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
    personService = TestBed.inject(PersonService);
    organizationService = TestBed.inject(OrganizationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Person query and add missing value', () => {
      const personInOrganization: IPersonInOrganization = { id: 456 };
      const person: IPerson = { id: 6880 };
      personInOrganization.person = person;

      const personCollection: IPerson[] = [{ id: 14468 }];
      jest.spyOn(personService, 'query').mockReturnValue(of(new HttpResponse({ body: personCollection })));
      const additionalPeople = [person];
      const expectedCollection: IPerson[] = [...additionalPeople, ...personCollection];
      jest.spyOn(personService, 'addPersonToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ personInOrganization });
      comp.ngOnInit();

      expect(personService.query).toHaveBeenCalled();
      expect(personService.addPersonToCollectionIfMissing).toHaveBeenCalledWith(
        personCollection,
        ...additionalPeople.map(expect.objectContaining)
      );
      expect(comp.peopleSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Organization query and add missing value', () => {
      const personInOrganization: IPersonInOrganization = { id: 456 };
      const organization: IOrganization = { id: 21815 };
      personInOrganization.organization = organization;

      const organizationCollection: IOrganization[] = [{ id: 58869 }];
      jest.spyOn(organizationService, 'query').mockReturnValue(of(new HttpResponse({ body: organizationCollection })));
      const additionalOrganizations = [organization];
      const expectedCollection: IOrganization[] = [...additionalOrganizations, ...organizationCollection];
      jest.spyOn(organizationService, 'addOrganizationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ personInOrganization });
      comp.ngOnInit();

      expect(organizationService.query).toHaveBeenCalled();
      expect(organizationService.addOrganizationToCollectionIfMissing).toHaveBeenCalledWith(
        organizationCollection,
        ...additionalOrganizations.map(expect.objectContaining)
      );
      expect(comp.organizationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const personInOrganization: IPersonInOrganization = { id: 456 };
      const person: IPerson = { id: 16037 };
      personInOrganization.person = person;
      const organization: IOrganization = { id: 95300 };
      personInOrganization.organization = organization;

      activatedRoute.data = of({ personInOrganization });
      comp.ngOnInit();

      expect(comp.peopleSharedCollection).toContain(person);
      expect(comp.organizationsSharedCollection).toContain(organization);
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

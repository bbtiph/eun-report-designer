import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CountryFormService } from './country-form.service';
import { CountryService } from '../service/country.service';
import { ICountry } from '../country.model';
import { IMinistry } from 'app/entities/ministry/ministry.model';
import { MinistryService } from 'app/entities/ministry/service/ministry.service';
import { IOperationalBodyMember } from 'app/entities/operational-body-member/operational-body-member.model';
import { OperationalBodyMemberService } from 'app/entities/operational-body-member/service/operational-body-member.service';
import { IOrganization } from 'app/entities/organization/organization.model';
import { OrganizationService } from 'app/entities/organization/service/organization.service';
import { IPerson } from 'app/entities/person/person.model';
import { PersonService } from 'app/entities/person/service/person.service';

import { CountryUpdateComponent } from './country-update.component';

describe('Country Management Update Component', () => {
  let comp: CountryUpdateComponent;
  let fixture: ComponentFixture<CountryUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let countryFormService: CountryFormService;
  let countryService: CountryService;
  let ministryService: MinistryService;
  let operationalBodyMemberService: OperationalBodyMemberService;
  let organizationService: OrganizationService;
  let personService: PersonService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CountryUpdateComponent],
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
      .overrideTemplate(CountryUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CountryUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    countryFormService = TestBed.inject(CountryFormService);
    countryService = TestBed.inject(CountryService);
    ministryService = TestBed.inject(MinistryService);
    operationalBodyMemberService = TestBed.inject(OperationalBodyMemberService);
    organizationService = TestBed.inject(OrganizationService);
    personService = TestBed.inject(PersonService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Ministry query and add missing value', () => {
      const country: ICountry = { id: 456 };
      const ministry: IMinistry = { id: 25013 };
      country.ministry = ministry;

      const ministryCollection: IMinistry[] = [{ id: 45031 }];
      jest.spyOn(ministryService, 'query').mockReturnValue(of(new HttpResponse({ body: ministryCollection })));
      const additionalMinistries = [ministry];
      const expectedCollection: IMinistry[] = [...additionalMinistries, ...ministryCollection];
      jest.spyOn(ministryService, 'addMinistryToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ country });
      comp.ngOnInit();

      expect(ministryService.query).toHaveBeenCalled();
      expect(ministryService.addMinistryToCollectionIfMissing).toHaveBeenCalledWith(
        ministryCollection,
        ...additionalMinistries.map(expect.objectContaining)
      );
      expect(comp.ministriesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call OperationalBodyMember query and add missing value', () => {
      const country: ICountry = { id: 456 };
      const operationalBodyMember: IOperationalBodyMember = { id: 51648 };
      country.operationalBodyMember = operationalBodyMember;

      const operationalBodyMemberCollection: IOperationalBodyMember[] = [{ id: 85219 }];
      jest.spyOn(operationalBodyMemberService, 'query').mockReturnValue(of(new HttpResponse({ body: operationalBodyMemberCollection })));
      const additionalOperationalBodyMembers = [operationalBodyMember];
      const expectedCollection: IOperationalBodyMember[] = [...additionalOperationalBodyMembers, ...operationalBodyMemberCollection];
      jest.spyOn(operationalBodyMemberService, 'addOperationalBodyMemberToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ country });
      comp.ngOnInit();

      expect(operationalBodyMemberService.query).toHaveBeenCalled();
      expect(operationalBodyMemberService.addOperationalBodyMemberToCollectionIfMissing).toHaveBeenCalledWith(
        operationalBodyMemberCollection,
        ...additionalOperationalBodyMembers.map(expect.objectContaining)
      );
      expect(comp.operationalBodyMembersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Organization query and add missing value', () => {
      const country: ICountry = { id: 456 };
      const organization: IOrganization = { id: 49978 };
      country.organization = organization;

      const organizationCollection: IOrganization[] = [{ id: 67355 }];
      jest.spyOn(organizationService, 'query').mockReturnValue(of(new HttpResponse({ body: organizationCollection })));
      const additionalOrganizations = [organization];
      const expectedCollection: IOrganization[] = [...additionalOrganizations, ...organizationCollection];
      jest.spyOn(organizationService, 'addOrganizationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ country });
      comp.ngOnInit();

      expect(organizationService.query).toHaveBeenCalled();
      expect(organizationService.addOrganizationToCollectionIfMissing).toHaveBeenCalledWith(
        organizationCollection,
        ...additionalOrganizations.map(expect.objectContaining)
      );
      expect(comp.organizationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Person query and add missing value', () => {
      const country: ICountry = { id: 456 };
      const person: IPerson = { id: 85760 };
      country.person = person;

      const personCollection: IPerson[] = [{ id: 79749 }];
      jest.spyOn(personService, 'query').mockReturnValue(of(new HttpResponse({ body: personCollection })));
      const additionalPeople = [person];
      const expectedCollection: IPerson[] = [...additionalPeople, ...personCollection];
      jest.spyOn(personService, 'addPersonToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ country });
      comp.ngOnInit();

      expect(personService.query).toHaveBeenCalled();
      expect(personService.addPersonToCollectionIfMissing).toHaveBeenCalledWith(
        personCollection,
        ...additionalPeople.map(expect.objectContaining)
      );
      expect(comp.peopleSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const country: ICountry = { id: 456 };
      const ministry: IMinistry = { id: 38326 };
      country.ministry = ministry;
      const operationalBodyMember: IOperationalBodyMember = { id: 94687 };
      country.operationalBodyMember = operationalBodyMember;
      const organization: IOrganization = { id: 78999 };
      country.organization = organization;
      const person: IPerson = { id: 70321 };
      country.person = person;

      activatedRoute.data = of({ country });
      comp.ngOnInit();

      expect(comp.ministriesSharedCollection).toContain(ministry);
      expect(comp.operationalBodyMembersSharedCollection).toContain(operationalBodyMember);
      expect(comp.organizationsSharedCollection).toContain(organization);
      expect(comp.peopleSharedCollection).toContain(person);
      expect(comp.country).toEqual(country);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICountry>>();
      const country = { id: 123 };
      jest.spyOn(countryFormService, 'getCountry').mockReturnValue(country);
      jest.spyOn(countryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ country });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: country }));
      saveSubject.complete();

      // THEN
      expect(countryFormService.getCountry).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(countryService.update).toHaveBeenCalledWith(expect.objectContaining(country));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICountry>>();
      const country = { id: 123 };
      jest.spyOn(countryFormService, 'getCountry').mockReturnValue({ id: null });
      jest.spyOn(countryService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ country: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: country }));
      saveSubject.complete();

      // THEN
      expect(countryFormService.getCountry).toHaveBeenCalled();
      expect(countryService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICountry>>();
      const country = { id: 123 };
      jest.spyOn(countryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ country });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(countryService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareMinistry', () => {
      it('Should forward to ministryService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(ministryService, 'compareMinistry');
        comp.compareMinistry(entity, entity2);
        expect(ministryService.compareMinistry).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareOperationalBodyMember', () => {
      it('Should forward to operationalBodyMemberService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(operationalBodyMemberService, 'compareOperationalBodyMember');
        comp.compareOperationalBodyMember(entity, entity2);
        expect(operationalBodyMemberService.compareOperationalBodyMember).toHaveBeenCalledWith(entity, entity2);
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

    describe('comparePerson', () => {
      it('Should forward to personService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(personService, 'comparePerson');
        comp.comparePerson(entity, entity2);
        expect(personService.comparePerson).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});

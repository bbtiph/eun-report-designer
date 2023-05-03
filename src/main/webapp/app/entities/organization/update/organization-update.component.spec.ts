import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { OrganizationFormService } from './organization-form.service';
import { OrganizationService } from '../service/organization.service';
import { IOrganization } from '../organization.model';
import { IEventInOrganization } from 'app/entities/event-in-organization/event-in-organization.model';
import { EventInOrganizationService } from 'app/entities/event-in-organization/service/event-in-organization.service';
import { IOrganizationInMinistry } from 'app/entities/organization-in-ministry/organization-in-ministry.model';
import { OrganizationInMinistryService } from 'app/entities/organization-in-ministry/service/organization-in-ministry.service';
import { IOrganizationInProject } from 'app/entities/organization-in-project/organization-in-project.model';
import { OrganizationInProjectService } from 'app/entities/organization-in-project/service/organization-in-project.service';
import { IPersonInOrganization } from 'app/entities/person-in-organization/person-in-organization.model';
import { PersonInOrganizationService } from 'app/entities/person-in-organization/service/person-in-organization.service';

import { OrganizationUpdateComponent } from './organization-update.component';

describe('Organization Management Update Component', () => {
  let comp: OrganizationUpdateComponent;
  let fixture: ComponentFixture<OrganizationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let organizationFormService: OrganizationFormService;
  let organizationService: OrganizationService;
  let eventInOrganizationService: EventInOrganizationService;
  let organizationInMinistryService: OrganizationInMinistryService;
  let organizationInProjectService: OrganizationInProjectService;
  let personInOrganizationService: PersonInOrganizationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [OrganizationUpdateComponent],
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
      .overrideTemplate(OrganizationUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(OrganizationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    organizationFormService = TestBed.inject(OrganizationFormService);
    organizationService = TestBed.inject(OrganizationService);
    eventInOrganizationService = TestBed.inject(EventInOrganizationService);
    organizationInMinistryService = TestBed.inject(OrganizationInMinistryService);
    organizationInProjectService = TestBed.inject(OrganizationInProjectService);
    personInOrganizationService = TestBed.inject(PersonInOrganizationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call EventInOrganization query and add missing value', () => {
      const organization: IOrganization = { id: 456 };
      const eventInOrganization: IEventInOrganization = { id: 85369 };
      organization.eventInOrganization = eventInOrganization;

      const eventInOrganizationCollection: IEventInOrganization[] = [{ id: 12191 }];
      jest.spyOn(eventInOrganizationService, 'query').mockReturnValue(of(new HttpResponse({ body: eventInOrganizationCollection })));
      const additionalEventInOrganizations = [eventInOrganization];
      const expectedCollection: IEventInOrganization[] = [...additionalEventInOrganizations, ...eventInOrganizationCollection];
      jest.spyOn(eventInOrganizationService, 'addEventInOrganizationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ organization });
      comp.ngOnInit();

      expect(eventInOrganizationService.query).toHaveBeenCalled();
      expect(eventInOrganizationService.addEventInOrganizationToCollectionIfMissing).toHaveBeenCalledWith(
        eventInOrganizationCollection,
        ...additionalEventInOrganizations.map(expect.objectContaining)
      );
      expect(comp.eventInOrganizationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call OrganizationInMinistry query and add missing value', () => {
      const organization: IOrganization = { id: 456 };
      const organizationInMinistry: IOrganizationInMinistry = { id: 46774 };
      organization.organizationInMinistry = organizationInMinistry;

      const organizationInMinistryCollection: IOrganizationInMinistry[] = [{ id: 63164 }];
      jest.spyOn(organizationInMinistryService, 'query').mockReturnValue(of(new HttpResponse({ body: organizationInMinistryCollection })));
      const additionalOrganizationInMinistries = [organizationInMinistry];
      const expectedCollection: IOrganizationInMinistry[] = [...additionalOrganizationInMinistries, ...organizationInMinistryCollection];
      jest.spyOn(organizationInMinistryService, 'addOrganizationInMinistryToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ organization });
      comp.ngOnInit();

      expect(organizationInMinistryService.query).toHaveBeenCalled();
      expect(organizationInMinistryService.addOrganizationInMinistryToCollectionIfMissing).toHaveBeenCalledWith(
        organizationInMinistryCollection,
        ...additionalOrganizationInMinistries.map(expect.objectContaining)
      );
      expect(comp.organizationInMinistriesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call OrganizationInProject query and add missing value', () => {
      const organization: IOrganization = { id: 456 };
      const organizationInProject: IOrganizationInProject = { id: 59726 };
      organization.organizationInProject = organizationInProject;

      const organizationInProjectCollection: IOrganizationInProject[] = [{ id: 75142 }];
      jest.spyOn(organizationInProjectService, 'query').mockReturnValue(of(new HttpResponse({ body: organizationInProjectCollection })));
      const additionalOrganizationInProjects = [organizationInProject];
      const expectedCollection: IOrganizationInProject[] = [...additionalOrganizationInProjects, ...organizationInProjectCollection];
      jest.spyOn(organizationInProjectService, 'addOrganizationInProjectToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ organization });
      comp.ngOnInit();

      expect(organizationInProjectService.query).toHaveBeenCalled();
      expect(organizationInProjectService.addOrganizationInProjectToCollectionIfMissing).toHaveBeenCalledWith(
        organizationInProjectCollection,
        ...additionalOrganizationInProjects.map(expect.objectContaining)
      );
      expect(comp.organizationInProjectsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call PersonInOrganization query and add missing value', () => {
      const organization: IOrganization = { id: 456 };
      const personInOrganization: IPersonInOrganization = { id: 69312 };
      organization.personInOrganization = personInOrganization;

      const personInOrganizationCollection: IPersonInOrganization[] = [{ id: 57288 }];
      jest.spyOn(personInOrganizationService, 'query').mockReturnValue(of(new HttpResponse({ body: personInOrganizationCollection })));
      const additionalPersonInOrganizations = [personInOrganization];
      const expectedCollection: IPersonInOrganization[] = [...additionalPersonInOrganizations, ...personInOrganizationCollection];
      jest.spyOn(personInOrganizationService, 'addPersonInOrganizationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ organization });
      comp.ngOnInit();

      expect(personInOrganizationService.query).toHaveBeenCalled();
      expect(personInOrganizationService.addPersonInOrganizationToCollectionIfMissing).toHaveBeenCalledWith(
        personInOrganizationCollection,
        ...additionalPersonInOrganizations.map(expect.objectContaining)
      );
      expect(comp.personInOrganizationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const organization: IOrganization = { id: 456 };
      const eventInOrganization: IEventInOrganization = { id: 30140 };
      organization.eventInOrganization = eventInOrganization;
      const organizationInMinistry: IOrganizationInMinistry = { id: 66424 };
      organization.organizationInMinistry = organizationInMinistry;
      const organizationInProject: IOrganizationInProject = { id: 2156 };
      organization.organizationInProject = organizationInProject;
      const personInOrganization: IPersonInOrganization = { id: 35016 };
      organization.personInOrganization = personInOrganization;

      activatedRoute.data = of({ organization });
      comp.ngOnInit();

      expect(comp.eventInOrganizationsSharedCollection).toContain(eventInOrganization);
      expect(comp.organizationInMinistriesSharedCollection).toContain(organizationInMinistry);
      expect(comp.organizationInProjectsSharedCollection).toContain(organizationInProject);
      expect(comp.personInOrganizationsSharedCollection).toContain(personInOrganization);
      expect(comp.organization).toEqual(organization);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOrganization>>();
      const organization = { id: 123 };
      jest.spyOn(organizationFormService, 'getOrganization').mockReturnValue(organization);
      jest.spyOn(organizationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ organization });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: organization }));
      saveSubject.complete();

      // THEN
      expect(organizationFormService.getOrganization).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(organizationService.update).toHaveBeenCalledWith(expect.objectContaining(organization));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOrganization>>();
      const organization = { id: 123 };
      jest.spyOn(organizationFormService, 'getOrganization').mockReturnValue({ id: null });
      jest.spyOn(organizationService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ organization: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: organization }));
      saveSubject.complete();

      // THEN
      expect(organizationFormService.getOrganization).toHaveBeenCalled();
      expect(organizationService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOrganization>>();
      const organization = { id: 123 };
      jest.spyOn(organizationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ organization });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(organizationService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareEventInOrganization', () => {
      it('Should forward to eventInOrganizationService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(eventInOrganizationService, 'compareEventInOrganization');
        comp.compareEventInOrganization(entity, entity2);
        expect(eventInOrganizationService.compareEventInOrganization).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareOrganizationInMinistry', () => {
      it('Should forward to organizationInMinistryService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(organizationInMinistryService, 'compareOrganizationInMinistry');
        comp.compareOrganizationInMinistry(entity, entity2);
        expect(organizationInMinistryService.compareOrganizationInMinistry).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareOrganizationInProject', () => {
      it('Should forward to organizationInProjectService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(organizationInProjectService, 'compareOrganizationInProject');
        comp.compareOrganizationInProject(entity, entity2);
        expect(organizationInProjectService.compareOrganizationInProject).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('comparePersonInOrganization', () => {
      it('Should forward to personInOrganizationService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(personInOrganizationService, 'comparePersonInOrganization');
        comp.comparePersonInOrganization(entity, entity2);
        expect(personInOrganizationService.comparePersonInOrganization).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});

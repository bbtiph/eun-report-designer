import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PersonFormService } from './person-form.service';
import { PersonService } from '../service/person.service';
import { IPerson } from '../person.model';
import { IEunTeamMember } from 'app/entities/eun-team-member/eun-team-member.model';
import { EunTeamMemberService } from 'app/entities/eun-team-member/service/eun-team-member.service';
import { IEventParticipant } from 'app/entities/event-participant/event-participant.model';
import { EventParticipantService } from 'app/entities/event-participant/service/event-participant.service';
import { IPersonInOrganization } from 'app/entities/person-in-organization/person-in-organization.model';
import { PersonInOrganizationService } from 'app/entities/person-in-organization/service/person-in-organization.service';
import { IPersonInProject } from 'app/entities/person-in-project/person-in-project.model';
import { PersonInProjectService } from 'app/entities/person-in-project/service/person-in-project.service';

import { PersonUpdateComponent } from './person-update.component';

describe('Person Management Update Component', () => {
  let comp: PersonUpdateComponent;
  let fixture: ComponentFixture<PersonUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let personFormService: PersonFormService;
  let personService: PersonService;
  let eunTeamMemberService: EunTeamMemberService;
  let eventParticipantService: EventParticipantService;
  let personInOrganizationService: PersonInOrganizationService;
  let personInProjectService: PersonInProjectService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PersonUpdateComponent],
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
      .overrideTemplate(PersonUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PersonUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    personFormService = TestBed.inject(PersonFormService);
    personService = TestBed.inject(PersonService);
    eunTeamMemberService = TestBed.inject(EunTeamMemberService);
    eventParticipantService = TestBed.inject(EventParticipantService);
    personInOrganizationService = TestBed.inject(PersonInOrganizationService);
    personInProjectService = TestBed.inject(PersonInProjectService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call EunTeamMember query and add missing value', () => {
      const person: IPerson = { id: 456 };
      const eunTeamMember: IEunTeamMember = { id: 54364 };
      person.eunTeamMember = eunTeamMember;

      const eunTeamMemberCollection: IEunTeamMember[] = [{ id: 77881 }];
      jest.spyOn(eunTeamMemberService, 'query').mockReturnValue(of(new HttpResponse({ body: eunTeamMemberCollection })));
      const additionalEunTeamMembers = [eunTeamMember];
      const expectedCollection: IEunTeamMember[] = [...additionalEunTeamMembers, ...eunTeamMemberCollection];
      jest.spyOn(eunTeamMemberService, 'addEunTeamMemberToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ person });
      comp.ngOnInit();

      expect(eunTeamMemberService.query).toHaveBeenCalled();
      expect(eunTeamMemberService.addEunTeamMemberToCollectionIfMissing).toHaveBeenCalledWith(
        eunTeamMemberCollection,
        ...additionalEunTeamMembers.map(expect.objectContaining)
      );
      expect(comp.eunTeamMembersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call EventParticipant query and add missing value', () => {
      const person: IPerson = { id: 456 };
      const eventParticipant: IEventParticipant = { id: 90919 };
      person.eventParticipant = eventParticipant;

      const eventParticipantCollection: IEventParticipant[] = [{ id: 4644 }];
      jest.spyOn(eventParticipantService, 'query').mockReturnValue(of(new HttpResponse({ body: eventParticipantCollection })));
      const additionalEventParticipants = [eventParticipant];
      const expectedCollection: IEventParticipant[] = [...additionalEventParticipants, ...eventParticipantCollection];
      jest.spyOn(eventParticipantService, 'addEventParticipantToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ person });
      comp.ngOnInit();

      expect(eventParticipantService.query).toHaveBeenCalled();
      expect(eventParticipantService.addEventParticipantToCollectionIfMissing).toHaveBeenCalledWith(
        eventParticipantCollection,
        ...additionalEventParticipants.map(expect.objectContaining)
      );
      expect(comp.eventParticipantsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call PersonInOrganization query and add missing value', () => {
      const person: IPerson = { id: 456 };
      const personInOrganization: IPersonInOrganization = { id: 99816 };
      person.personInOrganization = personInOrganization;

      const personInOrganizationCollection: IPersonInOrganization[] = [{ id: 82123 }];
      jest.spyOn(personInOrganizationService, 'query').mockReturnValue(of(new HttpResponse({ body: personInOrganizationCollection })));
      const additionalPersonInOrganizations = [personInOrganization];
      const expectedCollection: IPersonInOrganization[] = [...additionalPersonInOrganizations, ...personInOrganizationCollection];
      jest.spyOn(personInOrganizationService, 'addPersonInOrganizationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ person });
      comp.ngOnInit();

      expect(personInOrganizationService.query).toHaveBeenCalled();
      expect(personInOrganizationService.addPersonInOrganizationToCollectionIfMissing).toHaveBeenCalledWith(
        personInOrganizationCollection,
        ...additionalPersonInOrganizations.map(expect.objectContaining)
      );
      expect(comp.personInOrganizationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call PersonInProject query and add missing value', () => {
      const person: IPerson = { id: 456 };
      const personInProject: IPersonInProject = { id: 24070 };
      person.personInProject = personInProject;

      const personInProjectCollection: IPersonInProject[] = [{ id: 72443 }];
      jest.spyOn(personInProjectService, 'query').mockReturnValue(of(new HttpResponse({ body: personInProjectCollection })));
      const additionalPersonInProjects = [personInProject];
      const expectedCollection: IPersonInProject[] = [...additionalPersonInProjects, ...personInProjectCollection];
      jest.spyOn(personInProjectService, 'addPersonInProjectToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ person });
      comp.ngOnInit();

      expect(personInProjectService.query).toHaveBeenCalled();
      expect(personInProjectService.addPersonInProjectToCollectionIfMissing).toHaveBeenCalledWith(
        personInProjectCollection,
        ...additionalPersonInProjects.map(expect.objectContaining)
      );
      expect(comp.personInProjectsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const person: IPerson = { id: 456 };
      const eunTeamMember: IEunTeamMember = { id: 80596 };
      person.eunTeamMember = eunTeamMember;
      const eventParticipant: IEventParticipant = { id: 61172 };
      person.eventParticipant = eventParticipant;
      const personInOrganization: IPersonInOrganization = { id: 66764 };
      person.personInOrganization = personInOrganization;
      const personInProject: IPersonInProject = { id: 85125 };
      person.personInProject = personInProject;

      activatedRoute.data = of({ person });
      comp.ngOnInit();

      expect(comp.eunTeamMembersSharedCollection).toContain(eunTeamMember);
      expect(comp.eventParticipantsSharedCollection).toContain(eventParticipant);
      expect(comp.personInOrganizationsSharedCollection).toContain(personInOrganization);
      expect(comp.personInProjectsSharedCollection).toContain(personInProject);
      expect(comp.person).toEqual(person);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPerson>>();
      const person = { id: 123 };
      jest.spyOn(personFormService, 'getPerson').mockReturnValue(person);
      jest.spyOn(personService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ person });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: person }));
      saveSubject.complete();

      // THEN
      expect(personFormService.getPerson).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(personService.update).toHaveBeenCalledWith(expect.objectContaining(person));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPerson>>();
      const person = { id: 123 };
      jest.spyOn(personFormService, 'getPerson').mockReturnValue({ id: null });
      jest.spyOn(personService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ person: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: person }));
      saveSubject.complete();

      // THEN
      expect(personFormService.getPerson).toHaveBeenCalled();
      expect(personService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPerson>>();
      const person = { id: 123 };
      jest.spyOn(personService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ person });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(personService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareEunTeamMember', () => {
      it('Should forward to eunTeamMemberService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(eunTeamMemberService, 'compareEunTeamMember');
        comp.compareEunTeamMember(entity, entity2);
        expect(eunTeamMemberService.compareEunTeamMember).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareEventParticipant', () => {
      it('Should forward to eventParticipantService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(eventParticipantService, 'compareEventParticipant');
        comp.compareEventParticipant(entity, entity2);
        expect(eventParticipantService.compareEventParticipant).toHaveBeenCalledWith(entity, entity2);
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

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { EventFormService } from './event-form.service';
import { EventService } from '../service/event.service';
import { IEvent } from '../event.model';
import { IEventInOrganization } from 'app/entities/event-in-organization/event-in-organization.model';
import { EventInOrganizationService } from 'app/entities/event-in-organization/service/event-in-organization.service';
import { IEventParticipant } from 'app/entities/event-participant/event-participant.model';
import { EventParticipantService } from 'app/entities/event-participant/service/event-participant.service';

import { EventUpdateComponent } from './event-update.component';

describe('Event Management Update Component', () => {
  let comp: EventUpdateComponent;
  let fixture: ComponentFixture<EventUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let eventFormService: EventFormService;
  let eventService: EventService;
  let eventInOrganizationService: EventInOrganizationService;
  let eventParticipantService: EventParticipantService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [EventUpdateComponent],
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
      .overrideTemplate(EventUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EventUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    eventFormService = TestBed.inject(EventFormService);
    eventService = TestBed.inject(EventService);
    eventInOrganizationService = TestBed.inject(EventInOrganizationService);
    eventParticipantService = TestBed.inject(EventParticipantService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call EventInOrganization query and add missing value', () => {
      const event: IEvent = { id: 456 };
      const eventInOrganization: IEventInOrganization = { id: 94219 };
      event.eventInOrganization = eventInOrganization;

      const eventInOrganizationCollection: IEventInOrganization[] = [{ id: 24994 }];
      jest.spyOn(eventInOrganizationService, 'query').mockReturnValue(of(new HttpResponse({ body: eventInOrganizationCollection })));
      const additionalEventInOrganizations = [eventInOrganization];
      const expectedCollection: IEventInOrganization[] = [...additionalEventInOrganizations, ...eventInOrganizationCollection];
      jest.spyOn(eventInOrganizationService, 'addEventInOrganizationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ event });
      comp.ngOnInit();

      expect(eventInOrganizationService.query).toHaveBeenCalled();
      expect(eventInOrganizationService.addEventInOrganizationToCollectionIfMissing).toHaveBeenCalledWith(
        eventInOrganizationCollection,
        ...additionalEventInOrganizations.map(expect.objectContaining)
      );
      expect(comp.eventInOrganizationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call EventParticipant query and add missing value', () => {
      const event: IEvent = { id: 456 };
      const eventParticipant: IEventParticipant = { id: 49411 };
      event.eventParticipant = eventParticipant;

      const eventParticipantCollection: IEventParticipant[] = [{ id: 82340 }];
      jest.spyOn(eventParticipantService, 'query').mockReturnValue(of(new HttpResponse({ body: eventParticipantCollection })));
      const additionalEventParticipants = [eventParticipant];
      const expectedCollection: IEventParticipant[] = [...additionalEventParticipants, ...eventParticipantCollection];
      jest.spyOn(eventParticipantService, 'addEventParticipantToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ event });
      comp.ngOnInit();

      expect(eventParticipantService.query).toHaveBeenCalled();
      expect(eventParticipantService.addEventParticipantToCollectionIfMissing).toHaveBeenCalledWith(
        eventParticipantCollection,
        ...additionalEventParticipants.map(expect.objectContaining)
      );
      expect(comp.eventParticipantsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const event: IEvent = { id: 456 };
      const eventInOrganization: IEventInOrganization = { id: 1146 };
      event.eventInOrganization = eventInOrganization;
      const eventParticipant: IEventParticipant = { id: 44975 };
      event.eventParticipant = eventParticipant;

      activatedRoute.data = of({ event });
      comp.ngOnInit();

      expect(comp.eventInOrganizationsSharedCollection).toContain(eventInOrganization);
      expect(comp.eventParticipantsSharedCollection).toContain(eventParticipant);
      expect(comp.event).toEqual(event);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEvent>>();
      const event = { id: 123 };
      jest.spyOn(eventFormService, 'getEvent').mockReturnValue(event);
      jest.spyOn(eventService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ event });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: event }));
      saveSubject.complete();

      // THEN
      expect(eventFormService.getEvent).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(eventService.update).toHaveBeenCalledWith(expect.objectContaining(event));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEvent>>();
      const event = { id: 123 };
      jest.spyOn(eventFormService, 'getEvent').mockReturnValue({ id: null });
      jest.spyOn(eventService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ event: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: event }));
      saveSubject.complete();

      // THEN
      expect(eventFormService.getEvent).toHaveBeenCalled();
      expect(eventService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEvent>>();
      const event = { id: 123 };
      jest.spyOn(eventService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ event });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(eventService.update).toHaveBeenCalled();
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

    describe('compareEventParticipant', () => {
      it('Should forward to eventParticipantService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(eventParticipantService, 'compareEventParticipant');
        comp.compareEventParticipant(entity, entity2);
        expect(eventParticipantService.compareEventParticipant).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});

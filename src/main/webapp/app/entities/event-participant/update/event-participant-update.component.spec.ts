import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { EventParticipantFormService } from './event-participant-form.service';
import { EventParticipantService } from '../service/event-participant.service';
import { IEventParticipant } from '../event-participant.model';
import { IEvent } from 'app/entities/event/event.model';
import { EventService } from 'app/entities/event/service/event.service';
import { IPerson } from 'app/entities/person/person.model';
import { PersonService } from 'app/entities/person/service/person.service';

import { EventParticipantUpdateComponent } from './event-participant-update.component';

describe('EventParticipant Management Update Component', () => {
  let comp: EventParticipantUpdateComponent;
  let fixture: ComponentFixture<EventParticipantUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let eventParticipantFormService: EventParticipantFormService;
  let eventParticipantService: EventParticipantService;
  let eventService: EventService;
  let personService: PersonService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [EventParticipantUpdateComponent],
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
      .overrideTemplate(EventParticipantUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EventParticipantUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    eventParticipantFormService = TestBed.inject(EventParticipantFormService);
    eventParticipantService = TestBed.inject(EventParticipantService);
    eventService = TestBed.inject(EventService);
    personService = TestBed.inject(PersonService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Event query and add missing value', () => {
      const eventParticipant: IEventParticipant = { id: 456 };
      const event: IEvent = { id: 60170 };
      eventParticipant.event = event;

      const eventCollection: IEvent[] = [{ id: 83530 }];
      jest.spyOn(eventService, 'query').mockReturnValue(of(new HttpResponse({ body: eventCollection })));
      const additionalEvents = [event];
      const expectedCollection: IEvent[] = [...additionalEvents, ...eventCollection];
      jest.spyOn(eventService, 'addEventToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ eventParticipant });
      comp.ngOnInit();

      expect(eventService.query).toHaveBeenCalled();
      expect(eventService.addEventToCollectionIfMissing).toHaveBeenCalledWith(
        eventCollection,
        ...additionalEvents.map(expect.objectContaining)
      );
      expect(comp.eventsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Person query and add missing value', () => {
      const eventParticipant: IEventParticipant = { id: 456 };
      const person: IPerson = { id: 77792 };
      eventParticipant.person = person;

      const personCollection: IPerson[] = [{ id: 24201 }];
      jest.spyOn(personService, 'query').mockReturnValue(of(new HttpResponse({ body: personCollection })));
      const additionalPeople = [person];
      const expectedCollection: IPerson[] = [...additionalPeople, ...personCollection];
      jest.spyOn(personService, 'addPersonToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ eventParticipant });
      comp.ngOnInit();

      expect(personService.query).toHaveBeenCalled();
      expect(personService.addPersonToCollectionIfMissing).toHaveBeenCalledWith(
        personCollection,
        ...additionalPeople.map(expect.objectContaining)
      );
      expect(comp.peopleSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const eventParticipant: IEventParticipant = { id: 456 };
      const event: IEvent = { id: 26026 };
      eventParticipant.event = event;
      const person: IPerson = { id: 85756 };
      eventParticipant.person = person;

      activatedRoute.data = of({ eventParticipant });
      comp.ngOnInit();

      expect(comp.eventsSharedCollection).toContain(event);
      expect(comp.peopleSharedCollection).toContain(person);
      expect(comp.eventParticipant).toEqual(eventParticipant);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEventParticipant>>();
      const eventParticipant = { id: 123 };
      jest.spyOn(eventParticipantFormService, 'getEventParticipant').mockReturnValue(eventParticipant);
      jest.spyOn(eventParticipantService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ eventParticipant });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: eventParticipant }));
      saveSubject.complete();

      // THEN
      expect(eventParticipantFormService.getEventParticipant).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(eventParticipantService.update).toHaveBeenCalledWith(expect.objectContaining(eventParticipant));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEventParticipant>>();
      const eventParticipant = { id: 123 };
      jest.spyOn(eventParticipantFormService, 'getEventParticipant').mockReturnValue({ id: null });
      jest.spyOn(eventParticipantService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ eventParticipant: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: eventParticipant }));
      saveSubject.complete();

      // THEN
      expect(eventParticipantFormService.getEventParticipant).toHaveBeenCalled();
      expect(eventParticipantService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEventParticipant>>();
      const eventParticipant = { id: 123 };
      jest.spyOn(eventParticipantService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ eventParticipant });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(eventParticipantService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareEvent', () => {
      it('Should forward to eventService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(eventService, 'compareEvent');
        comp.compareEvent(entity, entity2);
        expect(eventService.compareEvent).toHaveBeenCalledWith(entity, entity2);
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

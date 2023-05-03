import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { EventInOrganizationFormService } from './event-in-organization-form.service';
import { EventInOrganizationService } from '../service/event-in-organization.service';
import { IEventInOrganization } from '../event-in-organization.model';
import { IEvent } from 'app/entities/event/event.model';
import { EventService } from 'app/entities/event/service/event.service';
import { IOrganization } from 'app/entities/organization/organization.model';
import { OrganizationService } from 'app/entities/organization/service/organization.service';

import { EventInOrganizationUpdateComponent } from './event-in-organization-update.component';

describe('EventInOrganization Management Update Component', () => {
  let comp: EventInOrganizationUpdateComponent;
  let fixture: ComponentFixture<EventInOrganizationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let eventInOrganizationFormService: EventInOrganizationFormService;
  let eventInOrganizationService: EventInOrganizationService;
  let eventService: EventService;
  let organizationService: OrganizationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [EventInOrganizationUpdateComponent],
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
      .overrideTemplate(EventInOrganizationUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EventInOrganizationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    eventInOrganizationFormService = TestBed.inject(EventInOrganizationFormService);
    eventInOrganizationService = TestBed.inject(EventInOrganizationService);
    eventService = TestBed.inject(EventService);
    organizationService = TestBed.inject(OrganizationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Event query and add missing value', () => {
      const eventInOrganization: IEventInOrganization = { id: 456 };
      const event: IEvent = { id: 39680 };
      eventInOrganization.event = event;

      const eventCollection: IEvent[] = [{ id: 43518 }];
      jest.spyOn(eventService, 'query').mockReturnValue(of(new HttpResponse({ body: eventCollection })));
      const additionalEvents = [event];
      const expectedCollection: IEvent[] = [...additionalEvents, ...eventCollection];
      jest.spyOn(eventService, 'addEventToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ eventInOrganization });
      comp.ngOnInit();

      expect(eventService.query).toHaveBeenCalled();
      expect(eventService.addEventToCollectionIfMissing).toHaveBeenCalledWith(
        eventCollection,
        ...additionalEvents.map(expect.objectContaining)
      );
      expect(comp.eventsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Organization query and add missing value', () => {
      const eventInOrganization: IEventInOrganization = { id: 456 };
      const organization: IOrganization = { id: 53674 };
      eventInOrganization.organization = organization;

      const organizationCollection: IOrganization[] = [{ id: 4978 }];
      jest.spyOn(organizationService, 'query').mockReturnValue(of(new HttpResponse({ body: organizationCollection })));
      const additionalOrganizations = [organization];
      const expectedCollection: IOrganization[] = [...additionalOrganizations, ...organizationCollection];
      jest.spyOn(organizationService, 'addOrganizationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ eventInOrganization });
      comp.ngOnInit();

      expect(organizationService.query).toHaveBeenCalled();
      expect(organizationService.addOrganizationToCollectionIfMissing).toHaveBeenCalledWith(
        organizationCollection,
        ...additionalOrganizations.map(expect.objectContaining)
      );
      expect(comp.organizationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const eventInOrganization: IEventInOrganization = { id: 456 };
      const event: IEvent = { id: 3046 };
      eventInOrganization.event = event;
      const organization: IOrganization = { id: 2177 };
      eventInOrganization.organization = organization;

      activatedRoute.data = of({ eventInOrganization });
      comp.ngOnInit();

      expect(comp.eventsSharedCollection).toContain(event);
      expect(comp.organizationsSharedCollection).toContain(organization);
      expect(comp.eventInOrganization).toEqual(eventInOrganization);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEventInOrganization>>();
      const eventInOrganization = { id: 123 };
      jest.spyOn(eventInOrganizationFormService, 'getEventInOrganization').mockReturnValue(eventInOrganization);
      jest.spyOn(eventInOrganizationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ eventInOrganization });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: eventInOrganization }));
      saveSubject.complete();

      // THEN
      expect(eventInOrganizationFormService.getEventInOrganization).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(eventInOrganizationService.update).toHaveBeenCalledWith(expect.objectContaining(eventInOrganization));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEventInOrganization>>();
      const eventInOrganization = { id: 123 };
      jest.spyOn(eventInOrganizationFormService, 'getEventInOrganization').mockReturnValue({ id: null });
      jest.spyOn(eventInOrganizationService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ eventInOrganization: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: eventInOrganization }));
      saveSubject.complete();

      // THEN
      expect(eventInOrganizationFormService.getEventInOrganization).toHaveBeenCalled();
      expect(eventInOrganizationService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEventInOrganization>>();
      const eventInOrganization = { id: 123 };
      jest.spyOn(eventInOrganizationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ eventInOrganization });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(eventInOrganizationService.update).toHaveBeenCalled();
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

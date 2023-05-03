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

import { EventParticipantUpdateComponent } from './event-participant-update.component';

describe('EventParticipant Management Update Component', () => {
  let comp: EventParticipantUpdateComponent;
  let fixture: ComponentFixture<EventParticipantUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let eventParticipantFormService: EventParticipantFormService;
  let eventParticipantService: EventParticipantService;

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

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const eventParticipant: IEventParticipant = { id: 456 };

      activatedRoute.data = of({ eventParticipant });
      comp.ngOnInit();

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
});

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { EventReferencesParticipantsCategoryFormService } from './event-references-participants-category-form.service';
import { EventReferencesParticipantsCategoryService } from '../service/event-references-participants-category.service';
import { IEventReferencesParticipantsCategory } from '../event-references-participants-category.model';
import { IEventReferences } from 'app/entities/event-references/event-references.model';
import { EventReferencesService } from 'app/entities/event-references/service/event-references.service';

import { EventReferencesParticipantsCategoryUpdateComponent } from './event-references-participants-category-update.component';

describe('EventReferencesParticipantsCategory Management Update Component', () => {
  let comp: EventReferencesParticipantsCategoryUpdateComponent;
  let fixture: ComponentFixture<EventReferencesParticipantsCategoryUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let eventReferencesParticipantsCategoryFormService: EventReferencesParticipantsCategoryFormService;
  let eventReferencesParticipantsCategoryService: EventReferencesParticipantsCategoryService;
  let eventReferencesService: EventReferencesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [EventReferencesParticipantsCategoryUpdateComponent],
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
      .overrideTemplate(EventReferencesParticipantsCategoryUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EventReferencesParticipantsCategoryUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    eventReferencesParticipantsCategoryFormService = TestBed.inject(EventReferencesParticipantsCategoryFormService);
    eventReferencesParticipantsCategoryService = TestBed.inject(EventReferencesParticipantsCategoryService);
    eventReferencesService = TestBed.inject(EventReferencesService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call EventReferences query and add missing value', () => {
      const eventReferencesParticipantsCategory: IEventReferencesParticipantsCategory = { id: 456 };
      const eventReference: IEventReferences = { id: 91629 };
      eventReferencesParticipantsCategory.eventReference = eventReference;

      const eventReferencesCollection: IEventReferences[] = [{ id: 43904 }];
      jest.spyOn(eventReferencesService, 'query').mockReturnValue(of(new HttpResponse({ body: eventReferencesCollection })));
      const additionalEventReferences = [eventReference];
      const expectedCollection: IEventReferences[] = [...additionalEventReferences, ...eventReferencesCollection];
      jest.spyOn(eventReferencesService, 'addEventReferencesToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ eventReferencesParticipantsCategory });
      comp.ngOnInit();

      expect(eventReferencesService.query).toHaveBeenCalled();
      expect(eventReferencesService.addEventReferencesToCollectionIfMissing).toHaveBeenCalledWith(
        eventReferencesCollection,
        ...additionalEventReferences.map(expect.objectContaining)
      );
      expect(comp.eventReferencesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const eventReferencesParticipantsCategory: IEventReferencesParticipantsCategory = { id: 456 };
      const eventReference: IEventReferences = { id: 12584 };
      eventReferencesParticipantsCategory.eventReference = eventReference;

      activatedRoute.data = of({ eventReferencesParticipantsCategory });
      comp.ngOnInit();

      expect(comp.eventReferencesSharedCollection).toContain(eventReference);
      expect(comp.eventReferencesParticipantsCategory).toEqual(eventReferencesParticipantsCategory);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEventReferencesParticipantsCategory>>();
      const eventReferencesParticipantsCategory = { id: 123 };
      jest
        .spyOn(eventReferencesParticipantsCategoryFormService, 'getEventReferencesParticipantsCategory')
        .mockReturnValue(eventReferencesParticipantsCategory);
      jest.spyOn(eventReferencesParticipantsCategoryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ eventReferencesParticipantsCategory });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: eventReferencesParticipantsCategory }));
      saveSubject.complete();

      // THEN
      expect(eventReferencesParticipantsCategoryFormService.getEventReferencesParticipantsCategory).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(eventReferencesParticipantsCategoryService.update).toHaveBeenCalledWith(
        expect.objectContaining(eventReferencesParticipantsCategory)
      );
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEventReferencesParticipantsCategory>>();
      const eventReferencesParticipantsCategory = { id: 123 };
      jest.spyOn(eventReferencesParticipantsCategoryFormService, 'getEventReferencesParticipantsCategory').mockReturnValue({ id: null });
      jest.spyOn(eventReferencesParticipantsCategoryService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ eventReferencesParticipantsCategory: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: eventReferencesParticipantsCategory }));
      saveSubject.complete();

      // THEN
      expect(eventReferencesParticipantsCategoryFormService.getEventReferencesParticipantsCategory).toHaveBeenCalled();
      expect(eventReferencesParticipantsCategoryService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEventReferencesParticipantsCategory>>();
      const eventReferencesParticipantsCategory = { id: 123 };
      jest.spyOn(eventReferencesParticipantsCategoryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ eventReferencesParticipantsCategory });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(eventReferencesParticipantsCategoryService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareEventReferences', () => {
      it('Should forward to eventReferencesService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(eventReferencesService, 'compareEventReferences');
        comp.compareEventReferences(entity, entity2);
        expect(eventReferencesService.compareEventReferences).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});

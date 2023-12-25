import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { EventReferencesFormService } from './event-references-form.service';
import { EventReferencesService } from '../service/event-references.service';
import { IEventReferences } from '../event-references.model';

import { EventReferencesUpdateComponent } from './event-references-update.component';

describe('EventReferences Management Update Component', () => {
  let comp: EventReferencesUpdateComponent;
  let fixture: ComponentFixture<EventReferencesUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let eventReferencesFormService: EventReferencesFormService;
  let eventReferencesService: EventReferencesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [EventReferencesUpdateComponent],
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
      .overrideTemplate(EventReferencesUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EventReferencesUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    eventReferencesFormService = TestBed.inject(EventReferencesFormService);
    eventReferencesService = TestBed.inject(EventReferencesService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const eventReferences: IEventReferences = { id: 456 };

      activatedRoute.data = of({ eventReferences });
      comp.ngOnInit();

      expect(comp.eventReferences).toEqual(eventReferences);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEventReferences>>();
      const eventReferences = { id: 123 };
      jest.spyOn(eventReferencesFormService, 'getEventReferences').mockReturnValue(eventReferences);
      jest.spyOn(eventReferencesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ eventReferences });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: eventReferences }));
      saveSubject.complete();

      // THEN
      expect(eventReferencesFormService.getEventReferences).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(eventReferencesService.update).toHaveBeenCalledWith(expect.objectContaining(eventReferences));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEventReferences>>();
      const eventReferences = { id: 123 };
      jest.spyOn(eventReferencesFormService, 'getEventReferences').mockReturnValue({ id: null });
      jest.spyOn(eventReferencesService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ eventReferences: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: eventReferences }));
      saveSubject.complete();

      // THEN
      expect(eventReferencesFormService.getEventReferences).toHaveBeenCalled();
      expect(eventReferencesService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEventReferences>>();
      const eventReferences = { id: 123 };
      jest.spyOn(eventReferencesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ eventReferences });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(eventReferencesService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});

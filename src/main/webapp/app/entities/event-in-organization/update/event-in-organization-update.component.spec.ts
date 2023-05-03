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

import { EventInOrganizationUpdateComponent } from './event-in-organization-update.component';

describe('EventInOrganization Management Update Component', () => {
  let comp: EventInOrganizationUpdateComponent;
  let fixture: ComponentFixture<EventInOrganizationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let eventInOrganizationFormService: EventInOrganizationFormService;
  let eventInOrganizationService: EventInOrganizationService;

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

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const eventInOrganization: IEventInOrganization = { id: 456 };

      activatedRoute.data = of({ eventInOrganization });
      comp.ngOnInit();

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
});

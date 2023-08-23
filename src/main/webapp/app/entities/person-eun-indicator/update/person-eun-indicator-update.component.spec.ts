import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PersonEunIndicatorFormService } from './person-eun-indicator-form.service';
import { PersonEunIndicatorService } from '../service/person-eun-indicator.service';
import { IPersonEunIndicator } from '../person-eun-indicator.model';

import { PersonEunIndicatorUpdateComponent } from './person-eun-indicator-update.component';

describe('PersonEunIndicator Management Update Component', () => {
  let comp: PersonEunIndicatorUpdateComponent;
  let fixture: ComponentFixture<PersonEunIndicatorUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let personEunIndicatorFormService: PersonEunIndicatorFormService;
  let personEunIndicatorService: PersonEunIndicatorService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PersonEunIndicatorUpdateComponent],
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
      .overrideTemplate(PersonEunIndicatorUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PersonEunIndicatorUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    personEunIndicatorFormService = TestBed.inject(PersonEunIndicatorFormService);
    personEunIndicatorService = TestBed.inject(PersonEunIndicatorService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const personEunIndicator: IPersonEunIndicator = { id: 456 };

      activatedRoute.data = of({ personEunIndicator });
      comp.ngOnInit();

      expect(comp.personEunIndicator).toEqual(personEunIndicator);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPersonEunIndicator>>();
      const personEunIndicator = { id: 123 };
      jest.spyOn(personEunIndicatorFormService, 'getPersonEunIndicator').mockReturnValue(personEunIndicator);
      jest.spyOn(personEunIndicatorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ personEunIndicator });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: personEunIndicator }));
      saveSubject.complete();

      // THEN
      expect(personEunIndicatorFormService.getPersonEunIndicator).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(personEunIndicatorService.update).toHaveBeenCalledWith(expect.objectContaining(personEunIndicator));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPersonEunIndicator>>();
      const personEunIndicator = { id: 123 };
      jest.spyOn(personEunIndicatorFormService, 'getPersonEunIndicator').mockReturnValue({ id: null });
      jest.spyOn(personEunIndicatorService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ personEunIndicator: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: personEunIndicator }));
      saveSubject.complete();

      // THEN
      expect(personEunIndicatorFormService.getPersonEunIndicator).toHaveBeenCalled();
      expect(personEunIndicatorService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPersonEunIndicator>>();
      const personEunIndicator = { id: 123 };
      jest.spyOn(personEunIndicatorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ personEunIndicator });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(personEunIndicatorService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});

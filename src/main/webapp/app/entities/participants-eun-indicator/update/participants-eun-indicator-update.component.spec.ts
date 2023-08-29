import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ParticipantsEunIndicatorFormService } from './participants-eun-indicator-form.service';
import { ParticipantsEunIndicatorService } from '../service/participants-eun-indicator.service';
import { IParticipantsEunIndicator } from '../participants-eun-indicator.model';

import { ParticipantsEunIndicatorUpdateComponent } from './participants-eun-indicator-update.component';

describe('ParticipantsEunIndicator Management Update Component', () => {
  let comp: ParticipantsEunIndicatorUpdateComponent;
  let fixture: ComponentFixture<ParticipantsEunIndicatorUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let participantsEunIndicatorFormService: ParticipantsEunIndicatorFormService;
  let participantsEunIndicatorService: ParticipantsEunIndicatorService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ParticipantsEunIndicatorUpdateComponent],
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
      .overrideTemplate(ParticipantsEunIndicatorUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ParticipantsEunIndicatorUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    participantsEunIndicatorFormService = TestBed.inject(ParticipantsEunIndicatorFormService);
    participantsEunIndicatorService = TestBed.inject(ParticipantsEunIndicatorService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const participantsEunIndicator: IParticipantsEunIndicator = { id: 456 };

      activatedRoute.data = of({ participantsEunIndicator });
      comp.ngOnInit();

      expect(comp.participantsEunIndicator).toEqual(participantsEunIndicator);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IParticipantsEunIndicator>>();
      const participantsEunIndicator = { id: 123 };
      jest.spyOn(participantsEunIndicatorFormService, 'getParticipantsEunIndicator').mockReturnValue(participantsEunIndicator);
      jest.spyOn(participantsEunIndicatorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ participantsEunIndicator });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: participantsEunIndicator }));
      saveSubject.complete();

      // THEN
      expect(participantsEunIndicatorFormService.getParticipantsEunIndicator).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(participantsEunIndicatorService.update).toHaveBeenCalledWith(expect.objectContaining(participantsEunIndicator));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IParticipantsEunIndicator>>();
      const participantsEunIndicator = { id: 123 };
      jest.spyOn(participantsEunIndicatorFormService, 'getParticipantsEunIndicator').mockReturnValue({ id: null });
      jest.spyOn(participantsEunIndicatorService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ participantsEunIndicator: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: participantsEunIndicator }));
      saveSubject.complete();

      // THEN
      expect(participantsEunIndicatorFormService.getParticipantsEunIndicator).toHaveBeenCalled();
      expect(participantsEunIndicatorService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IParticipantsEunIndicator>>();
      const participantsEunIndicator = { id: 123 };
      jest.spyOn(participantsEunIndicatorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ participantsEunIndicator });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(participantsEunIndicatorService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});

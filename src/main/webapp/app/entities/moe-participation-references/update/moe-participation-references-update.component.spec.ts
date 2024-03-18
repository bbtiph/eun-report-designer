import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { MOEParticipationReferencesFormService } from './moe-participation-references-form.service';
import { MOEParticipationReferencesService } from '../service/moe-participation-references.service';
import { IMOEParticipationReferences } from '../moe-participation-references.model';

import { MOEParticipationReferencesUpdateComponent } from './moe-participation-references-update.component';

describe('MOEParticipationReferences Management Update Component', () => {
  let comp: MOEParticipationReferencesUpdateComponent;
  let fixture: ComponentFixture<MOEParticipationReferencesUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let mOEParticipationReferencesFormService: MOEParticipationReferencesFormService;
  let mOEParticipationReferencesService: MOEParticipationReferencesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [MOEParticipationReferencesUpdateComponent],
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
      .overrideTemplate(MOEParticipationReferencesUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MOEParticipationReferencesUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    mOEParticipationReferencesFormService = TestBed.inject(MOEParticipationReferencesFormService);
    mOEParticipationReferencesService = TestBed.inject(MOEParticipationReferencesService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const mOEParticipationReferences: IMOEParticipationReferences = { id: 456 };

      activatedRoute.data = of({ mOEParticipationReferences });
      comp.ngOnInit();

      expect(comp.mOEParticipationReferences).toEqual(mOEParticipationReferences);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMOEParticipationReferences>>();
      const mOEParticipationReferences = { id: 123 };
      jest.spyOn(mOEParticipationReferencesFormService, 'getMOEParticipationReferences').mockReturnValue(mOEParticipationReferences);
      jest.spyOn(mOEParticipationReferencesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mOEParticipationReferences });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: mOEParticipationReferences }));
      saveSubject.complete();

      // THEN
      expect(mOEParticipationReferencesFormService.getMOEParticipationReferences).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(mOEParticipationReferencesService.update).toHaveBeenCalledWith(expect.objectContaining(mOEParticipationReferences));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMOEParticipationReferences>>();
      const mOEParticipationReferences = { id: 123 };
      jest.spyOn(mOEParticipationReferencesFormService, 'getMOEParticipationReferences').mockReturnValue({ id: null });
      jest.spyOn(mOEParticipationReferencesService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mOEParticipationReferences: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: mOEParticipationReferences }));
      saveSubject.complete();

      // THEN
      expect(mOEParticipationReferencesFormService.getMOEParticipationReferences).toHaveBeenCalled();
      expect(mOEParticipationReferencesService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMOEParticipationReferences>>();
      const mOEParticipationReferences = { id: 123 };
      jest.spyOn(mOEParticipationReferencesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mOEParticipationReferences });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(mOEParticipationReferencesService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});

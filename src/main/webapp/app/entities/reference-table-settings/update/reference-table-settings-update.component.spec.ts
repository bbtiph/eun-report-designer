import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ReferenceTableSettingsFormService } from './reference-table-settings-form.service';
import { ReferenceTableSettingsService } from '../service/reference-table-settings.service';
import { IReferenceTableSettings } from '../reference-table-settings.model';

import { ReferenceTableSettingsUpdateComponent } from './reference-table-settings-update.component';

describe('ReferenceTableSettings Management Update Component', () => {
  let comp: ReferenceTableSettingsUpdateComponent;
  let fixture: ComponentFixture<ReferenceTableSettingsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let referenceTableSettingsFormService: ReferenceTableSettingsFormService;
  let referenceTableSettingsService: ReferenceTableSettingsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ReferenceTableSettingsUpdateComponent],
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
      .overrideTemplate(ReferenceTableSettingsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ReferenceTableSettingsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    referenceTableSettingsFormService = TestBed.inject(ReferenceTableSettingsFormService);
    referenceTableSettingsService = TestBed.inject(ReferenceTableSettingsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const referenceTableSettings: IReferenceTableSettings = { id: 456 };

      activatedRoute.data = of({ referenceTableSettings });
      comp.ngOnInit();

      expect(comp.referenceTableSettings).toEqual(referenceTableSettings);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IReferenceTableSettings>>();
      const referenceTableSettings = { id: 123 };
      jest.spyOn(referenceTableSettingsFormService, 'getReferenceTableSettings').mockReturnValue(referenceTableSettings);
      jest.spyOn(referenceTableSettingsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ referenceTableSettings });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: referenceTableSettings }));
      saveSubject.complete();

      // THEN
      expect(referenceTableSettingsFormService.getReferenceTableSettings).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(referenceTableSettingsService.update).toHaveBeenCalledWith(expect.objectContaining(referenceTableSettings));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IReferenceTableSettings>>();
      const referenceTableSettings = { id: 123 };
      jest.spyOn(referenceTableSettingsFormService, 'getReferenceTableSettings').mockReturnValue({ id: null });
      jest.spyOn(referenceTableSettingsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ referenceTableSettings: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: referenceTableSettings }));
      saveSubject.complete();

      // THEN
      expect(referenceTableSettingsFormService.getReferenceTableSettings).toHaveBeenCalled();
      expect(referenceTableSettingsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IReferenceTableSettings>>();
      const referenceTableSettings = { id: 123 };
      jest.spyOn(referenceTableSettingsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ referenceTableSettings });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(referenceTableSettingsService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});

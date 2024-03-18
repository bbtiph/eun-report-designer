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
import { ICountries } from 'app/entities/countries/countries.model';
import { CountriesService } from 'app/entities/countries/service/countries.service';

import { MOEParticipationReferencesUpdateComponent } from './moe-participation-references-update.component';

describe('MOEParticipationReferences Management Update Component', () => {
  let comp: MOEParticipationReferencesUpdateComponent;
  let fixture: ComponentFixture<MOEParticipationReferencesUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let mOEParticipationReferencesFormService: MOEParticipationReferencesFormService;
  let mOEParticipationReferencesService: MOEParticipationReferencesService;
  let countriesService: CountriesService;

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
    countriesService = TestBed.inject(CountriesService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Countries query and add missing value', () => {
      const mOEParticipationReferences: IMOEParticipationReferences = { id: 456 };
      const countries: ICountries[] = [{ id: 24651 }];
      mOEParticipationReferences.countries = countries;

      const countriesCollection: ICountries[] = [{ id: 92409 }];
      jest.spyOn(countriesService, 'query').mockReturnValue(of(new HttpResponse({ body: countriesCollection })));
      const additionalCountries = [...countries];
      const expectedCollection: ICountries[] = [...additionalCountries, ...countriesCollection];
      jest.spyOn(countriesService, 'addCountriesToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ mOEParticipationReferences });
      comp.ngOnInit();

      expect(countriesService.query).toHaveBeenCalled();
      expect(countriesService.addCountriesToCollectionIfMissing).toHaveBeenCalledWith(
        countriesCollection,
        ...additionalCountries.map(expect.objectContaining)
      );
      expect(comp.countriesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const mOEParticipationReferences: IMOEParticipationReferences = { id: 456 };
      const countries: ICountries = { id: 68307 };
      mOEParticipationReferences.countries = [countries];

      activatedRoute.data = of({ mOEParticipationReferences });
      comp.ngOnInit();

      expect(comp.countriesSharedCollection).toContain(countries);
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

  describe('Compare relationships', () => {
    describe('compareCountries', () => {
      it('Should forward to countriesService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(countriesService, 'compareCountries');
        comp.compareCountries(entity, entity2);
        expect(countriesService.compareCountries).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});

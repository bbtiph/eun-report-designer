import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { MinistryFormService } from './ministry-form.service';
import { MinistryService } from '../service/ministry.service';
import { IMinistry } from '../ministry.model';
import { ICountries } from 'app/entities/countries/countries.model';
import { CountriesService } from 'app/entities/countries/service/countries.service';

import { MinistryUpdateComponent } from './ministry-update.component';

describe('Ministry Management Update Component', () => {
  let comp: MinistryUpdateComponent;
  let fixture: ComponentFixture<MinistryUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let ministryFormService: MinistryFormService;
  let ministryService: MinistryService;
  let countriesService: CountriesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [MinistryUpdateComponent],
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
      .overrideTemplate(MinistryUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MinistryUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    ministryFormService = TestBed.inject(MinistryFormService);
    ministryService = TestBed.inject(MinistryService);
    countriesService = TestBed.inject(CountriesService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Countries query and add missing value', () => {
      const ministry: IMinistry = { id: 456 };
      const country: ICountries = { id: 77485 };
      ministry.country = country;

      const countriesCollection: ICountries[] = [{ id: 99699 }];
      jest.spyOn(countriesService, 'query').mockReturnValue(of(new HttpResponse({ body: countriesCollection })));
      const additionalCountries = [country];
      const expectedCollection: ICountries[] = [...additionalCountries, ...countriesCollection];
      jest.spyOn(countriesService, 'addCountriesToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ ministry });
      comp.ngOnInit();

      expect(countriesService.query).toHaveBeenCalled();
      expect(countriesService.addCountriesToCollectionIfMissing).toHaveBeenCalledWith(
        countriesCollection,
        ...additionalCountries.map(expect.objectContaining)
      );
      expect(comp.countriesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const ministry: IMinistry = { id: 456 };
      const country: ICountries = { id: 58824 };
      ministry.country = country;

      activatedRoute.data = of({ ministry });
      comp.ngOnInit();

      expect(comp.countriesSharedCollection).toContain(country);
      expect(comp.ministry).toEqual(ministry);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMinistry>>();
      const ministry = { id: 123 };
      jest.spyOn(ministryFormService, 'getMinistry').mockReturnValue(ministry);
      jest.spyOn(ministryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ministry });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: ministry }));
      saveSubject.complete();

      // THEN
      expect(ministryFormService.getMinistry).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(ministryService.update).toHaveBeenCalledWith(expect.objectContaining(ministry));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMinistry>>();
      const ministry = { id: 123 };
      jest.spyOn(ministryFormService, 'getMinistry').mockReturnValue({ id: null });
      jest.spyOn(ministryService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ministry: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: ministry }));
      saveSubject.complete();

      // THEN
      expect(ministryFormService.getMinistry).toHaveBeenCalled();
      expect(ministryService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMinistry>>();
      const ministry = { id: 123 };
      jest.spyOn(ministryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ministry });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(ministryService.update).toHaveBeenCalled();
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

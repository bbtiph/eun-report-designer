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
import { IOrganizationInMinistry } from 'app/entities/organization-in-ministry/organization-in-ministry.model';
import { OrganizationInMinistryService } from 'app/entities/organization-in-ministry/service/organization-in-ministry.service';

import { MinistryUpdateComponent } from './ministry-update.component';

describe('Ministry Management Update Component', () => {
  let comp: MinistryUpdateComponent;
  let fixture: ComponentFixture<MinistryUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let ministryFormService: MinistryFormService;
  let ministryService: MinistryService;
  let organizationInMinistryService: OrganizationInMinistryService;

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
    organizationInMinistryService = TestBed.inject(OrganizationInMinistryService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call OrganizationInMinistry query and add missing value', () => {
      const ministry: IMinistry = { id: 456 };
      const organizationInMinistry: IOrganizationInMinistry = { id: 10182 };
      ministry.organizationInMinistry = organizationInMinistry;

      const organizationInMinistryCollection: IOrganizationInMinistry[] = [{ id: 86863 }];
      jest.spyOn(organizationInMinistryService, 'query').mockReturnValue(of(new HttpResponse({ body: organizationInMinistryCollection })));
      const additionalOrganizationInMinistries = [organizationInMinistry];
      const expectedCollection: IOrganizationInMinistry[] = [...additionalOrganizationInMinistries, ...organizationInMinistryCollection];
      jest.spyOn(organizationInMinistryService, 'addOrganizationInMinistryToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ ministry });
      comp.ngOnInit();

      expect(organizationInMinistryService.query).toHaveBeenCalled();
      expect(organizationInMinistryService.addOrganizationInMinistryToCollectionIfMissing).toHaveBeenCalledWith(
        organizationInMinistryCollection,
        ...additionalOrganizationInMinistries.map(expect.objectContaining)
      );
      expect(comp.organizationInMinistriesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const ministry: IMinistry = { id: 456 };
      const organizationInMinistry: IOrganizationInMinistry = { id: 76489 };
      ministry.organizationInMinistry = organizationInMinistry;

      activatedRoute.data = of({ ministry });
      comp.ngOnInit();

      expect(comp.organizationInMinistriesSharedCollection).toContain(organizationInMinistry);
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
    describe('compareOrganizationInMinistry', () => {
      it('Should forward to organizationInMinistryService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(organizationInMinistryService, 'compareOrganizationInMinistry');
        comp.compareOrganizationInMinistry(entity, entity2);
        expect(organizationInMinistryService.compareOrganizationInMinistry).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { OrganizationInMinistryFormService } from './organization-in-ministry-form.service';
import { OrganizationInMinistryService } from '../service/organization-in-ministry.service';
import { IOrganizationInMinistry } from '../organization-in-ministry.model';
import { IMinistry } from 'app/entities/ministry/ministry.model';
import { MinistryService } from 'app/entities/ministry/service/ministry.service';
import { IOrganization } from 'app/entities/organization/organization.model';
import { OrganizationService } from 'app/entities/organization/service/organization.service';

import { OrganizationInMinistryUpdateComponent } from './organization-in-ministry-update.component';

describe('OrganizationInMinistry Management Update Component', () => {
  let comp: OrganizationInMinistryUpdateComponent;
  let fixture: ComponentFixture<OrganizationInMinistryUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let organizationInMinistryFormService: OrganizationInMinistryFormService;
  let organizationInMinistryService: OrganizationInMinistryService;
  let ministryService: MinistryService;
  let organizationService: OrganizationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [OrganizationInMinistryUpdateComponent],
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
      .overrideTemplate(OrganizationInMinistryUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(OrganizationInMinistryUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    organizationInMinistryFormService = TestBed.inject(OrganizationInMinistryFormService);
    organizationInMinistryService = TestBed.inject(OrganizationInMinistryService);
    ministryService = TestBed.inject(MinistryService);
    organizationService = TestBed.inject(OrganizationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Ministry query and add missing value', () => {
      const organizationInMinistry: IOrganizationInMinistry = { id: 456 };
      const ministry: IMinistry = { id: 16363 };
      organizationInMinistry.ministry = ministry;

      const ministryCollection: IMinistry[] = [{ id: 31159 }];
      jest.spyOn(ministryService, 'query').mockReturnValue(of(new HttpResponse({ body: ministryCollection })));
      const additionalMinistries = [ministry];
      const expectedCollection: IMinistry[] = [...additionalMinistries, ...ministryCollection];
      jest.spyOn(ministryService, 'addMinistryToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ organizationInMinistry });
      comp.ngOnInit();

      expect(ministryService.query).toHaveBeenCalled();
      expect(ministryService.addMinistryToCollectionIfMissing).toHaveBeenCalledWith(
        ministryCollection,
        ...additionalMinistries.map(expect.objectContaining)
      );
      expect(comp.ministriesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Organization query and add missing value', () => {
      const organizationInMinistry: IOrganizationInMinistry = { id: 456 };
      const organization: IOrganization = { id: 68119 };
      organizationInMinistry.organization = organization;

      const organizationCollection: IOrganization[] = [{ id: 53150 }];
      jest.spyOn(organizationService, 'query').mockReturnValue(of(new HttpResponse({ body: organizationCollection })));
      const additionalOrganizations = [organization];
      const expectedCollection: IOrganization[] = [...additionalOrganizations, ...organizationCollection];
      jest.spyOn(organizationService, 'addOrganizationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ organizationInMinistry });
      comp.ngOnInit();

      expect(organizationService.query).toHaveBeenCalled();
      expect(organizationService.addOrganizationToCollectionIfMissing).toHaveBeenCalledWith(
        organizationCollection,
        ...additionalOrganizations.map(expect.objectContaining)
      );
      expect(comp.organizationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const organizationInMinistry: IOrganizationInMinistry = { id: 456 };
      const ministry: IMinistry = { id: 10997 };
      organizationInMinistry.ministry = ministry;
      const organization: IOrganization = { id: 37773 };
      organizationInMinistry.organization = organization;

      activatedRoute.data = of({ organizationInMinistry });
      comp.ngOnInit();

      expect(comp.ministriesSharedCollection).toContain(ministry);
      expect(comp.organizationsSharedCollection).toContain(organization);
      expect(comp.organizationInMinistry).toEqual(organizationInMinistry);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOrganizationInMinistry>>();
      const organizationInMinistry = { id: 123 };
      jest.spyOn(organizationInMinistryFormService, 'getOrganizationInMinistry').mockReturnValue(organizationInMinistry);
      jest.spyOn(organizationInMinistryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ organizationInMinistry });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: organizationInMinistry }));
      saveSubject.complete();

      // THEN
      expect(organizationInMinistryFormService.getOrganizationInMinistry).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(organizationInMinistryService.update).toHaveBeenCalledWith(expect.objectContaining(organizationInMinistry));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOrganizationInMinistry>>();
      const organizationInMinistry = { id: 123 };
      jest.spyOn(organizationInMinistryFormService, 'getOrganizationInMinistry').mockReturnValue({ id: null });
      jest.spyOn(organizationInMinistryService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ organizationInMinistry: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: organizationInMinistry }));
      saveSubject.complete();

      // THEN
      expect(organizationInMinistryFormService.getOrganizationInMinistry).toHaveBeenCalled();
      expect(organizationInMinistryService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOrganizationInMinistry>>();
      const organizationInMinistry = { id: 123 };
      jest.spyOn(organizationInMinistryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ organizationInMinistry });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(organizationInMinistryService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareMinistry', () => {
      it('Should forward to ministryService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(ministryService, 'compareMinistry');
        comp.compareMinistry(entity, entity2);
        expect(ministryService.compareMinistry).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareOrganization', () => {
      it('Should forward to organizationService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(organizationService, 'compareOrganization');
        comp.compareOrganization(entity, entity2);
        expect(organizationService.compareOrganization).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});

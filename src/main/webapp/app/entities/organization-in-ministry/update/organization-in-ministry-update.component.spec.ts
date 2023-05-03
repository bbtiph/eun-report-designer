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

import { OrganizationInMinistryUpdateComponent } from './organization-in-ministry-update.component';

describe('OrganizationInMinistry Management Update Component', () => {
  let comp: OrganizationInMinistryUpdateComponent;
  let fixture: ComponentFixture<OrganizationInMinistryUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let organizationInMinistryFormService: OrganizationInMinistryFormService;
  let organizationInMinistryService: OrganizationInMinistryService;

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

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const organizationInMinistry: IOrganizationInMinistry = { id: 456 };

      activatedRoute.data = of({ organizationInMinistry });
      comp.ngOnInit();

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
});

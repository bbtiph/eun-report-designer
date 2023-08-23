import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { OrganizationEunIndicatorFormService } from './organization-eun-indicator-form.service';
import { OrganizationEunIndicatorService } from '../service/organization-eun-indicator.service';
import { IOrganizationEunIndicator } from '../organization-eun-indicator.model';

import { OrganizationEunIndicatorUpdateComponent } from './organization-eun-indicator-update.component';

describe('OrganizationEunIndicator Management Update Component', () => {
  let comp: OrganizationEunIndicatorUpdateComponent;
  let fixture: ComponentFixture<OrganizationEunIndicatorUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let organizationEunIndicatorFormService: OrganizationEunIndicatorFormService;
  let organizationEunIndicatorService: OrganizationEunIndicatorService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [OrganizationEunIndicatorUpdateComponent],
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
      .overrideTemplate(OrganizationEunIndicatorUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(OrganizationEunIndicatorUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    organizationEunIndicatorFormService = TestBed.inject(OrganizationEunIndicatorFormService);
    organizationEunIndicatorService = TestBed.inject(OrganizationEunIndicatorService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const organizationEunIndicator: IOrganizationEunIndicator = { id: 456 };

      activatedRoute.data = of({ organizationEunIndicator });
      comp.ngOnInit();

      expect(comp.organizationEunIndicator).toEqual(organizationEunIndicator);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOrganizationEunIndicator>>();
      const organizationEunIndicator = { id: 123 };
      jest.spyOn(organizationEunIndicatorFormService, 'getOrganizationEunIndicator').mockReturnValue(organizationEunIndicator);
      jest.spyOn(organizationEunIndicatorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ organizationEunIndicator });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: organizationEunIndicator }));
      saveSubject.complete();

      // THEN
      expect(organizationEunIndicatorFormService.getOrganizationEunIndicator).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(organizationEunIndicatorService.update).toHaveBeenCalledWith(expect.objectContaining(organizationEunIndicator));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOrganizationEunIndicator>>();
      const organizationEunIndicator = { id: 123 };
      jest.spyOn(organizationEunIndicatorFormService, 'getOrganizationEunIndicator').mockReturnValue({ id: null });
      jest.spyOn(organizationEunIndicatorService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ organizationEunIndicator: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: organizationEunIndicator }));
      saveSubject.complete();

      // THEN
      expect(organizationEunIndicatorFormService.getOrganizationEunIndicator).toHaveBeenCalled();
      expect(organizationEunIndicatorService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOrganizationEunIndicator>>();
      const organizationEunIndicator = { id: 123 };
      jest.spyOn(organizationEunIndicatorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ organizationEunIndicator });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(organizationEunIndicatorService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});

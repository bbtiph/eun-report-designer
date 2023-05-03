import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { OrganizationInProjectFormService } from './organization-in-project-form.service';
import { OrganizationInProjectService } from '../service/organization-in-project.service';
import { IOrganizationInProject } from '../organization-in-project.model';

import { OrganizationInProjectUpdateComponent } from './organization-in-project-update.component';

describe('OrganizationInProject Management Update Component', () => {
  let comp: OrganizationInProjectUpdateComponent;
  let fixture: ComponentFixture<OrganizationInProjectUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let organizationInProjectFormService: OrganizationInProjectFormService;
  let organizationInProjectService: OrganizationInProjectService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [OrganizationInProjectUpdateComponent],
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
      .overrideTemplate(OrganizationInProjectUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(OrganizationInProjectUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    organizationInProjectFormService = TestBed.inject(OrganizationInProjectFormService);
    organizationInProjectService = TestBed.inject(OrganizationInProjectService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const organizationInProject: IOrganizationInProject = { id: 456 };

      activatedRoute.data = of({ organizationInProject });
      comp.ngOnInit();

      expect(comp.organizationInProject).toEqual(organizationInProject);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOrganizationInProject>>();
      const organizationInProject = { id: 123 };
      jest.spyOn(organizationInProjectFormService, 'getOrganizationInProject').mockReturnValue(organizationInProject);
      jest.spyOn(organizationInProjectService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ organizationInProject });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: organizationInProject }));
      saveSubject.complete();

      // THEN
      expect(organizationInProjectFormService.getOrganizationInProject).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(organizationInProjectService.update).toHaveBeenCalledWith(expect.objectContaining(organizationInProject));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOrganizationInProject>>();
      const organizationInProject = { id: 123 };
      jest.spyOn(organizationInProjectFormService, 'getOrganizationInProject').mockReturnValue({ id: null });
      jest.spyOn(organizationInProjectService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ organizationInProject: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: organizationInProject }));
      saveSubject.complete();

      // THEN
      expect(organizationInProjectFormService.getOrganizationInProject).toHaveBeenCalled();
      expect(organizationInProjectService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOrganizationInProject>>();
      const organizationInProject = { id: 123 };
      jest.spyOn(organizationInProjectService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ organizationInProject });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(organizationInProjectService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { RoleFormService } from './role-form.service';
import { RoleService } from '../service/role.service';
import { IRole } from '../role.model';
import { IPrivilege } from 'app/entities/privilege/privilege.model';
import { PrivilegeService } from 'app/entities/privilege/service/privilege.service';

import { RoleUpdateComponent } from './role-update.component';

describe('Role Management Update Component', () => {
  let comp: RoleUpdateComponent;
  let fixture: ComponentFixture<RoleUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let roleFormService: RoleFormService;
  let roleService: RoleService;
  let privilegeService: PrivilegeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [RoleUpdateComponent],
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
      .overrideTemplate(RoleUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RoleUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    roleFormService = TestBed.inject(RoleFormService);
    roleService = TestBed.inject(RoleService);
    privilegeService = TestBed.inject(PrivilegeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Privilege query and add missing value', () => {
      const role: IRole = { id: 456 };
      const privileges: IPrivilege[] = [{ id: 92586 }];
      role.privileges = privileges;

      const privilegeCollection: IPrivilege[] = [{ id: 12643 }];
      jest.spyOn(privilegeService, 'query').mockReturnValue(of(new HttpResponse({ body: privilegeCollection })));
      const additionalPrivileges = [...privileges];
      const expectedCollection: IPrivilege[] = [...additionalPrivileges, ...privilegeCollection];
      jest.spyOn(privilegeService, 'addPrivilegeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ role });
      comp.ngOnInit();

      expect(privilegeService.query).toHaveBeenCalled();
      expect(privilegeService.addPrivilegeToCollectionIfMissing).toHaveBeenCalledWith(
        privilegeCollection,
        ...additionalPrivileges.map(expect.objectContaining)
      );
      expect(comp.privilegesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const role: IRole = { id: 456 };
      const privilege: IPrivilege = { id: 75639 };
      role.privileges = [privilege];

      activatedRoute.data = of({ role });
      comp.ngOnInit();

      expect(comp.privilegesSharedCollection).toContain(privilege);
      expect(comp.role).toEqual(role);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRole>>();
      const role = { id: 123 };
      jest.spyOn(roleFormService, 'getRole').mockReturnValue(role);
      jest.spyOn(roleService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ role });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: role }));
      saveSubject.complete();

      // THEN
      expect(roleFormService.getRole).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(roleService.update).toHaveBeenCalledWith(expect.objectContaining(role));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRole>>();
      const role = { id: 123 };
      jest.spyOn(roleFormService, 'getRole').mockReturnValue({ id: null });
      jest.spyOn(roleService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ role: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: role }));
      saveSubject.complete();

      // THEN
      expect(roleFormService.getRole).toHaveBeenCalled();
      expect(roleService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRole>>();
      const role = { id: 123 };
      jest.spyOn(roleService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ role });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(roleService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('comparePrivilege', () => {
      it('Should forward to privilegeService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(privilegeService, 'comparePrivilege');
        comp.comparePrivilege(entity, entity2);
        expect(privilegeService.comparePrivilege).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});

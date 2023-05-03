import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { OperationalBodyMemberFormService } from './operational-body-member-form.service';
import { OperationalBodyMemberService } from '../service/operational-body-member.service';
import { IOperationalBodyMember } from '../operational-body-member.model';

import { OperationalBodyMemberUpdateComponent } from './operational-body-member-update.component';

describe('OperationalBodyMember Management Update Component', () => {
  let comp: OperationalBodyMemberUpdateComponent;
  let fixture: ComponentFixture<OperationalBodyMemberUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let operationalBodyMemberFormService: OperationalBodyMemberFormService;
  let operationalBodyMemberService: OperationalBodyMemberService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [OperationalBodyMemberUpdateComponent],
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
      .overrideTemplate(OperationalBodyMemberUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(OperationalBodyMemberUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    operationalBodyMemberFormService = TestBed.inject(OperationalBodyMemberFormService);
    operationalBodyMemberService = TestBed.inject(OperationalBodyMemberService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const operationalBodyMember: IOperationalBodyMember = { id: 456 };

      activatedRoute.data = of({ operationalBodyMember });
      comp.ngOnInit();

      expect(comp.operationalBodyMember).toEqual(operationalBodyMember);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOperationalBodyMember>>();
      const operationalBodyMember = { id: 123 };
      jest.spyOn(operationalBodyMemberFormService, 'getOperationalBodyMember').mockReturnValue(operationalBodyMember);
      jest.spyOn(operationalBodyMemberService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ operationalBodyMember });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: operationalBodyMember }));
      saveSubject.complete();

      // THEN
      expect(operationalBodyMemberFormService.getOperationalBodyMember).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(operationalBodyMemberService.update).toHaveBeenCalledWith(expect.objectContaining(operationalBodyMember));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOperationalBodyMember>>();
      const operationalBodyMember = { id: 123 };
      jest.spyOn(operationalBodyMemberFormService, 'getOperationalBodyMember').mockReturnValue({ id: null });
      jest.spyOn(operationalBodyMemberService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ operationalBodyMember: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: operationalBodyMember }));
      saveSubject.complete();

      // THEN
      expect(operationalBodyMemberFormService.getOperationalBodyMember).toHaveBeenCalled();
      expect(operationalBodyMemberService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOperationalBodyMember>>();
      const operationalBodyMember = { id: 123 };
      jest.spyOn(operationalBodyMemberService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ operationalBodyMember });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(operationalBodyMemberService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});

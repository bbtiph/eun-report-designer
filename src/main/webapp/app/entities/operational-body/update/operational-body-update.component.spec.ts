import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { OperationalBodyFormService } from './operational-body-form.service';
import { OperationalBodyService } from '../service/operational-body.service';
import { IOperationalBody } from '../operational-body.model';

import { OperationalBodyUpdateComponent } from './operational-body-update.component';

describe('OperationalBody Management Update Component', () => {
  let comp: OperationalBodyUpdateComponent;
  let fixture: ComponentFixture<OperationalBodyUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let operationalBodyFormService: OperationalBodyFormService;
  let operationalBodyService: OperationalBodyService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [OperationalBodyUpdateComponent],
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
      .overrideTemplate(OperationalBodyUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(OperationalBodyUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    operationalBodyFormService = TestBed.inject(OperationalBodyFormService);
    operationalBodyService = TestBed.inject(OperationalBodyService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const operationalBody: IOperationalBody = { id: 456 };

      activatedRoute.data = of({ operationalBody });
      comp.ngOnInit();

      expect(comp.operationalBody).toEqual(operationalBody);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOperationalBody>>();
      const operationalBody = { id: 123 };
      jest.spyOn(operationalBodyFormService, 'getOperationalBody').mockReturnValue(operationalBody);
      jest.spyOn(operationalBodyService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ operationalBody });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: operationalBody }));
      saveSubject.complete();

      // THEN
      expect(operationalBodyFormService.getOperationalBody).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(operationalBodyService.update).toHaveBeenCalledWith(expect.objectContaining(operationalBody));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOperationalBody>>();
      const operationalBody = { id: 123 };
      jest.spyOn(operationalBodyFormService, 'getOperationalBody').mockReturnValue({ id: null });
      jest.spyOn(operationalBodyService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ operationalBody: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: operationalBody }));
      saveSubject.complete();

      // THEN
      expect(operationalBodyFormService.getOperationalBody).toHaveBeenCalled();
      expect(operationalBodyService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOperationalBody>>();
      const operationalBody = { id: 123 };
      jest.spyOn(operationalBodyService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ operationalBody });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(operationalBodyService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { WorkingGroupReferencesFormService } from './working-group-references-form.service';
import { WorkingGroupReferencesService } from '../service/working-group-references.service';
import { IWorkingGroupReferences } from '../working-group-references.model';

import { WorkingGroupReferencesUpdateComponent } from './working-group-references-update.component';

describe('WorkingGroupReferences Management Update Component', () => {
  let comp: WorkingGroupReferencesUpdateComponent;
  let fixture: ComponentFixture<WorkingGroupReferencesUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let workingGroupReferencesFormService: WorkingGroupReferencesFormService;
  let workingGroupReferencesService: WorkingGroupReferencesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [WorkingGroupReferencesUpdateComponent],
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
      .overrideTemplate(WorkingGroupReferencesUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(WorkingGroupReferencesUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    workingGroupReferencesFormService = TestBed.inject(WorkingGroupReferencesFormService);
    workingGroupReferencesService = TestBed.inject(WorkingGroupReferencesService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const workingGroupReferences: IWorkingGroupReferences = { id: 456 };

      activatedRoute.data = of({ workingGroupReferences });
      comp.ngOnInit();

      expect(comp.workingGroupReferences).toEqual(workingGroupReferences);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IWorkingGroupReferences>>();
      const workingGroupReferences = { id: 123 };
      jest.spyOn(workingGroupReferencesFormService, 'getWorkingGroupReferences').mockReturnValue(workingGroupReferences);
      jest.spyOn(workingGroupReferencesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ workingGroupReferences });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: workingGroupReferences }));
      saveSubject.complete();

      // THEN
      expect(workingGroupReferencesFormService.getWorkingGroupReferences).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(workingGroupReferencesService.update).toHaveBeenCalledWith(expect.objectContaining(workingGroupReferences));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IWorkingGroupReferences>>();
      const workingGroupReferences = { id: 123 };
      jest.spyOn(workingGroupReferencesFormService, 'getWorkingGroupReferences').mockReturnValue({ id: null });
      jest.spyOn(workingGroupReferencesService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ workingGroupReferences: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: workingGroupReferences }));
      saveSubject.complete();

      // THEN
      expect(workingGroupReferencesFormService.getWorkingGroupReferences).toHaveBeenCalled();
      expect(workingGroupReferencesService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IWorkingGroupReferences>>();
      const workingGroupReferences = { id: 123 };
      jest.spyOn(workingGroupReferencesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ workingGroupReferences });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(workingGroupReferencesService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});

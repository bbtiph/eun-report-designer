import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { MoeContactsFormService } from './moe-contacts-form.service';
import { MoeContactsService } from '../service/moe-contacts.service';
import { IMoeContacts } from '../moe-contacts.model';

import { MoeContactsUpdateComponent } from './moe-contacts-update.component';

describe('MoeContacts Management Update Component', () => {
  let comp: MoeContactsUpdateComponent;
  let fixture: ComponentFixture<MoeContactsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let moeContactsFormService: MoeContactsFormService;
  let moeContactsService: MoeContactsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [MoeContactsUpdateComponent],
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
      .overrideTemplate(MoeContactsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MoeContactsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    moeContactsFormService = TestBed.inject(MoeContactsFormService);
    moeContactsService = TestBed.inject(MoeContactsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const moeContacts: IMoeContacts = { id: 456 };

      activatedRoute.data = of({ moeContacts });
      comp.ngOnInit();

      expect(comp.moeContacts).toEqual(moeContacts);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMoeContacts>>();
      const moeContacts = { id: 123 };
      jest.spyOn(moeContactsFormService, 'getMoeContacts').mockReturnValue(moeContacts);
      jest.spyOn(moeContactsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ moeContacts });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: moeContacts }));
      saveSubject.complete();

      // THEN
      expect(moeContactsFormService.getMoeContacts).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(moeContactsService.update).toHaveBeenCalledWith(expect.objectContaining(moeContacts));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMoeContacts>>();
      const moeContacts = { id: 123 };
      jest.spyOn(moeContactsFormService, 'getMoeContacts').mockReturnValue({ id: null });
      jest.spyOn(moeContactsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ moeContacts: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: moeContacts }));
      saveSubject.complete();

      // THEN
      expect(moeContactsFormService.getMoeContacts).toHaveBeenCalled();
      expect(moeContactsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMoeContacts>>();
      const moeContacts = { id: 123 };
      jest.spyOn(moeContactsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ moeContacts });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(moeContactsService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});

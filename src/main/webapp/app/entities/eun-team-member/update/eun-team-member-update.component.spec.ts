import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { EunTeamMemberFormService } from './eun-team-member-form.service';
import { EunTeamMemberService } from '../service/eun-team-member.service';
import { IEunTeamMember } from '../eun-team-member.model';

import { EunTeamMemberUpdateComponent } from './eun-team-member-update.component';

describe('EunTeamMember Management Update Component', () => {
  let comp: EunTeamMemberUpdateComponent;
  let fixture: ComponentFixture<EunTeamMemberUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let eunTeamMemberFormService: EunTeamMemberFormService;
  let eunTeamMemberService: EunTeamMemberService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [EunTeamMemberUpdateComponent],
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
      .overrideTemplate(EunTeamMemberUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EunTeamMemberUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    eunTeamMemberFormService = TestBed.inject(EunTeamMemberFormService);
    eunTeamMemberService = TestBed.inject(EunTeamMemberService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const eunTeamMember: IEunTeamMember = { id: 456 };

      activatedRoute.data = of({ eunTeamMember });
      comp.ngOnInit();

      expect(comp.eunTeamMember).toEqual(eunTeamMember);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEunTeamMember>>();
      const eunTeamMember = { id: 123 };
      jest.spyOn(eunTeamMemberFormService, 'getEunTeamMember').mockReturnValue(eunTeamMember);
      jest.spyOn(eunTeamMemberService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ eunTeamMember });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: eunTeamMember }));
      saveSubject.complete();

      // THEN
      expect(eunTeamMemberFormService.getEunTeamMember).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(eunTeamMemberService.update).toHaveBeenCalledWith(expect.objectContaining(eunTeamMember));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEunTeamMember>>();
      const eunTeamMember = { id: 123 };
      jest.spyOn(eunTeamMemberFormService, 'getEunTeamMember').mockReturnValue({ id: null });
      jest.spyOn(eunTeamMemberService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ eunTeamMember: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: eunTeamMember }));
      saveSubject.complete();

      // THEN
      expect(eunTeamMemberFormService.getEunTeamMember).toHaveBeenCalled();
      expect(eunTeamMemberService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEunTeamMember>>();
      const eunTeamMember = { id: 123 };
      jest.spyOn(eunTeamMemberService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ eunTeamMember });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(eunTeamMemberService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});

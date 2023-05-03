import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { EunTeamFormService } from './eun-team-form.service';
import { EunTeamService } from '../service/eun-team.service';
import { IEunTeam } from '../eun-team.model';
import { IEunTeamMember } from 'app/entities/eun-team-member/eun-team-member.model';
import { EunTeamMemberService } from 'app/entities/eun-team-member/service/eun-team-member.service';

import { EunTeamUpdateComponent } from './eun-team-update.component';

describe('EunTeam Management Update Component', () => {
  let comp: EunTeamUpdateComponent;
  let fixture: ComponentFixture<EunTeamUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let eunTeamFormService: EunTeamFormService;
  let eunTeamService: EunTeamService;
  let eunTeamMemberService: EunTeamMemberService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [EunTeamUpdateComponent],
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
      .overrideTemplate(EunTeamUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EunTeamUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    eunTeamFormService = TestBed.inject(EunTeamFormService);
    eunTeamService = TestBed.inject(EunTeamService);
    eunTeamMemberService = TestBed.inject(EunTeamMemberService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call EunTeamMember query and add missing value', () => {
      const eunTeam: IEunTeam = { id: 456 };
      const eunTeamMember: IEunTeamMember = { id: 86747 };
      eunTeam.eunTeamMember = eunTeamMember;

      const eunTeamMemberCollection: IEunTeamMember[] = [{ id: 49171 }];
      jest.spyOn(eunTeamMemberService, 'query').mockReturnValue(of(new HttpResponse({ body: eunTeamMemberCollection })));
      const additionalEunTeamMembers = [eunTeamMember];
      const expectedCollection: IEunTeamMember[] = [...additionalEunTeamMembers, ...eunTeamMemberCollection];
      jest.spyOn(eunTeamMemberService, 'addEunTeamMemberToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ eunTeam });
      comp.ngOnInit();

      expect(eunTeamMemberService.query).toHaveBeenCalled();
      expect(eunTeamMemberService.addEunTeamMemberToCollectionIfMissing).toHaveBeenCalledWith(
        eunTeamMemberCollection,
        ...additionalEunTeamMembers.map(expect.objectContaining)
      );
      expect(comp.eunTeamMembersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const eunTeam: IEunTeam = { id: 456 };
      const eunTeamMember: IEunTeamMember = { id: 15160 };
      eunTeam.eunTeamMember = eunTeamMember;

      activatedRoute.data = of({ eunTeam });
      comp.ngOnInit();

      expect(comp.eunTeamMembersSharedCollection).toContain(eunTeamMember);
      expect(comp.eunTeam).toEqual(eunTeam);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEunTeam>>();
      const eunTeam = { id: 123 };
      jest.spyOn(eunTeamFormService, 'getEunTeam').mockReturnValue(eunTeam);
      jest.spyOn(eunTeamService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ eunTeam });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: eunTeam }));
      saveSubject.complete();

      // THEN
      expect(eunTeamFormService.getEunTeam).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(eunTeamService.update).toHaveBeenCalledWith(expect.objectContaining(eunTeam));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEunTeam>>();
      const eunTeam = { id: 123 };
      jest.spyOn(eunTeamFormService, 'getEunTeam').mockReturnValue({ id: null });
      jest.spyOn(eunTeamService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ eunTeam: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: eunTeam }));
      saveSubject.complete();

      // THEN
      expect(eunTeamFormService.getEunTeam).toHaveBeenCalled();
      expect(eunTeamService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEunTeam>>();
      const eunTeam = { id: 123 };
      jest.spyOn(eunTeamService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ eunTeam });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(eunTeamService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareEunTeamMember', () => {
      it('Should forward to eunTeamMemberService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(eunTeamMemberService, 'compareEunTeamMember');
        comp.compareEunTeamMember(entity, entity2);
        expect(eunTeamMemberService.compareEunTeamMember).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});

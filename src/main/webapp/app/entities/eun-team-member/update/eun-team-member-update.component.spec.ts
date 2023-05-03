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
import { IEunTeam } from 'app/entities/eun-team/eun-team.model';
import { EunTeamService } from 'app/entities/eun-team/service/eun-team.service';
import { IPerson } from 'app/entities/person/person.model';
import { PersonService } from 'app/entities/person/service/person.service';

import { EunTeamMemberUpdateComponent } from './eun-team-member-update.component';

describe('EunTeamMember Management Update Component', () => {
  let comp: EunTeamMemberUpdateComponent;
  let fixture: ComponentFixture<EunTeamMemberUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let eunTeamMemberFormService: EunTeamMemberFormService;
  let eunTeamMemberService: EunTeamMemberService;
  let eunTeamService: EunTeamService;
  let personService: PersonService;

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
    eunTeamService = TestBed.inject(EunTeamService);
    personService = TestBed.inject(PersonService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call EunTeam query and add missing value', () => {
      const eunTeamMember: IEunTeamMember = { id: 456 };
      const team: IEunTeam = { id: 73326 };
      eunTeamMember.team = team;

      const eunTeamCollection: IEunTeam[] = [{ id: 26904 }];
      jest.spyOn(eunTeamService, 'query').mockReturnValue(of(new HttpResponse({ body: eunTeamCollection })));
      const additionalEunTeams = [team];
      const expectedCollection: IEunTeam[] = [...additionalEunTeams, ...eunTeamCollection];
      jest.spyOn(eunTeamService, 'addEunTeamToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ eunTeamMember });
      comp.ngOnInit();

      expect(eunTeamService.query).toHaveBeenCalled();
      expect(eunTeamService.addEunTeamToCollectionIfMissing).toHaveBeenCalledWith(
        eunTeamCollection,
        ...additionalEunTeams.map(expect.objectContaining)
      );
      expect(comp.eunTeamsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Person query and add missing value', () => {
      const eunTeamMember: IEunTeamMember = { id: 456 };
      const person: IPerson = { id: 94703 };
      eunTeamMember.person = person;

      const personCollection: IPerson[] = [{ id: 96079 }];
      jest.spyOn(personService, 'query').mockReturnValue(of(new HttpResponse({ body: personCollection })));
      const additionalPeople = [person];
      const expectedCollection: IPerson[] = [...additionalPeople, ...personCollection];
      jest.spyOn(personService, 'addPersonToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ eunTeamMember });
      comp.ngOnInit();

      expect(personService.query).toHaveBeenCalled();
      expect(personService.addPersonToCollectionIfMissing).toHaveBeenCalledWith(
        personCollection,
        ...additionalPeople.map(expect.objectContaining)
      );
      expect(comp.peopleSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const eunTeamMember: IEunTeamMember = { id: 456 };
      const team: IEunTeam = { id: 38142 };
      eunTeamMember.team = team;
      const person: IPerson = { id: 60404 };
      eunTeamMember.person = person;

      activatedRoute.data = of({ eunTeamMember });
      comp.ngOnInit();

      expect(comp.eunTeamsSharedCollection).toContain(team);
      expect(comp.peopleSharedCollection).toContain(person);
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

  describe('Compare relationships', () => {
    describe('compareEunTeam', () => {
      it('Should forward to eunTeamService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(eunTeamService, 'compareEunTeam');
        comp.compareEunTeam(entity, entity2);
        expect(eunTeamService.compareEunTeam).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('comparePerson', () => {
      it('Should forward to personService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(personService, 'comparePerson');
        comp.comparePerson(entity, entity2);
        expect(personService.comparePerson).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});

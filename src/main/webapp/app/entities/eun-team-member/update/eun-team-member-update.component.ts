import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { EunTeamMemberFormService, EunTeamMemberFormGroup } from './eun-team-member-form.service';
import { IEunTeamMember } from '../eun-team-member.model';
import { EunTeamMemberService } from '../service/eun-team-member.service';
import { IEunTeam } from 'app/entities/eun-team/eun-team.model';
import { EunTeamService } from 'app/entities/eun-team/service/eun-team.service';
import { IPerson } from 'app/entities/person/person.model';
import { PersonService } from 'app/entities/person/service/person.service';

@Component({
  selector: 'jhi-eun-team-member-update',
  templateUrl: './eun-team-member-update.component.html',
})
export class EunTeamMemberUpdateComponent implements OnInit {
  isSaving = false;
  eunTeamMember: IEunTeamMember | null = null;

  eunTeamsSharedCollection: IEunTeam[] = [];
  peopleSharedCollection: IPerson[] = [];

  editForm: EunTeamMemberFormGroup = this.eunTeamMemberFormService.createEunTeamMemberFormGroup();

  constructor(
    protected eunTeamMemberService: EunTeamMemberService,
    protected eunTeamMemberFormService: EunTeamMemberFormService,
    protected eunTeamService: EunTeamService,
    protected personService: PersonService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareEunTeam = (o1: IEunTeam | null, o2: IEunTeam | null): boolean => this.eunTeamService.compareEunTeam(o1, o2);

  comparePerson = (o1: IPerson | null, o2: IPerson | null): boolean => this.personService.comparePerson(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ eunTeamMember }) => {
      this.eunTeamMember = eunTeamMember;
      if (eunTeamMember) {
        this.updateForm(eunTeamMember);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const eunTeamMember = this.eunTeamMemberFormService.getEunTeamMember(this.editForm);
    if (eunTeamMember.id !== null) {
      this.subscribeToSaveResponse(this.eunTeamMemberService.update(eunTeamMember));
    } else {
      this.subscribeToSaveResponse(this.eunTeamMemberService.create(eunTeamMember));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEunTeamMember>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(eunTeamMember: IEunTeamMember): void {
    this.eunTeamMember = eunTeamMember;
    this.eunTeamMemberFormService.resetForm(this.editForm, eunTeamMember);

    this.eunTeamsSharedCollection = this.eunTeamService.addEunTeamToCollectionIfMissing<IEunTeam>(
      this.eunTeamsSharedCollection,
      eunTeamMember.team
    );
    this.peopleSharedCollection = this.personService.addPersonToCollectionIfMissing<IPerson>(
      this.peopleSharedCollection,
      eunTeamMember.person
    );
  }

  protected loadRelationshipsOptions(): void {
    this.eunTeamService
      .query()
      .pipe(map((res: HttpResponse<IEunTeam[]>) => res.body ?? []))
      .pipe(
        map((eunTeams: IEunTeam[]) => this.eunTeamService.addEunTeamToCollectionIfMissing<IEunTeam>(eunTeams, this.eunTeamMember?.team))
      )
      .subscribe((eunTeams: IEunTeam[]) => (this.eunTeamsSharedCollection = eunTeams));

    this.personService
      .query()
      .pipe(map((res: HttpResponse<IPerson[]>) => res.body ?? []))
      .pipe(map((people: IPerson[]) => this.personService.addPersonToCollectionIfMissing<IPerson>(people, this.eunTeamMember?.person)))
      .subscribe((people: IPerson[]) => (this.peopleSharedCollection = people));
  }
}

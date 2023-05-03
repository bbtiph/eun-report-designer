import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { PersonFormService, PersonFormGroup } from './person-form.service';
import { IPerson } from '../person.model';
import { PersonService } from '../service/person.service';
import { IEunTeamMember } from 'app/entities/eun-team-member/eun-team-member.model';
import { EunTeamMemberService } from 'app/entities/eun-team-member/service/eun-team-member.service';
import { IEventParticipant } from 'app/entities/event-participant/event-participant.model';
import { EventParticipantService } from 'app/entities/event-participant/service/event-participant.service';
import { IPersonInOrganization } from 'app/entities/person-in-organization/person-in-organization.model';
import { PersonInOrganizationService } from 'app/entities/person-in-organization/service/person-in-organization.service';
import { IPersonInProject } from 'app/entities/person-in-project/person-in-project.model';
import { PersonInProjectService } from 'app/entities/person-in-project/service/person-in-project.service';
import { GdprStatus } from 'app/entities/enumerations/gdpr-status.model';

@Component({
  selector: 'jhi-person-update',
  templateUrl: './person-update.component.html',
})
export class PersonUpdateComponent implements OnInit {
  isSaving = false;
  person: IPerson | null = null;
  gdprStatusValues = Object.keys(GdprStatus);

  eunTeamMembersSharedCollection: IEunTeamMember[] = [];
  eventParticipantsSharedCollection: IEventParticipant[] = [];
  personInOrganizationsSharedCollection: IPersonInOrganization[] = [];
  personInProjectsSharedCollection: IPersonInProject[] = [];

  editForm: PersonFormGroup = this.personFormService.createPersonFormGroup();

  constructor(
    protected personService: PersonService,
    protected personFormService: PersonFormService,
    protected eunTeamMemberService: EunTeamMemberService,
    protected eventParticipantService: EventParticipantService,
    protected personInOrganizationService: PersonInOrganizationService,
    protected personInProjectService: PersonInProjectService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareEunTeamMember = (o1: IEunTeamMember | null, o2: IEunTeamMember | null): boolean =>
    this.eunTeamMemberService.compareEunTeamMember(o1, o2);

  compareEventParticipant = (o1: IEventParticipant | null, o2: IEventParticipant | null): boolean =>
    this.eventParticipantService.compareEventParticipant(o1, o2);

  comparePersonInOrganization = (o1: IPersonInOrganization | null, o2: IPersonInOrganization | null): boolean =>
    this.personInOrganizationService.comparePersonInOrganization(o1, o2);

  comparePersonInProject = (o1: IPersonInProject | null, o2: IPersonInProject | null): boolean =>
    this.personInProjectService.comparePersonInProject(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ person }) => {
      this.person = person;
      if (person) {
        this.updateForm(person);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const person = this.personFormService.getPerson(this.editForm);
    if (person.id !== null) {
      this.subscribeToSaveResponse(this.personService.update(person));
    } else {
      this.subscribeToSaveResponse(this.personService.create(person));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPerson>>): void {
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

  protected updateForm(person: IPerson): void {
    this.person = person;
    this.personFormService.resetForm(this.editForm, person);

    this.eunTeamMembersSharedCollection = this.eunTeamMemberService.addEunTeamMemberToCollectionIfMissing<IEunTeamMember>(
      this.eunTeamMembersSharedCollection,
      person.eunTeamMember
    );
    this.eventParticipantsSharedCollection = this.eventParticipantService.addEventParticipantToCollectionIfMissing<IEventParticipant>(
      this.eventParticipantsSharedCollection,
      person.eventParticipant
    );
    this.personInOrganizationsSharedCollection =
      this.personInOrganizationService.addPersonInOrganizationToCollectionIfMissing<IPersonInOrganization>(
        this.personInOrganizationsSharedCollection,
        person.personInOrganization
      );
    this.personInProjectsSharedCollection = this.personInProjectService.addPersonInProjectToCollectionIfMissing<IPersonInProject>(
      this.personInProjectsSharedCollection,
      person.personInProject
    );
  }

  protected loadRelationshipsOptions(): void {
    this.eunTeamMemberService
      .query()
      .pipe(map((res: HttpResponse<IEunTeamMember[]>) => res.body ?? []))
      .pipe(
        map((eunTeamMembers: IEunTeamMember[]) =>
          this.eunTeamMemberService.addEunTeamMemberToCollectionIfMissing<IEunTeamMember>(eunTeamMembers, this.person?.eunTeamMember)
        )
      )
      .subscribe((eunTeamMembers: IEunTeamMember[]) => (this.eunTeamMembersSharedCollection = eunTeamMembers));

    this.eventParticipantService
      .query()
      .pipe(map((res: HttpResponse<IEventParticipant[]>) => res.body ?? []))
      .pipe(
        map((eventParticipants: IEventParticipant[]) =>
          this.eventParticipantService.addEventParticipantToCollectionIfMissing<IEventParticipant>(
            eventParticipants,
            this.person?.eventParticipant
          )
        )
      )
      .subscribe((eventParticipants: IEventParticipant[]) => (this.eventParticipantsSharedCollection = eventParticipants));

    this.personInOrganizationService
      .query()
      .pipe(map((res: HttpResponse<IPersonInOrganization[]>) => res.body ?? []))
      .pipe(
        map((personInOrganizations: IPersonInOrganization[]) =>
          this.personInOrganizationService.addPersonInOrganizationToCollectionIfMissing<IPersonInOrganization>(
            personInOrganizations,
            this.person?.personInOrganization
          )
        )
      )
      .subscribe((personInOrganizations: IPersonInOrganization[]) => (this.personInOrganizationsSharedCollection = personInOrganizations));

    this.personInProjectService
      .query()
      .pipe(map((res: HttpResponse<IPersonInProject[]>) => res.body ?? []))
      .pipe(
        map((personInProjects: IPersonInProject[]) =>
          this.personInProjectService.addPersonInProjectToCollectionIfMissing<IPersonInProject>(
            personInProjects,
            this.person?.personInProject
          )
        )
      )
      .subscribe((personInProjects: IPersonInProject[]) => (this.personInProjectsSharedCollection = personInProjects));
  }
}

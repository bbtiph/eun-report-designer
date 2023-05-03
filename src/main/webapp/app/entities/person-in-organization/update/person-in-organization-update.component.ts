import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { PersonInOrganizationFormService, PersonInOrganizationFormGroup } from './person-in-organization-form.service';
import { IPersonInOrganization } from '../person-in-organization.model';
import { PersonInOrganizationService } from '../service/person-in-organization.service';
import { IPerson } from 'app/entities/person/person.model';
import { PersonService } from 'app/entities/person/service/person.service';
import { IOrganization } from 'app/entities/organization/organization.model';
import { OrganizationService } from 'app/entities/organization/service/organization.service';

@Component({
  selector: 'jhi-person-in-organization-update',
  templateUrl: './person-in-organization-update.component.html',
})
export class PersonInOrganizationUpdateComponent implements OnInit {
  isSaving = false;
  personInOrganization: IPersonInOrganization | null = null;

  peopleSharedCollection: IPerson[] = [];
  organizationsSharedCollection: IOrganization[] = [];

  editForm: PersonInOrganizationFormGroup = this.personInOrganizationFormService.createPersonInOrganizationFormGroup();

  constructor(
    protected personInOrganizationService: PersonInOrganizationService,
    protected personInOrganizationFormService: PersonInOrganizationFormService,
    protected personService: PersonService,
    protected organizationService: OrganizationService,
    protected activatedRoute: ActivatedRoute
  ) {}

  comparePerson = (o1: IPerson | null, o2: IPerson | null): boolean => this.personService.comparePerson(o1, o2);

  compareOrganization = (o1: IOrganization | null, o2: IOrganization | null): boolean =>
    this.organizationService.compareOrganization(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ personInOrganization }) => {
      this.personInOrganization = personInOrganization;
      if (personInOrganization) {
        this.updateForm(personInOrganization);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const personInOrganization = this.personInOrganizationFormService.getPersonInOrganization(this.editForm);
    if (personInOrganization.id !== null) {
      this.subscribeToSaveResponse(this.personInOrganizationService.update(personInOrganization));
    } else {
      this.subscribeToSaveResponse(this.personInOrganizationService.create(personInOrganization));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPersonInOrganization>>): void {
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

  protected updateForm(personInOrganization: IPersonInOrganization): void {
    this.personInOrganization = personInOrganization;
    this.personInOrganizationFormService.resetForm(this.editForm, personInOrganization);

    this.peopleSharedCollection = this.personService.addPersonToCollectionIfMissing<IPerson>(
      this.peopleSharedCollection,
      personInOrganization.person
    );
    this.organizationsSharedCollection = this.organizationService.addOrganizationToCollectionIfMissing<IOrganization>(
      this.organizationsSharedCollection,
      personInOrganization.organization
    );
  }

  protected loadRelationshipsOptions(): void {
    this.personService
      .query()
      .pipe(map((res: HttpResponse<IPerson[]>) => res.body ?? []))
      .pipe(
        map((people: IPerson[]) => this.personService.addPersonToCollectionIfMissing<IPerson>(people, this.personInOrganization?.person))
      )
      .subscribe((people: IPerson[]) => (this.peopleSharedCollection = people));

    this.organizationService
      .query()
      .pipe(map((res: HttpResponse<IOrganization[]>) => res.body ?? []))
      .pipe(
        map((organizations: IOrganization[]) =>
          this.organizationService.addOrganizationToCollectionIfMissing<IOrganization>(
            organizations,
            this.personInOrganization?.organization
          )
        )
      )
      .subscribe((organizations: IOrganization[]) => (this.organizationsSharedCollection = organizations));
  }
}

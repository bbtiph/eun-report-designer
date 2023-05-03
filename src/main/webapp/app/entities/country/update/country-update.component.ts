import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { CountryFormService, CountryFormGroup } from './country-form.service';
import { ICountry } from '../country.model';
import { CountryService } from '../service/country.service';
import { IMinistry } from 'app/entities/ministry/ministry.model';
import { MinistryService } from 'app/entities/ministry/service/ministry.service';
import { IOperationalBodyMember } from 'app/entities/operational-body-member/operational-body-member.model';
import { OperationalBodyMemberService } from 'app/entities/operational-body-member/service/operational-body-member.service';
import { IOrganization } from 'app/entities/organization/organization.model';
import { OrganizationService } from 'app/entities/organization/service/organization.service';
import { IPerson } from 'app/entities/person/person.model';
import { PersonService } from 'app/entities/person/service/person.service';

@Component({
  selector: 'jhi-country-update',
  templateUrl: './country-update.component.html',
})
export class CountryUpdateComponent implements OnInit {
  isSaving = false;
  country: ICountry | null = null;

  ministriesSharedCollection: IMinistry[] = [];
  operationalBodyMembersSharedCollection: IOperationalBodyMember[] = [];
  organizationsSharedCollection: IOrganization[] = [];
  peopleSharedCollection: IPerson[] = [];

  editForm: CountryFormGroup = this.countryFormService.createCountryFormGroup();

  constructor(
    protected countryService: CountryService,
    protected countryFormService: CountryFormService,
    protected ministryService: MinistryService,
    protected operationalBodyMemberService: OperationalBodyMemberService,
    protected organizationService: OrganizationService,
    protected personService: PersonService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareMinistry = (o1: IMinistry | null, o2: IMinistry | null): boolean => this.ministryService.compareMinistry(o1, o2);

  compareOperationalBodyMember = (o1: IOperationalBodyMember | null, o2: IOperationalBodyMember | null): boolean =>
    this.operationalBodyMemberService.compareOperationalBodyMember(o1, o2);

  compareOrganization = (o1: IOrganization | null, o2: IOrganization | null): boolean =>
    this.organizationService.compareOrganization(o1, o2);

  comparePerson = (o1: IPerson | null, o2: IPerson | null): boolean => this.personService.comparePerson(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ country }) => {
      this.country = country;
      if (country) {
        this.updateForm(country);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const country = this.countryFormService.getCountry(this.editForm);
    if (country.id !== null) {
      this.subscribeToSaveResponse(this.countryService.update(country));
    } else {
      this.subscribeToSaveResponse(this.countryService.create(country));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICountry>>): void {
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

  protected updateForm(country: ICountry): void {
    this.country = country;
    this.countryFormService.resetForm(this.editForm, country);

    this.ministriesSharedCollection = this.ministryService.addMinistryToCollectionIfMissing<IMinistry>(
      this.ministriesSharedCollection,
      country.ministry
    );
    this.operationalBodyMembersSharedCollection =
      this.operationalBodyMemberService.addOperationalBodyMemberToCollectionIfMissing<IOperationalBodyMember>(
        this.operationalBodyMembersSharedCollection,
        country.operationalBodyMember
      );
    this.organizationsSharedCollection = this.organizationService.addOrganizationToCollectionIfMissing<IOrganization>(
      this.organizationsSharedCollection,
      country.organization
    );
    this.peopleSharedCollection = this.personService.addPersonToCollectionIfMissing<IPerson>(this.peopleSharedCollection, country.person);
  }

  protected loadRelationshipsOptions(): void {
    this.ministryService
      .query()
      .pipe(map((res: HttpResponse<IMinistry[]>) => res.body ?? []))
      .pipe(
        map((ministries: IMinistry[]) =>
          this.ministryService.addMinistryToCollectionIfMissing<IMinistry>(ministries, this.country?.ministry)
        )
      )
      .subscribe((ministries: IMinistry[]) => (this.ministriesSharedCollection = ministries));

    this.operationalBodyMemberService
      .query()
      .pipe(map((res: HttpResponse<IOperationalBodyMember[]>) => res.body ?? []))
      .pipe(
        map((operationalBodyMembers: IOperationalBodyMember[]) =>
          this.operationalBodyMemberService.addOperationalBodyMemberToCollectionIfMissing<IOperationalBodyMember>(
            operationalBodyMembers,
            this.country?.operationalBodyMember
          )
        )
      )
      .subscribe(
        (operationalBodyMembers: IOperationalBodyMember[]) => (this.operationalBodyMembersSharedCollection = operationalBodyMembers)
      );

    this.organizationService
      .query()
      .pipe(map((res: HttpResponse<IOrganization[]>) => res.body ?? []))
      .pipe(
        map((organizations: IOrganization[]) =>
          this.organizationService.addOrganizationToCollectionIfMissing<IOrganization>(organizations, this.country?.organization)
        )
      )
      .subscribe((organizations: IOrganization[]) => (this.organizationsSharedCollection = organizations));

    this.personService
      .query()
      .pipe(map((res: HttpResponse<IPerson[]>) => res.body ?? []))
      .pipe(map((people: IPerson[]) => this.personService.addPersonToCollectionIfMissing<IPerson>(people, this.country?.person)))
      .subscribe((people: IPerson[]) => (this.peopleSharedCollection = people));
  }
}

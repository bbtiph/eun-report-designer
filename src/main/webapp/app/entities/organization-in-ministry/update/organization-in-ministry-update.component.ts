import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { OrganizationInMinistryFormService, OrganizationInMinistryFormGroup } from './organization-in-ministry-form.service';
import { IOrganizationInMinistry } from '../organization-in-ministry.model';
import { OrganizationInMinistryService } from '../service/organization-in-ministry.service';
import { IMinistry } from 'app/entities/ministry/ministry.model';
import { MinistryService } from 'app/entities/ministry/service/ministry.service';
import { IOrganization } from 'app/entities/organization/organization.model';
import { OrganizationService } from 'app/entities/organization/service/organization.service';

@Component({
  selector: 'jhi-organization-in-ministry-update',
  templateUrl: './organization-in-ministry-update.component.html',
})
export class OrganizationInMinistryUpdateComponent implements OnInit {
  isSaving = false;
  organizationInMinistry: IOrganizationInMinistry | null = null;

  ministriesSharedCollection: IMinistry[] = [];
  organizationsSharedCollection: IOrganization[] = [];

  editForm: OrganizationInMinistryFormGroup = this.organizationInMinistryFormService.createOrganizationInMinistryFormGroup();

  constructor(
    protected organizationInMinistryService: OrganizationInMinistryService,
    protected organizationInMinistryFormService: OrganizationInMinistryFormService,
    protected ministryService: MinistryService,
    protected organizationService: OrganizationService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareMinistry = (o1: IMinistry | null, o2: IMinistry | null): boolean => this.ministryService.compareMinistry(o1, o2);

  compareOrganization = (o1: IOrganization | null, o2: IOrganization | null): boolean =>
    this.organizationService.compareOrganization(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ organizationInMinistry }) => {
      this.organizationInMinistry = organizationInMinistry;
      if (organizationInMinistry) {
        this.updateForm(organizationInMinistry);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const organizationInMinistry = this.organizationInMinistryFormService.getOrganizationInMinistry(this.editForm);
    if (organizationInMinistry.id !== null) {
      this.subscribeToSaveResponse(this.organizationInMinistryService.update(organizationInMinistry));
    } else {
      this.subscribeToSaveResponse(this.organizationInMinistryService.create(organizationInMinistry));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrganizationInMinistry>>): void {
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

  protected updateForm(organizationInMinistry: IOrganizationInMinistry): void {
    this.organizationInMinistry = organizationInMinistry;
    this.organizationInMinistryFormService.resetForm(this.editForm, organizationInMinistry);

    this.ministriesSharedCollection = this.ministryService.addMinistryToCollectionIfMissing<IMinistry>(
      this.ministriesSharedCollection,
      organizationInMinistry.ministry
    );
    this.organizationsSharedCollection = this.organizationService.addOrganizationToCollectionIfMissing<IOrganization>(
      this.organizationsSharedCollection,
      organizationInMinistry.organization
    );
  }

  protected loadRelationshipsOptions(): void {
    this.ministryService
      .query()
      .pipe(map((res: HttpResponse<IMinistry[]>) => res.body ?? []))
      .pipe(
        map((ministries: IMinistry[]) =>
          this.ministryService.addMinistryToCollectionIfMissing<IMinistry>(ministries, this.organizationInMinistry?.ministry)
        )
      )
      .subscribe((ministries: IMinistry[]) => (this.ministriesSharedCollection = ministries));

    this.organizationService
      .query()
      .pipe(map((res: HttpResponse<IOrganization[]>) => res.body ?? []))
      .pipe(
        map((organizations: IOrganization[]) =>
          this.organizationService.addOrganizationToCollectionIfMissing<IOrganization>(
            organizations,
            this.organizationInMinistry?.organization
          )
        )
      )
      .subscribe((organizations: IOrganization[]) => (this.organizationsSharedCollection = organizations));
  }
}

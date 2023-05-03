import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { OrganizationFormService, OrganizationFormGroup } from './organization-form.service';
import { IOrganization } from '../organization.model';
import { OrganizationService } from '../service/organization.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { ICountries } from 'app/entities/countries/countries.model';
import { CountriesService } from 'app/entities/countries/service/countries.service';
import { OrgStatus } from 'app/entities/enumerations/org-status.model';

@Component({
  selector: 'jhi-organization-update',
  templateUrl: './organization-update.component.html',
})
export class OrganizationUpdateComponent implements OnInit {
  isSaving = false;
  organization: IOrganization | null = null;
  orgStatusValues = Object.keys(OrgStatus);

  countriesSharedCollection: ICountries[] = [];

  editForm: OrganizationFormGroup = this.organizationFormService.createOrganizationFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected organizationService: OrganizationService,
    protected organizationFormService: OrganizationFormService,
    protected countriesService: CountriesService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareCountries = (o1: ICountries | null, o2: ICountries | null): boolean => this.countriesService.compareCountries(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ organization }) => {
      this.organization = organization;
      if (organization) {
        this.updateForm(organization);
      }

      this.loadRelationshipsOptions();
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('eunReportDesignerApp.error', { message: err.message })),
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const organization = this.organizationFormService.getOrganization(this.editForm);
    if (organization.id !== null) {
      this.subscribeToSaveResponse(this.organizationService.update(organization));
    } else {
      this.subscribeToSaveResponse(this.organizationService.create(organization));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrganization>>): void {
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

  protected updateForm(organization: IOrganization): void {
    this.organization = organization;
    this.organizationFormService.resetForm(this.editForm, organization);

    this.countriesSharedCollection = this.countriesService.addCountriesToCollectionIfMissing<ICountries>(
      this.countriesSharedCollection,
      organization.country
    );
  }

  protected loadRelationshipsOptions(): void {
    this.countriesService
      .query()
      .pipe(map((res: HttpResponse<ICountries[]>) => res.body ?? []))
      .pipe(
        map((countries: ICountries[]) =>
          this.countriesService.addCountriesToCollectionIfMissing<ICountries>(countries, this.organization?.country)
        )
      )
      .subscribe((countries: ICountries[]) => (this.countriesSharedCollection = countries));
  }
}

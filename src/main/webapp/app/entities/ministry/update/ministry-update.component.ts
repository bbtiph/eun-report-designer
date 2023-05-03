import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { MinistryFormService, MinistryFormGroup } from './ministry-form.service';
import { IMinistry } from '../ministry.model';
import { MinistryService } from '../service/ministry.service';
import { IOrganizationInMinistry } from 'app/entities/organization-in-ministry/organization-in-ministry.model';
import { OrganizationInMinistryService } from 'app/entities/organization-in-ministry/service/organization-in-ministry.service';

@Component({
  selector: 'jhi-ministry-update',
  templateUrl: './ministry-update.component.html',
})
export class MinistryUpdateComponent implements OnInit {
  isSaving = false;
  ministry: IMinistry | null = null;

  organizationInMinistriesSharedCollection: IOrganizationInMinistry[] = [];

  editForm: MinistryFormGroup = this.ministryFormService.createMinistryFormGroup();

  constructor(
    protected ministryService: MinistryService,
    protected ministryFormService: MinistryFormService,
    protected organizationInMinistryService: OrganizationInMinistryService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareOrganizationInMinistry = (o1: IOrganizationInMinistry | null, o2: IOrganizationInMinistry | null): boolean =>
    this.organizationInMinistryService.compareOrganizationInMinistry(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ministry }) => {
      this.ministry = ministry;
      if (ministry) {
        this.updateForm(ministry);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ministry = this.ministryFormService.getMinistry(this.editForm);
    if (ministry.id !== null) {
      this.subscribeToSaveResponse(this.ministryService.update(ministry));
    } else {
      this.subscribeToSaveResponse(this.ministryService.create(ministry));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMinistry>>): void {
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

  protected updateForm(ministry: IMinistry): void {
    this.ministry = ministry;
    this.ministryFormService.resetForm(this.editForm, ministry);

    this.organizationInMinistriesSharedCollection =
      this.organizationInMinistryService.addOrganizationInMinistryToCollectionIfMissing<IOrganizationInMinistry>(
        this.organizationInMinistriesSharedCollection,
        ministry.organizationInMinistry
      );
  }

  protected loadRelationshipsOptions(): void {
    this.organizationInMinistryService
      .query()
      .pipe(map((res: HttpResponse<IOrganizationInMinistry[]>) => res.body ?? []))
      .pipe(
        map((organizationInMinistries: IOrganizationInMinistry[]) =>
          this.organizationInMinistryService.addOrganizationInMinistryToCollectionIfMissing<IOrganizationInMinistry>(
            organizationInMinistries,
            this.ministry?.organizationInMinistry
          )
        )
      )
      .subscribe(
        (organizationInMinistries: IOrganizationInMinistry[]) => (this.organizationInMinistriesSharedCollection = organizationInMinistries)
      );
  }
}

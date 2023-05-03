import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { OrganizationInMinistryFormService, OrganizationInMinistryFormGroup } from './organization-in-ministry-form.service';
import { IOrganizationInMinistry } from '../organization-in-ministry.model';
import { OrganizationInMinistryService } from '../service/organization-in-ministry.service';

@Component({
  selector: 'jhi-organization-in-ministry-update',
  templateUrl: './organization-in-ministry-update.component.html',
})
export class OrganizationInMinistryUpdateComponent implements OnInit {
  isSaving = false;
  organizationInMinistry: IOrganizationInMinistry | null = null;

  editForm: OrganizationInMinistryFormGroup = this.organizationInMinistryFormService.createOrganizationInMinistryFormGroup();

  constructor(
    protected organizationInMinistryService: OrganizationInMinistryService,
    protected organizationInMinistryFormService: OrganizationInMinistryFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ organizationInMinistry }) => {
      this.organizationInMinistry = organizationInMinistry;
      if (organizationInMinistry) {
        this.updateForm(organizationInMinistry);
      }
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
  }
}

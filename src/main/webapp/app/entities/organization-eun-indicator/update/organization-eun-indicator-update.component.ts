import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { OrganizationEunIndicatorFormService, OrganizationEunIndicatorFormGroup } from './organization-eun-indicator-form.service';
import { IOrganizationEunIndicator } from '../organization-eun-indicator.model';
import { OrganizationEunIndicatorService } from '../service/organization-eun-indicator.service';

@Component({
  selector: 'jhi-organization-eun-indicator-update',
  templateUrl: './organization-eun-indicator-update.component.html',
})
export class OrganizationEunIndicatorUpdateComponent implements OnInit {
  isSaving = false;
  organizationEunIndicator: IOrganizationEunIndicator | null = null;

  editForm: OrganizationEunIndicatorFormGroup = this.organizationEunIndicatorFormService.createOrganizationEunIndicatorFormGroup();

  constructor(
    protected organizationEunIndicatorService: OrganizationEunIndicatorService,
    protected organizationEunIndicatorFormService: OrganizationEunIndicatorFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ organizationEunIndicator }) => {
      this.organizationEunIndicator = organizationEunIndicator;
      if (organizationEunIndicator) {
        this.updateForm(organizationEunIndicator);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const organizationEunIndicator = this.organizationEunIndicatorFormService.getOrganizationEunIndicator(this.editForm);
    if (organizationEunIndicator.id !== null) {
      this.subscribeToSaveResponse(this.organizationEunIndicatorService.update(organizationEunIndicator));
    } else {
      this.subscribeToSaveResponse(this.organizationEunIndicatorService.create(organizationEunIndicator));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrganizationEunIndicator>>): void {
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

  protected updateForm(organizationEunIndicator: IOrganizationEunIndicator): void {
    this.organizationEunIndicator = organizationEunIndicator;
    this.organizationEunIndicatorFormService.resetForm(this.editForm, organizationEunIndicator);
  }
}

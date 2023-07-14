import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ReferenceTableSettingsFormService, ReferenceTableSettingsFormGroup } from './reference-table-settings-form.service';
import { IReferenceTableSettings } from '../reference-table-settings.model';
import { ReferenceTableSettingsService } from '../service/reference-table-settings.service';

@Component({
  selector: 'jhi-reference-table-settings-update',
  templateUrl: './reference-table-settings-update.component.html',
})
export class ReferenceTableSettingsUpdateComponent implements OnInit {
  isSaving = false;
  referenceTableSettings: IReferenceTableSettings | null = null;

  editForm: ReferenceTableSettingsFormGroup = this.referenceTableSettingsFormService.createReferenceTableSettingsFormGroup();

  constructor(
    protected referenceTableSettingsService: ReferenceTableSettingsService,
    protected referenceTableSettingsFormService: ReferenceTableSettingsFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ referenceTableSettings }) => {
      this.referenceTableSettings = referenceTableSettings;
      if (referenceTableSettings) {
        this.updateForm(referenceTableSettings);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const referenceTableSettings = this.referenceTableSettingsFormService.getReferenceTableSettings(this.editForm);
    if (referenceTableSettings.id !== null) {
      this.subscribeToSaveResponse(this.referenceTableSettingsService.update(referenceTableSettings));
    } else {
      this.subscribeToSaveResponse(this.referenceTableSettingsService.create(referenceTableSettings));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReferenceTableSettings>>): void {
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

  protected updateForm(referenceTableSettings: IReferenceTableSettings): void {
    this.referenceTableSettings = referenceTableSettings;
    this.referenceTableSettingsFormService.resetForm(this.editForm, referenceTableSettings);
  }
}

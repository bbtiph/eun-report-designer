import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { WorkingGroupReferencesFormService, WorkingGroupReferencesFormGroup } from './working-group-references-form.service';
import { IWorkingGroupReferences } from '../working-group-references.model';
import { WorkingGroupReferencesService } from '../service/working-group-references.service';

@Component({
  selector: 'jhi-working-group-references-update',
  templateUrl: './working-group-references-update.component.html',
})
export class WorkingGroupReferencesUpdateComponent implements OnInit {
  isSaving = false;
  workingGroupReferences: IWorkingGroupReferences | null = null;

  editForm: WorkingGroupReferencesFormGroup = this.workingGroupReferencesFormService.createWorkingGroupReferencesFormGroup();

  constructor(
    protected workingGroupReferencesService: WorkingGroupReferencesService,
    protected workingGroupReferencesFormService: WorkingGroupReferencesFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ workingGroupReferences }) => {
      this.workingGroupReferences = workingGroupReferences;
      if (workingGroupReferences) {
        this.updateForm(workingGroupReferences);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const workingGroupReferences = this.workingGroupReferencesFormService.getWorkingGroupReferences(this.editForm);
    if (workingGroupReferences.id !== null) {
      this.subscribeToSaveResponse(this.workingGroupReferencesService.update(workingGroupReferences));
    } else {
      this.subscribeToSaveResponse(this.workingGroupReferencesService.create(workingGroupReferences));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWorkingGroupReferences>>): void {
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

  protected updateForm(workingGroupReferences: IWorkingGroupReferences): void {
    this.workingGroupReferences = workingGroupReferences;
    this.workingGroupReferencesFormService.resetForm(this.editForm, workingGroupReferences);
  }
}

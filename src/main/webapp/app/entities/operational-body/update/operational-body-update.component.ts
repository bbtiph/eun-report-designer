import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { OperationalBodyFormService, OperationalBodyFormGroup } from './operational-body-form.service';
import { IOperationalBody } from '../operational-body.model';
import { OperationalBodyService } from '../service/operational-body.service';

@Component({
  selector: 'jhi-operational-body-update',
  templateUrl: './operational-body-update.component.html',
})
export class OperationalBodyUpdateComponent implements OnInit {
  isSaving = false;
  operationalBody: IOperationalBody | null = null;

  editForm: OperationalBodyFormGroup = this.operationalBodyFormService.createOperationalBodyFormGroup();

  constructor(
    protected operationalBodyService: OperationalBodyService,
    protected operationalBodyFormService: OperationalBodyFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ operationalBody }) => {
      this.operationalBody = operationalBody;
      if (operationalBody) {
        this.updateForm(operationalBody);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const operationalBody = this.operationalBodyFormService.getOperationalBody(this.editForm);
    if (operationalBody.id !== null) {
      this.subscribeToSaveResponse(this.operationalBodyService.update(operationalBody));
    } else {
      this.subscribeToSaveResponse(this.operationalBodyService.create(operationalBody));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOperationalBody>>): void {
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

  protected updateForm(operationalBody: IOperationalBody): void {
    this.operationalBody = operationalBody;
    this.operationalBodyFormService.resetForm(this.editForm, operationalBody);
  }
}

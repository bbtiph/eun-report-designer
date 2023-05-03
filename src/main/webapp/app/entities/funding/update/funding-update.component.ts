import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { FundingFormService, FundingFormGroup } from './funding-form.service';
import { IFunding } from '../funding.model';
import { FundingService } from '../service/funding.service';

@Component({
  selector: 'jhi-funding-update',
  templateUrl: './funding-update.component.html',
})
export class FundingUpdateComponent implements OnInit {
  isSaving = false;
  funding: IFunding | null = null;

  editForm: FundingFormGroup = this.fundingFormService.createFundingFormGroup();

  constructor(
    protected fundingService: FundingService,
    protected fundingFormService: FundingFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ funding }) => {
      this.funding = funding;
      if (funding) {
        this.updateForm(funding);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const funding = this.fundingFormService.getFunding(this.editForm);
    if (funding.id !== null) {
      this.subscribeToSaveResponse(this.fundingService.update(funding));
    } else {
      this.subscribeToSaveResponse(this.fundingService.create(funding));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFunding>>): void {
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

  protected updateForm(funding: IFunding): void {
    this.funding = funding;
    this.fundingFormService.resetForm(this.editForm, funding);
  }
}

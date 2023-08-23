import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { PersonEunIndicatorFormService, PersonEunIndicatorFormGroup } from './person-eun-indicator-form.service';
import { IPersonEunIndicator } from '../person-eun-indicator.model';
import { PersonEunIndicatorService } from '../service/person-eun-indicator.service';

@Component({
  selector: 'jhi-person-eun-indicator-update',
  templateUrl: './person-eun-indicator-update.component.html',
})
export class PersonEunIndicatorUpdateComponent implements OnInit {
  isSaving = false;
  personEunIndicator: IPersonEunIndicator | null = null;

  editForm: PersonEunIndicatorFormGroup = this.personEunIndicatorFormService.createPersonEunIndicatorFormGroup();

  constructor(
    protected personEunIndicatorService: PersonEunIndicatorService,
    protected personEunIndicatorFormService: PersonEunIndicatorFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ personEunIndicator }) => {
      this.personEunIndicator = personEunIndicator;
      if (personEunIndicator) {
        this.updateForm(personEunIndicator);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const personEunIndicator = this.personEunIndicatorFormService.getPersonEunIndicator(this.editForm);
    if (personEunIndicator.id !== null) {
      this.subscribeToSaveResponse(this.personEunIndicatorService.update(personEunIndicator));
    } else {
      this.subscribeToSaveResponse(this.personEunIndicatorService.create(personEunIndicator));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPersonEunIndicator>>): void {
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

  protected updateForm(personEunIndicator: IPersonEunIndicator): void {
    this.personEunIndicator = personEunIndicator;
    this.personEunIndicatorFormService.resetForm(this.editForm, personEunIndicator);
  }
}

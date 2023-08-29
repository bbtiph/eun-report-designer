import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ParticipantsEunIndicatorFormService, ParticipantsEunIndicatorFormGroup } from './participants-eun-indicator-form.service';
import { IParticipantsEunIndicator } from '../participants-eun-indicator.model';
import { ParticipantsEunIndicatorService } from '../service/participants-eun-indicator.service';

@Component({
  selector: 'jhi-participants-eun-indicator-update',
  templateUrl: './participants-eun-indicator-update.component.html',
})
export class ParticipantsEunIndicatorUpdateComponent implements OnInit {
  isSaving = false;
  participantsEunIndicator: IParticipantsEunIndicator | null = null;

  editForm: ParticipantsEunIndicatorFormGroup = this.participantsEunIndicatorFormService.createParticipantsEunIndicatorFormGroup();

  constructor(
    protected participantsEunIndicatorService: ParticipantsEunIndicatorService,
    protected participantsEunIndicatorFormService: ParticipantsEunIndicatorFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ participantsEunIndicator }) => {
      this.participantsEunIndicator = participantsEunIndicator;
      if (participantsEunIndicator) {
        this.updateForm(participantsEunIndicator);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const participantsEunIndicator = this.participantsEunIndicatorFormService.getParticipantsEunIndicator(this.editForm);
    if (participantsEunIndicator.id !== null) {
      this.subscribeToSaveResponse(this.participantsEunIndicatorService.update(participantsEunIndicator));
    } else {
      this.subscribeToSaveResponse(this.participantsEunIndicatorService.create(participantsEunIndicator));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IParticipantsEunIndicator>>): void {
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

  protected updateForm(participantsEunIndicator: IParticipantsEunIndicator): void {
    this.participantsEunIndicator = participantsEunIndicator;
    this.participantsEunIndicatorFormService.resetForm(this.editForm, participantsEunIndicator);
  }
}

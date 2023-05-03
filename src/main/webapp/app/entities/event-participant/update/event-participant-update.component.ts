import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { EventParticipantFormService, EventParticipantFormGroup } from './event-participant-form.service';
import { IEventParticipant } from '../event-participant.model';
import { EventParticipantService } from '../service/event-participant.service';

@Component({
  selector: 'jhi-event-participant-update',
  templateUrl: './event-participant-update.component.html',
})
export class EventParticipantUpdateComponent implements OnInit {
  isSaving = false;
  eventParticipant: IEventParticipant | null = null;

  editForm: EventParticipantFormGroup = this.eventParticipantFormService.createEventParticipantFormGroup();

  constructor(
    protected eventParticipantService: EventParticipantService,
    protected eventParticipantFormService: EventParticipantFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ eventParticipant }) => {
      this.eventParticipant = eventParticipant;
      if (eventParticipant) {
        this.updateForm(eventParticipant);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const eventParticipant = this.eventParticipantFormService.getEventParticipant(this.editForm);
    if (eventParticipant.id !== null) {
      this.subscribeToSaveResponse(this.eventParticipantService.update(eventParticipant));
    } else {
      this.subscribeToSaveResponse(this.eventParticipantService.create(eventParticipant));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEventParticipant>>): void {
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

  protected updateForm(eventParticipant: IEventParticipant): void {
    this.eventParticipant = eventParticipant;
    this.eventParticipantFormService.resetForm(this.editForm, eventParticipant);
  }
}

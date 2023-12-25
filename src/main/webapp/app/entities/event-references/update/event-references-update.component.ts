import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { EventReferencesFormService, EventReferencesFormGroup } from './event-references-form.service';
import { IEventReferences } from '../event-references.model';
import { EventReferencesService } from '../service/event-references.service';

@Component({
  selector: 'jhi-event-references-update',
  templateUrl: './event-references-update.component.html',
})
export class EventReferencesUpdateComponent implements OnInit {
  isSaving = false;
  eventReferences: IEventReferences | null = null;

  editForm: EventReferencesFormGroup = this.eventReferencesFormService.createEventReferencesFormGroup();

  constructor(
    protected eventReferencesService: EventReferencesService,
    protected eventReferencesFormService: EventReferencesFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ eventReferences }) => {
      this.eventReferences = eventReferences;
      if (eventReferences) {
        this.updateForm(eventReferences);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const eventReferences = this.eventReferencesFormService.getEventReferences(this.editForm);
    if (eventReferences.id !== null) {
      this.subscribeToSaveResponse(this.eventReferencesService.update(eventReferences));
    } else {
      this.subscribeToSaveResponse(this.eventReferencesService.create(eventReferences));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEventReferences>>): void {
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

  protected updateForm(eventReferences: IEventReferences): void {
    this.eventReferences = eventReferences;
    this.eventReferencesFormService.resetForm(this.editForm, eventReferences);
  }
}

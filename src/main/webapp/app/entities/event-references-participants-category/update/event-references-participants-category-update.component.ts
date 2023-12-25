import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import {
  EventReferencesParticipantsCategoryFormService,
  EventReferencesParticipantsCategoryFormGroup,
} from './event-references-participants-category-form.service';
import { IEventReferencesParticipantsCategory } from '../event-references-participants-category.model';
import { EventReferencesParticipantsCategoryService } from '../service/event-references-participants-category.service';
import { IEventReferences } from 'app/entities/event-references/event-references.model';
import { EventReferencesService } from 'app/entities/event-references/service/event-references.service';

@Component({
  selector: 'jhi-event-references-participants-category-update',
  templateUrl: './event-references-participants-category-update.component.html',
})
export class EventReferencesParticipantsCategoryUpdateComponent implements OnInit {
  isSaving = false;
  eventReferencesParticipantsCategory: IEventReferencesParticipantsCategory | null = null;

  eventReferencesSharedCollection: IEventReferences[] = [];

  editForm: EventReferencesParticipantsCategoryFormGroup =
    this.eventReferencesParticipantsCategoryFormService.createEventReferencesParticipantsCategoryFormGroup();

  constructor(
    protected eventReferencesParticipantsCategoryService: EventReferencesParticipantsCategoryService,
    protected eventReferencesParticipantsCategoryFormService: EventReferencesParticipantsCategoryFormService,
    protected eventReferencesService: EventReferencesService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareEventReferences = (o1: IEventReferences | null, o2: IEventReferences | null): boolean =>
    this.eventReferencesService.compareEventReferences(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ eventReferencesParticipantsCategory }) => {
      this.eventReferencesParticipantsCategory = eventReferencesParticipantsCategory;
      if (eventReferencesParticipantsCategory) {
        this.updateForm(eventReferencesParticipantsCategory);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const eventReferencesParticipantsCategory = this.eventReferencesParticipantsCategoryFormService.getEventReferencesParticipantsCategory(
      this.editForm
    );
    if (eventReferencesParticipantsCategory.id !== null) {
      this.subscribeToSaveResponse(this.eventReferencesParticipantsCategoryService.update(eventReferencesParticipantsCategory));
    } else {
      this.subscribeToSaveResponse(this.eventReferencesParticipantsCategoryService.create(eventReferencesParticipantsCategory));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEventReferencesParticipantsCategory>>): void {
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

  protected updateForm(eventReferencesParticipantsCategory: IEventReferencesParticipantsCategory): void {
    this.eventReferencesParticipantsCategory = eventReferencesParticipantsCategory;
    this.eventReferencesParticipantsCategoryFormService.resetForm(this.editForm, eventReferencesParticipantsCategory);

    this.eventReferencesSharedCollection = this.eventReferencesService.addEventReferencesToCollectionIfMissing<IEventReferences>(
      this.eventReferencesSharedCollection,
      eventReferencesParticipantsCategory.eventReference
    );
  }

  protected loadRelationshipsOptions(): void {
    this.eventReferencesService
      .query()
      .pipe(map((res: HttpResponse<IEventReferences[]>) => res.body ?? []))
      .pipe(
        map((eventReferences: IEventReferences[]) =>
          this.eventReferencesService.addEventReferencesToCollectionIfMissing<IEventReferences>(
            eventReferences,
            this.eventReferencesParticipantsCategory?.eventReference
          )
        )
      )
      .subscribe((eventReferences: IEventReferences[]) => (this.eventReferencesSharedCollection = eventReferences));
  }
}

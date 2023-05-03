import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { EventFormService, EventFormGroup } from './event-form.service';
import { IEvent } from '../event.model';
import { EventService } from '../service/event.service';
import { IEventInOrganization } from 'app/entities/event-in-organization/event-in-organization.model';
import { EventInOrganizationService } from 'app/entities/event-in-organization/service/event-in-organization.service';
import { IEventParticipant } from 'app/entities/event-participant/event-participant.model';
import { EventParticipantService } from 'app/entities/event-participant/service/event-participant.service';

@Component({
  selector: 'jhi-event-update',
  templateUrl: './event-update.component.html',
})
export class EventUpdateComponent implements OnInit {
  isSaving = false;
  event: IEvent | null = null;

  eventInOrganizationsSharedCollection: IEventInOrganization[] = [];
  eventParticipantsSharedCollection: IEventParticipant[] = [];

  editForm: EventFormGroup = this.eventFormService.createEventFormGroup();

  constructor(
    protected eventService: EventService,
    protected eventFormService: EventFormService,
    protected eventInOrganizationService: EventInOrganizationService,
    protected eventParticipantService: EventParticipantService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareEventInOrganization = (o1: IEventInOrganization | null, o2: IEventInOrganization | null): boolean =>
    this.eventInOrganizationService.compareEventInOrganization(o1, o2);

  compareEventParticipant = (o1: IEventParticipant | null, o2: IEventParticipant | null): boolean =>
    this.eventParticipantService.compareEventParticipant(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ event }) => {
      this.event = event;
      if (event) {
        this.updateForm(event);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const event = this.eventFormService.getEvent(this.editForm);
    if (event.id !== null) {
      this.subscribeToSaveResponse(this.eventService.update(event));
    } else {
      this.subscribeToSaveResponse(this.eventService.create(event));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEvent>>): void {
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

  protected updateForm(event: IEvent): void {
    this.event = event;
    this.eventFormService.resetForm(this.editForm, event);

    this.eventInOrganizationsSharedCollection =
      this.eventInOrganizationService.addEventInOrganizationToCollectionIfMissing<IEventInOrganization>(
        this.eventInOrganizationsSharedCollection,
        event.eventInOrganization
      );
    this.eventParticipantsSharedCollection = this.eventParticipantService.addEventParticipantToCollectionIfMissing<IEventParticipant>(
      this.eventParticipantsSharedCollection,
      event.eventParticipant
    );
  }

  protected loadRelationshipsOptions(): void {
    this.eventInOrganizationService
      .query()
      .pipe(map((res: HttpResponse<IEventInOrganization[]>) => res.body ?? []))
      .pipe(
        map((eventInOrganizations: IEventInOrganization[]) =>
          this.eventInOrganizationService.addEventInOrganizationToCollectionIfMissing<IEventInOrganization>(
            eventInOrganizations,
            this.event?.eventInOrganization
          )
        )
      )
      .subscribe((eventInOrganizations: IEventInOrganization[]) => (this.eventInOrganizationsSharedCollection = eventInOrganizations));

    this.eventParticipantService
      .query()
      .pipe(map((res: HttpResponse<IEventParticipant[]>) => res.body ?? []))
      .pipe(
        map((eventParticipants: IEventParticipant[]) =>
          this.eventParticipantService.addEventParticipantToCollectionIfMissing<IEventParticipant>(
            eventParticipants,
            this.event?.eventParticipant
          )
        )
      )
      .subscribe((eventParticipants: IEventParticipant[]) => (this.eventParticipantsSharedCollection = eventParticipants));
  }
}

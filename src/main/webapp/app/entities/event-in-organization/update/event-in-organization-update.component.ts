import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { EventInOrganizationFormService, EventInOrganizationFormGroup } from './event-in-organization-form.service';
import { IEventInOrganization } from '../event-in-organization.model';
import { EventInOrganizationService } from '../service/event-in-organization.service';
import { IEvent } from 'app/entities/event/event.model';
import { EventService } from 'app/entities/event/service/event.service';
import { IOrganization } from 'app/entities/organization/organization.model';
import { OrganizationService } from 'app/entities/organization/service/organization.service';

@Component({
  selector: 'jhi-event-in-organization-update',
  templateUrl: './event-in-organization-update.component.html',
})
export class EventInOrganizationUpdateComponent implements OnInit {
  isSaving = false;
  eventInOrganization: IEventInOrganization | null = null;

  eventsSharedCollection: IEvent[] = [];
  organizationsSharedCollection: IOrganization[] = [];

  editForm: EventInOrganizationFormGroup = this.eventInOrganizationFormService.createEventInOrganizationFormGroup();

  constructor(
    protected eventInOrganizationService: EventInOrganizationService,
    protected eventInOrganizationFormService: EventInOrganizationFormService,
    protected eventService: EventService,
    protected organizationService: OrganizationService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareEvent = (o1: IEvent | null, o2: IEvent | null): boolean => this.eventService.compareEvent(o1, o2);

  compareOrganization = (o1: IOrganization | null, o2: IOrganization | null): boolean =>
    this.organizationService.compareOrganization(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ eventInOrganization }) => {
      this.eventInOrganization = eventInOrganization;
      if (eventInOrganization) {
        this.updateForm(eventInOrganization);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const eventInOrganization = this.eventInOrganizationFormService.getEventInOrganization(this.editForm);
    if (eventInOrganization.id !== null) {
      this.subscribeToSaveResponse(this.eventInOrganizationService.update(eventInOrganization));
    } else {
      this.subscribeToSaveResponse(this.eventInOrganizationService.create(eventInOrganization));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEventInOrganization>>): void {
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

  protected updateForm(eventInOrganization: IEventInOrganization): void {
    this.eventInOrganization = eventInOrganization;
    this.eventInOrganizationFormService.resetForm(this.editForm, eventInOrganization);

    this.eventsSharedCollection = this.eventService.addEventToCollectionIfMissing<IEvent>(
      this.eventsSharedCollection,
      eventInOrganization.event
    );
    this.organizationsSharedCollection = this.organizationService.addOrganizationToCollectionIfMissing<IOrganization>(
      this.organizationsSharedCollection,
      eventInOrganization.organization
    );
  }

  protected loadRelationshipsOptions(): void {
    this.eventService
      .query()
      .pipe(map((res: HttpResponse<IEvent[]>) => res.body ?? []))
      .pipe(map((events: IEvent[]) => this.eventService.addEventToCollectionIfMissing<IEvent>(events, this.eventInOrganization?.event)))
      .subscribe((events: IEvent[]) => (this.eventsSharedCollection = events));

    this.organizationService
      .query()
      .pipe(map((res: HttpResponse<IOrganization[]>) => res.body ?? []))
      .pipe(
        map((organizations: IOrganization[]) =>
          this.organizationService.addOrganizationToCollectionIfMissing<IOrganization>(
            organizations,
            this.eventInOrganization?.organization
          )
        )
      )
      .subscribe((organizations: IOrganization[]) => (this.organizationsSharedCollection = organizations));
  }
}

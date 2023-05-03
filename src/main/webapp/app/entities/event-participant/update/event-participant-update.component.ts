import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { EventParticipantFormService, EventParticipantFormGroup } from './event-participant-form.service';
import { IEventParticipant } from '../event-participant.model';
import { EventParticipantService } from '../service/event-participant.service';
import { IEvent } from 'app/entities/event/event.model';
import { EventService } from 'app/entities/event/service/event.service';
import { IPerson } from 'app/entities/person/person.model';
import { PersonService } from 'app/entities/person/service/person.service';

@Component({
  selector: 'jhi-event-participant-update',
  templateUrl: './event-participant-update.component.html',
})
export class EventParticipantUpdateComponent implements OnInit {
  isSaving = false;
  eventParticipant: IEventParticipant | null = null;

  eventsSharedCollection: IEvent[] = [];
  peopleSharedCollection: IPerson[] = [];

  editForm: EventParticipantFormGroup = this.eventParticipantFormService.createEventParticipantFormGroup();

  constructor(
    protected eventParticipantService: EventParticipantService,
    protected eventParticipantFormService: EventParticipantFormService,
    protected eventService: EventService,
    protected personService: PersonService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareEvent = (o1: IEvent | null, o2: IEvent | null): boolean => this.eventService.compareEvent(o1, o2);

  comparePerson = (o1: IPerson | null, o2: IPerson | null): boolean => this.personService.comparePerson(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ eventParticipant }) => {
      this.eventParticipant = eventParticipant;
      if (eventParticipant) {
        this.updateForm(eventParticipant);
      }

      this.loadRelationshipsOptions();
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

    this.eventsSharedCollection = this.eventService.addEventToCollectionIfMissing<IEvent>(
      this.eventsSharedCollection,
      eventParticipant.event
    );
    this.peopleSharedCollection = this.personService.addPersonToCollectionIfMissing<IPerson>(
      this.peopleSharedCollection,
      eventParticipant.person
    );
  }

  protected loadRelationshipsOptions(): void {
    this.eventService
      .query()
      .pipe(map((res: HttpResponse<IEvent[]>) => res.body ?? []))
      .pipe(map((events: IEvent[]) => this.eventService.addEventToCollectionIfMissing<IEvent>(events, this.eventParticipant?.event)))
      .subscribe((events: IEvent[]) => (this.eventsSharedCollection = events));

    this.personService
      .query()
      .pipe(map((res: HttpResponse<IPerson[]>) => res.body ?? []))
      .pipe(map((people: IPerson[]) => this.personService.addPersonToCollectionIfMissing<IPerson>(people, this.eventParticipant?.person)))
      .subscribe((people: IPerson[]) => (this.peopleSharedCollection = people));
  }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEventParticipant } from '../event-participant.model';

@Component({
  selector: 'jhi-event-participant-detail',
  templateUrl: './event-participant-detail.component.html',
})
export class EventParticipantDetailComponent implements OnInit {
  eventParticipant: IEventParticipant | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ eventParticipant }) => {
      this.eventParticipant = eventParticipant;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

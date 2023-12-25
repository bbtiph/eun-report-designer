import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEventReferencesParticipantsCategory } from '../event-references-participants-category.model';

@Component({
  selector: 'jhi-event-references-participants-category-detail',
  templateUrl: './event-references-participants-category-detail.component.html',
})
export class EventReferencesParticipantsCategoryDetailComponent implements OnInit {
  eventReferencesParticipantsCategory: IEventReferencesParticipantsCategory | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ eventReferencesParticipantsCategory }) => {
      this.eventReferencesParticipantsCategory = eventReferencesParticipantsCategory;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

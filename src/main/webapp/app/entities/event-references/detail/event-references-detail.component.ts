import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEventReferences } from '../event-references.model';

@Component({
  selector: 'jhi-event-references-detail',
  templateUrl: './event-references-detail.component.html',
})
export class EventReferencesDetailComponent implements OnInit {
  eventReferences: IEventReferences | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ eventReferences }) => {
      this.eventReferences = eventReferences;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

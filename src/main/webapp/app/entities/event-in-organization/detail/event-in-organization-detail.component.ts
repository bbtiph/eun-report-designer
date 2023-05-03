import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEventInOrganization } from '../event-in-organization.model';

@Component({
  selector: 'jhi-event-in-organization-detail',
  templateUrl: './event-in-organization-detail.component.html',
})
export class EventInOrganizationDetailComponent implements OnInit {
  eventInOrganization: IEventInOrganization | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ eventInOrganization }) => {
      this.eventInOrganization = eventInOrganization;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPersonInOrganization } from '../person-in-organization.model';

@Component({
  selector: 'jhi-person-in-organization-detail',
  templateUrl: './person-in-organization-detail.component.html',
})
export class PersonInOrganizationDetailComponent implements OnInit {
  personInOrganization: IPersonInOrganization | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ personInOrganization }) => {
      this.personInOrganization = personInOrganization;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

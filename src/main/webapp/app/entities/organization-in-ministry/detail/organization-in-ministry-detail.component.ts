import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOrganizationInMinistry } from '../organization-in-ministry.model';

@Component({
  selector: 'jhi-organization-in-ministry-detail',
  templateUrl: './organization-in-ministry-detail.component.html',
})
export class OrganizationInMinistryDetailComponent implements OnInit {
  organizationInMinistry: IOrganizationInMinistry | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ organizationInMinistry }) => {
      this.organizationInMinistry = organizationInMinistry;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

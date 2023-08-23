import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOrganizationEunIndicator } from '../organization-eun-indicator.model';

@Component({
  selector: 'jhi-organization-eun-indicator-detail',
  templateUrl: './organization-eun-indicator-detail.component.html',
})
export class OrganizationEunIndicatorDetailComponent implements OnInit {
  organizationEunIndicator: IOrganizationEunIndicator | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ organizationEunIndicator }) => {
      this.organizationEunIndicator = organizationEunIndicator;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOrganizationInProject } from '../organization-in-project.model';

@Component({
  selector: 'jhi-organization-in-project-detail',
  templateUrl: './organization-in-project-detail.component.html',
})
export class OrganizationInProjectDetailComponent implements OnInit {
  organizationInProject: IOrganizationInProject | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ organizationInProject }) => {
      this.organizationInProject = organizationInProject;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

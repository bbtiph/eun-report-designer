import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProjectPartner } from '../project-partner.model';

@Component({
  selector: 'jhi-project-partner-detail',
  templateUrl: './project-partner-detail.component.html',
})
export class ProjectPartnerDetailComponent implements OnInit {
  projectPartner: IProjectPartner | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ projectPartner }) => {
      this.projectPartner = projectPartner;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

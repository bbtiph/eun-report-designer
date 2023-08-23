import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPersonEunIndicator } from '../person-eun-indicator.model';

@Component({
  selector: 'jhi-person-eun-indicator-detail',
  templateUrl: './person-eun-indicator-detail.component.html',
})
export class PersonEunIndicatorDetailComponent implements OnInit {
  personEunIndicator: IPersonEunIndicator | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ personEunIndicator }) => {
      this.personEunIndicator = personEunIndicator;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

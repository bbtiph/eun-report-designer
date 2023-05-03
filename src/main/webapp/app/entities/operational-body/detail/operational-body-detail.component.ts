import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOperationalBody } from '../operational-body.model';

@Component({
  selector: 'jhi-operational-body-detail',
  templateUrl: './operational-body-detail.component.html',
})
export class OperationalBodyDetailComponent implements OnInit {
  operationalBody: IOperationalBody | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ operationalBody }) => {
      this.operationalBody = operationalBody;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

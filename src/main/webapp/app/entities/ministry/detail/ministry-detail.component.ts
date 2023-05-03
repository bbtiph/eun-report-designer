import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMinistry } from '../ministry.model';

@Component({
  selector: 'jhi-ministry-detail',
  templateUrl: './ministry-detail.component.html',
})
export class MinistryDetailComponent implements OnInit {
  ministry: IMinistry | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ministry }) => {
      this.ministry = ministry;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

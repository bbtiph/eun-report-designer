import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IReferenceTableSettings } from '../reference-table-settings.model';

@Component({
  selector: 'jhi-reference-table-settings-detail',
  templateUrl: './reference-table-settings-detail.component.html',
})
export class ReferenceTableSettingsDetailComponent implements OnInit {
  referenceTableSettings: IReferenceTableSettings | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ referenceTableSettings }) => {
      this.referenceTableSettings = referenceTableSettings;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

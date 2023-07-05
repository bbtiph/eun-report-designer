import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IWorkingGroupReferences } from '../working-group-references.model';

@Component({
  selector: 'jhi-working-group-references-detail',
  templateUrl: './working-group-references-detail.component.html',
})
export class WorkingGroupReferencesDetailComponent implements OnInit {
  workingGroupReferences: IWorkingGroupReferences | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ workingGroupReferences }) => {
      this.workingGroupReferences = workingGroupReferences;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

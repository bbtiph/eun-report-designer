import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMOEParticipationReferences } from '../moe-participation-references.model';

@Component({
  selector: 'jhi-moe-participation-references-detail',
  templateUrl: './moe-participation-references-detail.component.html',
})
export class MOEParticipationReferencesDetailComponent implements OnInit {
  mOEParticipationReferences: IMOEParticipationReferences | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mOEParticipationReferences }) => {
      this.mOEParticipationReferences = mOEParticipationReferences;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

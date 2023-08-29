import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IParticipantsEunIndicator } from '../participants-eun-indicator.model';

@Component({
  selector: 'jhi-participants-eun-indicator-detail',
  templateUrl: './participants-eun-indicator-detail.component.html',
})
export class ParticipantsEunIndicatorDetailComponent implements OnInit {
  participantsEunIndicator: IParticipantsEunIndicator | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ participantsEunIndicator }) => {
      this.participantsEunIndicator = participantsEunIndicator;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

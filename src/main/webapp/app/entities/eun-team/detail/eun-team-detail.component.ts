import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEunTeam } from '../eun-team.model';

@Component({
  selector: 'jhi-eun-team-detail',
  templateUrl: './eun-team-detail.component.html',
})
export class EunTeamDetailComponent implements OnInit {
  eunTeam: IEunTeam | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ eunTeam }) => {
      this.eunTeam = eunTeam;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

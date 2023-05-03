import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEunTeamMember } from '../eun-team-member.model';

@Component({
  selector: 'jhi-eun-team-member-detail',
  templateUrl: './eun-team-member-detail.component.html',
})
export class EunTeamMemberDetailComponent implements OnInit {
  eunTeamMember: IEunTeamMember | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ eunTeamMember }) => {
      this.eunTeamMember = eunTeamMember;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

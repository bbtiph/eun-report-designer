import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOperationalBodyMember } from '../operational-body-member.model';

@Component({
  selector: 'jhi-operational-body-member-detail',
  templateUrl: './operational-body-member-detail.component.html',
})
export class OperationalBodyMemberDetailComponent implements OnInit {
  operationalBodyMember: IOperationalBodyMember | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ operationalBodyMember }) => {
      this.operationalBodyMember = operationalBodyMember;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

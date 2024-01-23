import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IJobInfo } from '../job-info.model';

@Component({
  selector: 'jhi-job-info-detail',
  templateUrl: './job-info-detail.component.html',
})
export class JobInfoDetailComponent implements OnInit {
  jobInfo: IJobInfo | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ jobInfo }) => {
      this.jobInfo = jobInfo;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

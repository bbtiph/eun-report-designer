import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IReportBlocks } from '../report-blocks.model';

@Component({
  selector: 'jhi-report-blocks-detail',
  templateUrl: './report-blocks-detail.component.html',
})
export class ReportBlocksDetailComponent implements OnInit {
  reportBlocks: IReportBlocks | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ reportBlocks }) => {
      this.reportBlocks = reportBlocks;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IReportBlocksContentData } from '../report-blocks-content-data.model';

@Component({
  selector: 'jhi-report-blocks-content-data-detail',
  templateUrl: './report-blocks-content-data-detail.component.html',
})
export class ReportBlocksContentDataDetailComponent implements OnInit {
  reportBlocksContentData: IReportBlocksContentData | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ reportBlocksContentData }) => {
      this.reportBlocksContentData = reportBlocksContentData;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

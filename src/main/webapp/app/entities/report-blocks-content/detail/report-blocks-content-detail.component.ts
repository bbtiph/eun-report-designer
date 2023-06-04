import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IReportBlocksContent } from '../report-blocks-content.model';

@Component({
  selector: 'jhi-report-blocks-content-detail',
  templateUrl: './report-blocks-content-detail.component.html',
})
export class ReportBlocksContentDetailComponent implements OnInit {
  reportBlocksContent: IReportBlocksContent | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ reportBlocksContent }) => {
      this.reportBlocksContent = reportBlocksContent;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

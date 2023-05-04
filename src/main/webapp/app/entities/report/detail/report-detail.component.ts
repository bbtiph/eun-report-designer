import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IReport } from '../report.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-report-detail',
  templateUrl: './report-detail.component.html',
})
export class ReportDetailComponent implements OnInit {
  report: IReport | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ report }) => {
      this.report = report;
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  previousState(): void {
    window.history.back();
  }
}

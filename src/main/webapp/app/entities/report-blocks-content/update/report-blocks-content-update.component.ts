import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ReportBlocksContentFormService, ReportBlocksContentFormGroup } from './report-blocks-content-form.service';
import { IReportBlocksContent } from '../report-blocks-content.model';
import { ReportBlocksContentService } from '../service/report-blocks-content.service';
import { IReportBlocks } from 'app/entities/report-blocks/report-blocks.model';
import { ReportBlocksService } from 'app/entities/report-blocks/service/report-blocks.service';

@Component({
  selector: 'jhi-report-blocks-content-update',
  templateUrl: './report-blocks-content-update.component.html',
})
export class ReportBlocksContentUpdateComponent implements OnInit {
  isSaving = false;
  reportBlocksContent: IReportBlocksContent | null = null;

  reportBlocksSharedCollection: IReportBlocks[] = [];

  editForm: ReportBlocksContentFormGroup = this.reportBlocksContentFormService.createReportBlocksContentFormGroup();

  constructor(
    protected reportBlocksContentService: ReportBlocksContentService,
    protected reportBlocksContentFormService: ReportBlocksContentFormService,
    protected reportBlocksService: ReportBlocksService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareReportBlocks = (o1: IReportBlocks | null, o2: IReportBlocks | null): boolean =>
    this.reportBlocksService.compareReportBlocks(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ reportBlocksContent }) => {
      this.reportBlocksContent = reportBlocksContent;
      if (reportBlocksContent) {
        this.updateForm(reportBlocksContent);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const reportBlocksContent = this.reportBlocksContentFormService.getReportBlocksContent(this.editForm);
    if (reportBlocksContent.id !== null) {
      this.subscribeToSaveResponse(this.reportBlocksContentService.update(reportBlocksContent));
    } else {
      // @ts-ignore
      this.subscribeToSaveResponse(this.reportBlocksContentService.create(reportBlocksContent));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReportBlocksContent>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(reportBlocksContent: IReportBlocksContent): void {
    this.reportBlocksContent = reportBlocksContent;
    this.reportBlocksContentFormService.resetForm(this.editForm, reportBlocksContent);

    this.reportBlocksSharedCollection = this.reportBlocksService.addReportBlocksToCollectionIfMissing<IReportBlocks>(
      this.reportBlocksSharedCollection,
      reportBlocksContent.reportBlocks
    );
  }

  protected loadRelationshipsOptions(): void {
    this.reportBlocksService
      .query()
      .pipe(map((res: HttpResponse<IReportBlocks[]>) => res.body ?? []))
      .pipe(
        map((reportBlocks: IReportBlocks[]) =>
          this.reportBlocksService.addReportBlocksToCollectionIfMissing<IReportBlocks>(reportBlocks, this.reportBlocksContent?.reportBlocks)
        )
      )
      .subscribe((reportBlocks: IReportBlocks[]) => (this.reportBlocksSharedCollection = reportBlocks));
  }
}

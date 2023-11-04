import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { GeneratedReportFormService, GeneratedReportFormGroup } from './generated-report-form.service';
import { IGeneratedReport } from '../generated-report.model';
import { GeneratedReportService } from '../service/generated-report.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-generated-report-update',
  templateUrl: './generated-report-update.component.html',
})
export class GeneratedReportUpdateComponent implements OnInit {
  isSaving = false;
  generatedReport: IGeneratedReport | null = null;

  editForm: GeneratedReportFormGroup = this.generatedReportFormService.createGeneratedReportFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected generatedReportService: GeneratedReportService,
    protected generatedReportFormService: GeneratedReportFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ generatedReport }) => {
      this.generatedReport = generatedReport;
      if (generatedReport) {
        this.updateForm(generatedReport);
      }
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('eunReportDesignerApp.error', { message: err.message })),
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const generatedReport = this.generatedReportFormService.getGeneratedReport(this.editForm);
    if (generatedReport.id !== null) {
      this.subscribeToSaveResponse(this.generatedReportService.update(generatedReport));
    } else {
      this.subscribeToSaveResponse(this.generatedReportService.create(generatedReport));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGeneratedReport>>): void {
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

  protected updateForm(generatedReport: IGeneratedReport): void {
    this.generatedReport = generatedReport;
    this.generatedReportFormService.resetForm(this.editForm, generatedReport);
  }
}

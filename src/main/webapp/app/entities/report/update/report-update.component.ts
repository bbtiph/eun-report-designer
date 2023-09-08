import { Component, OnInit, ElementRef, OnChanges, SimpleChanges } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ReportFormService, ReportFormGroup } from './report-form.service';
import { IReport } from '../report.model';
import { ReportService } from '../service/report.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IReportTemplate } from 'app/entities/report-template/report-template.model';
import { ReportTemplateService } from 'app/entities/report-template/service/report-template.service';

@Component({
  selector: 'jhi-report-update',
  templateUrl: './report-update.component.html',
  styleUrls: ['./report-update.component.scss'],
})
export class ReportUpdateComponent implements OnInit, OnChanges {
  isSaving = false;
  report: IReport | null = null;

  reportTemplatesSharedCollection: IReportTemplate[] = [];

  editForm: ReportFormGroup = this.reportFormService.createReportFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected reportService: ReportService,
    protected reportFormService: ReportFormService,
    protected reportTemplateService: ReportTemplateService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private router: Router
  ) {}

  compareReportTemplate = (o1: IReportTemplate | null, o2: IReportTemplate | null): boolean =>
    this.reportTemplateService.compareReportTemplate(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ report }) => {
      this.report = report;
      if (report) {
        this.updateForm(report);
      }

      this.loadRelationshipsOptions();
    });
    this.editForm.get('reportTemplate')?.valueChanges.subscribe(newValue => {
      // @ts-ignore
      const acronym = newValue?.name?.toLowerCase().replace(/\s+/g, '_');
      this.editForm.get('acronym')?.setValue(acronym);
      // @ts-ignore
      this.editForm.get('type')?.setValue(newValue?.type);
    });
  }

  ngOnChanges(changes: SimpleChanges): void {}

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

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    this.router.navigateByUrl('/report');
  }

  save(): void {
    this.isSaving = true;
    const report = this.reportFormService.getReport(this.editForm);
    if (report.id !== null) {
      this.subscribeToSaveResponse(this.reportService.update(report));
    } else {
      this.subscribeToSaveResponse(this.reportService.create(report));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReport>>): void {
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

  protected updateForm(report: IReport): void {
    this.report = report;
    this.reportFormService.resetForm(this.editForm, report);

    this.reportTemplatesSharedCollection = this.reportTemplateService.addReportTemplateToCollectionIfMissing<IReportTemplate>(
      this.reportTemplatesSharedCollection,
      report.reportTemplate
    );
  }

  protected loadRelationshipsOptions(): void {
    this.reportTemplateService
      .query()
      .pipe(map((res: HttpResponse<IReportTemplate[]>) => res.body ?? []))
      .pipe(
        map((reportTemplates: IReportTemplate[]) =>
          this.reportTemplateService.addReportTemplateToCollectionIfMissing<IReportTemplate>(reportTemplates, this.report?.reportTemplate)
        )
      )
      .subscribe((reportTemplates: IReportTemplate[]) => (this.reportTemplatesSharedCollection = reportTemplates));
  }
}

import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ReportTemplateFormService, ReportTemplateFormGroup } from './report-template-form.service';
import { IReportTemplate } from '../report-template.model';
import { ReportTemplateService } from '../service/report-template.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatDialog } from '@angular/material/dialog';
import { DialogConfirmComponent } from '../../../shared/dialog-confirm/dialog-confirm.component';

@Component({
  selector: 'jhi-report-template-update',
  templateUrl: './report-template-update.component.html',
  styleUrls: ['./report-template-update.component.scss'],
})
export class ReportTemplateUpdateComponent implements OnInit {
  isSaving = false;
  reportTemplate: IReportTemplate | null = null;
  public files: any[] = [];

  editForm: ReportTemplateFormGroup = this.reportTemplateFormService.createReportTemplateFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected reportTemplateService: ReportTemplateService,
    protected reportTemplateFormService: ReportTemplateFormService,
    protected activatedRoute: ActivatedRoute,
    private _snackBar: MatSnackBar,
    public dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ reportTemplate }) => {
      this.reportTemplate = reportTemplate;
      if (reportTemplate) {
        this.updateForm(reportTemplate);
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
    const reportTemplate = this.reportTemplateFormService.getReportTemplate(this.editForm);
    if (reportTemplate.id !== null) {
      this.subscribeToSaveResponse(this.reportTemplateService.update(reportTemplate));
    } else {
      this.subscribeToSaveResponse(this.reportTemplateService.create(reportTemplate));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReportTemplate>>): void {
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

  protected updateForm(reportTemplate: IReportTemplate): void {
    this.reportTemplate = reportTemplate;
    this.reportTemplateFormService.resetForm(this.editForm, reportTemplate);
  }

  onFileChange(event: any): void {
    this.setFileData(event, 'file', false);
  }

  // @ts-ignore
  openConfirmDialog(pIndex): void {
    const dialogRef = this.dialog.open(DialogConfirmComponent, {
      panelClass: 'modal-xs',
    });
    dialogRef.componentInstance.fName = this.files[pIndex].name;
    dialogRef.componentInstance.fIndex = pIndex;

    dialogRef.afterClosed().subscribe(result => {
      if (result !== undefined) {
        this.deleteFromArray(result);
      }
    });
  }

  // @ts-ignore
  deleteFromArray(index) {
    console.log(this.files);
    this.files.splice(index, 1);
  }
}

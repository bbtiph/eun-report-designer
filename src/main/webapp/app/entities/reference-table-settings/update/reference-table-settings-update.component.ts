import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ReferenceTableSettingsFormService, ReferenceTableSettingsFormGroup } from './reference-table-settings-form.service';
import { IReferenceTableSettings } from '../reference-table-settings.model';
import { ReferenceTableSettingsService } from '../service/reference-table-settings.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatDialog } from '@angular/material/dialog';
import { DialogConfirmComponent } from '../../../shared/dialog-confirm/dialog-confirm.component';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'jhi-reference-table-settings-update',
  templateUrl: './reference-table-settings-update.component.html',
  styleUrls: ['./reference-table-settings-update.component.scss'],
})
export class ReferenceTableSettingsUpdateComponent implements OnInit {
  isSaving = false;
  referenceTableSettings: IReferenceTableSettings | null = null;

  public files: any[] = [];

  editForm: ReferenceTableSettingsFormGroup = this.referenceTableSettingsFormService.createReferenceTableSettingsFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected referenceTableSettingsService: ReferenceTableSettingsService,
    protected referenceTableSettingsFormService: ReferenceTableSettingsFormService,
    protected activatedRoute: ActivatedRoute,
    private _snackBar: MatSnackBar,
    public dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ referenceTableSettings }) => {
      this.referenceTableSettings = referenceTableSettings;
      if (referenceTableSettings) {
        this.updateForm(referenceTableSettings);
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
    const referenceTableSettings = this.referenceTableSettingsFormService.getReferenceTableSettings(this.editForm);
    console.log('res: ', referenceTableSettings);
    if (referenceTableSettings.id !== null) {
      this.subscribeToSaveResponse(this.referenceTableSettingsService.update(referenceTableSettings));
    } else {
      this.subscribeToSaveResponse(this.referenceTableSettingsService.create(referenceTableSettings));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReferenceTableSettings>>): void {
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

  protected updateForm(referenceTableSettings: IReferenceTableSettings): void {
    this.referenceTableSettings = referenceTableSettings;
    this.referenceTableSettingsFormService.resetForm(this.editForm, referenceTableSettings);
  }

  onFileChange(event: any): void {
    this.files = event.target.files ?? Object.keys(event.target.files).map(key => event.target.files[key]);
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
    this.files.splice(index, 1);
  }

  updateColumns(updatedColumns: any) {
    this.editForm.setControl('columns', new FormControl(updatedColumns));
  }
}

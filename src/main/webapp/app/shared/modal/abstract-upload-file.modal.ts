import { Component, Input, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { CountryService } from '../../entities/country/service/country.service';
import { ICountry } from '../../entities/country/country.model';
import { Observable } from 'rxjs';
import { DialogConfirmComponent } from '../dialog-confirm/dialog-confirm.component';
import { DataUtils, FileLoadError } from '../../core/util/data-util.service';
import { EventManager, EventWithContent } from '../../core/util/event-manager.service';
import { AlertError } from '../alert/alert-error.model';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-abstract-export-modal',
  styleUrls: ['./abstract.modal.scss'],
  template: ` <div class="modal-header">
      <h4 class="modal-title text-center" id="modal-title">Upload File</h4>
      <button type="button" class="close" aria-describedby="modal-title" (click)="modal.dismiss()">
        <span aria-hidden="true">&times;</span>
      </button>
    </div>
    <div class="modal-body">
      <div class="row">
        <div class="col-md-12">
          <div style="padding: 15px">
            <div>
              <div class="dropzone" fileDragDrop (filesChangeEmiter)="onFileChange($event)">
                <div class="text-wrapper">
                  <div class="centered">
                    <input type="file" name="file" id="file" (change)="onFileChange($event)" />
                    <label for="file"><span class="textLink">Select your file</span> or Drop it here!</label>
                  </div>
                </div>
              </div>
            </div>
            <hr />
            <h3 mat-subheader>Files</h3>
            <div class="row fileItem" *ngFor="let f of files; let ind = index">
              <div class="col-sm-12 fileItemIconDiv" (click)="openConfirmDialog(ind)">
                <i class="fa-6x fileItemIcon"></i>
              </div>
              <div class="col-sm-12 fileItemText">
                <span>{{ f.name }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="modal-footer">
      <button type="button" class="btn btn-outline-secondary" (click)="modal.dismiss()">Close</button>
      <button type="button" class="btn btn-success" [disabled]="!(files.length > 0)" (click)="modal.close({ files: files })">Upload</button>
    </div>`,
})
export class AbstractUploadFileModal implements OnInit {
  public files: any[] = [];

  constructor(
    public modal: NgbActiveModal,
    public dialog: MatDialog,
    protected dataUtils: DataUtils,
    protected eventManager: EventManager
  ) {}

  ngOnInit(): void {}

  onFileChange(event: any): void {
    this.files = event.target.files ?? Object.keys(event.target.files).map(key => event.target.files[key]);
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
}

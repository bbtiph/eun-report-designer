import { Component, Input, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { CountryService } from '../../entities/country/service/country.service';
import { ICountry } from '../../entities/country/country.model';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-abstract-export-modal',
  template: ` <div class="modal-header">
      <h4 class="modal-title text-center" id="modal-title">Export</h4>
      <button type="button" class="close" aria-describedby="modal-title" (click)="modal.dismiss()">
        <span aria-hidden="true">&times;</span>
      </button>
    </div>
    <div class="modal-body">
      <div class="row">
        <div class="col-md-12">
          <div class="form-group">
            <label class="col-form-label">Name:</label>
            <input type="text" class="form-control" [disabled]="true" [value]="reportName" />
          </div>
          <div class="form-group">
            <label class="col-form-label">Format:</label>
            <select class="form-control" [(ngModel)]="format" name="reportFormat" id="field_status" data-cy="status">
              <option [ngValue]="null"></option>
              <option *ngFor="let reportFormat of reportFormats" [ngValue]="reportFormat">{{ reportFormat.fullname }}</option>
            </select>
          </div>
          <div class="form-group">
            <label class="col-form-label">Country: {{ countryName }}</label>
          </div>
        </div>
      </div>
    </div>
    <div class="modal-footer">
      <button type="button" class="btn btn-outline-secondary" (click)="modal.dismiss()">Close</button>
      <button type="button" class="btn btn-success" (click)="modal.close({ lang: lang, format: format })">Download</button>
    </div>`,
})
export class AbstractExportModal implements OnInit {
  @Input() public reportName?: string;
  @Input() public countryName?: string;
  public lang = 'ru';
  // @ts-ignore
  public countries: ICountry[];
  // @ts-ignore
  public country: ICountry;
  public format = { id: 1, name: 'PDF', fullName: 'Acrobat Reader' };

  constructor(public modal: NgbActiveModal, public countryService: CountryService) {}

  reportFormats = [
    { id: 1, name: 'PDF', fullname: 'PDF(Acrobat Reader)' },
    // { id: 2, name: 'DOCX', fullname: 'MS Word DOCX' },
    // { id: 3, name: 'XLSX', fullname: 'MS Excel' },
    { id: 4, name: 'DOC', fullname: 'DOC(MS Word DOC)' },
    { id: 5, name: 'HTML', fullname: 'HTML(HyperText Markup Language)' },
  ];

  ngOnInit(): void {
    this.countryService.findAll().subscribe((countries: ICountry[]) => {
      this.countries = countries;
    });
  }
}

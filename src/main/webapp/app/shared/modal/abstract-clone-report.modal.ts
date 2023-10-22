import { Component, Input, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { CountryService } from '../../entities/country/service/country.service';
import { ICountry } from '../../entities/country/country.model';
import { Observable } from 'rxjs';
import { IReport } from '../../entities/report/report.model';
import { ReportFormGroup, ReportFormService } from '../../entities/report/update/report-form.service';
import { IReportTemplate } from '../../entities/report-template/report-template.model';
import { finalize, map } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';
import { ReportService } from '../../entities/report/service/report.service';
import { ReportTemplateService } from '../../entities/report-template/service/report-template.service';
import { ITEM_CLONED, ITEM_DELETED_EVENT } from '../../config/navigation.constants';

@Component({
  selector: 'app-abstract-clone-export-modal',
  template: ` <div class="modal-header">
      <h4 class="modal-title text-center" id="modal-title">
        Clone from '<b>{{ report?.reportName }}</b
        >'
      </h4>
      <button type="button" class="close" aria-describedby="modal-title" (click)="modal.dismiss()">
        <span aria-hidden="true">&times;</span>
      </button>
    </div>
    <div class="modal-body">
      <div class="row">
        <div class="col-md-12">
          <div class="d-flex justify-content-center">
            <div class="col-8">
              <form name="editForm" role="form" novalidate [formGroup]="editForm">
                <div>
                  <!--        <jhi-alert-error></jhi-alert-error>-->

                  <!--        <div class="row mb-3" *ngIf="editForm.controls.id.value !== null">-->
                  <!--          <label class="form-label" for="field_id">ID</label>-->
                  <!--          <input type="number" class="form-control" name="id" id="field_id" data-cy="id" formControlName="id" [readonly]="true" />-->
                  <!--        </div>-->

                  <div class="row mb-3">
                    <label class="form-label" for="field_reportName">Report Name</label>
                    <input
                      type="text"
                      class="form-control"
                      name="reportName"
                      id="field_reportName"
                      data-cy="reportName"
                      formControlName="reportName"
                    />
                    <div
                      *ngIf="
                        editForm.get('reportName')!.invalid && (editForm.get('reportName')!.dirty || editForm.get('reportName')!.touched)
                      "
                    >
                      <small class="form-text text-danger" *ngIf="editForm.get('reportName')?.errors?.required">
                        This field is required.
                      </small>
                    </div>
                  </div>

                  <!--                  <div class="row mb-3">-->
                  <!--                    <label class="form-label" for="field_acronym">Acronym</label>-->
                  <!--                    <input-->
                  <!--                      type="text"-->
                  <!--                      class="form-control"-->
                  <!--                      name="acronym"-->
                  <!--                      id="field_acronym"-->
                  <!--                      data-cy="acronym"-->
                  <!--                      formControlName="acronym"-->
                  <!--                      [readonly]="true"-->
                  <!--                      [class.disabled]="true"-->
                  <!--                    />-->
                  <!--                  </div>-->

                  <!--                  <div class="row mb-3">-->
                  <!--                    <label class="form-label" for="field_type">Type</label>-->
                  <!--                    <input-->
                  <!--                      type="text"-->
                  <!--                      class="form-control"-->
                  <!--                      name="type"-->
                  <!--                      id="field_type"-->
                  <!--                      data-cy="type"-->
                  <!--                      formControlName="type"-->
                  <!--                      [readonly]="true"-->
                  <!--                      [class.disabled]="true"-->
                  <!--                    />-->
                  <!--                  </div>-->

                  <div class="row mb-3">
                    <label class="form-label" for="field_description">Description</label>
                    <input
                      type="text"
                      class="form-control"
                      name="description"
                      id="field_description"
                      data-cy="description"
                      formControlName="description"
                    />
                  </div>

                  <div class="row mb-3">
                    <label class="form-label" for="field_reportTemplate">Template of BIRT report designer</label>
                    <select
                      class="form-control"
                      id="field_reportTemplate"
                      data-cy="reportTemplate"
                      name="reportTemplate"
                      formControlName="reportTemplate"
                      [compareWith]="compareReportTemplate"
                      [class.disabled]="true"
                    >
                      <option [ngValue]="null" disabled></option>
                      <option
                        [ngValue]="reportTemplateOption"
                        *ngFor="let reportTemplateOption of reportTemplatesSharedCollection"
                        disabled
                      >
                        {{ reportTemplateOption.name }} ({{ reportTemplateOption.description }})
                      </option>
                    </select>
                  </div>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="modal-footer">
      <button
        type="submit"
        id="save-entity"
        data-cy="entityCreateSaveButton"
        [disabled]="editForm.invalid"
        (click)="clone()"
        class="btn btn-success"
      >
        <fa-icon icon="save"></fa-icon>&nbsp;<span>Clone</span>
      </button>
      <button type="button" class="btn btn-outline-secondary" (click)="modal.dismiss()">Close</button>
    </div>`,
  styleUrls: ['./abstract.modal.scss'],
})
export class AbstractCloneReportModal implements OnInit {
  report: IReport | null = null;
  @Input() public reportToClone: IReport | null = null;

  editForm: ReportFormGroup = this.reportFormService.createReportFormGroup();
  reportTemplatesSharedCollection: IReportTemplate[] = [];

  compareReportTemplate = (o1: IReportTemplate | null, o2: IReportTemplate | null): boolean =>
    this.reportTemplateService.compareReportTemplate(o1, o2);

  constructor(
    public modal: NgbActiveModal,
    protected reportFormService: ReportFormService,
    protected reportService: ReportService,
    protected reportTemplateService: ReportTemplateService
  ) {}

  ngOnInit(): void {
    this.report = this.reportToClone;
    if (this.reportToClone) {
      this.updateForm(this.reportToClone);
    }
    this.loadRelationshipsOptions();
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

  clone(): void {
    const report = this.reportFormService.getReport(this.editForm);
    // @ts-ignore
    this.subscribeToSaveResponse(this.reportService.clone(report));
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReport>>): void {
    result.subscribe(response => {
      // @ts-ignore
      this.reportService.cloneReportBlocks(response.body).subscribe(() => {
        this.modal.close(ITEM_CLONED);
      });
    });
  }

  protected onSaveSuccess(): void {}

  protected onSaveError(): void {}

  protected onSaveFinalize(): void {}
}

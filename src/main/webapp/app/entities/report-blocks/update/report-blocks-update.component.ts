import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ReportBlocksFormService, ReportBlocksFormGroup } from './report-blocks-form.service';
import { IReportBlocks } from '../report-blocks.model';
import { ReportBlocksService } from '../service/report-blocks.service';
import { ICountries } from 'app/entities/countries/countries.model';
import { CountriesService } from 'app/entities/countries/service/countries.service';
import { IReport } from 'app/entities/report/report.model';
import { ReportService } from 'app/entities/report/service/report.service';

@Component({
  selector: 'jhi-report-blocks-update',
  templateUrl: './report-blocks-update.component.html',
})
export class ReportBlocksUpdateComponent implements OnInit {
  isSaving = false;
  reportBlocks: IReportBlocks | null = null;

  countriesSharedCollection: ICountries[] = [];
  reportsSharedCollection: IReport[] = [];

  editForm: ReportBlocksFormGroup = this.reportBlocksFormService.createReportBlocksFormGroup();

  constructor(
    protected reportBlocksService: ReportBlocksService,
    protected reportBlocksFormService: ReportBlocksFormService,
    protected countriesService: CountriesService,
    protected reportService: ReportService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareCountries = (o1: ICountries | null, o2: ICountries | null): boolean => this.countriesService.compareCountries(o1, o2);

  compareReport = (o1: IReport | null, o2: IReport | null): boolean => this.reportService.compareReport(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ reportBlocks }) => {
      this.reportBlocks = reportBlocks;
      if (reportBlocks) {
        this.updateForm(reportBlocks);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const reportBlocks = this.reportBlocksFormService.getReportBlocks(this.editForm);
    if (reportBlocks.id !== null) {
      this.subscribeToSaveResponse(this.reportBlocksService.update(reportBlocks));
    } else {
      this.subscribeToSaveResponse(this.reportBlocksService.create(reportBlocks));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReportBlocks>>): void {
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

  protected updateForm(reportBlocks: IReportBlocks): void {
    this.reportBlocks = reportBlocks;
    this.reportBlocksFormService.resetForm(this.editForm, reportBlocks);

    this.countriesSharedCollection = this.countriesService.addCountriesToCollectionIfMissing<ICountries>(
      this.countriesSharedCollection,
      ...(reportBlocks.countryIds ?? [])
    );
    this.reportsSharedCollection = this.reportService.addReportToCollectionIfMissing<IReport>(
      this.reportsSharedCollection,
      reportBlocks.report
    );
  }

  protected loadRelationshipsOptions(): void {
    this.countriesService
      .query()
      .pipe(map((res: HttpResponse<ICountries[]>) => res.body ?? []))
      .pipe(
        map((countries: ICountries[]) =>
          this.countriesService.addCountriesToCollectionIfMissing<ICountries>(countries, ...(this.reportBlocks?.countryIds ?? []))
        )
      )
      .subscribe((countries: ICountries[]) => (this.countriesSharedCollection = countries));

    this.reportService
      .query()
      .pipe(map((res: HttpResponse<IReport[]>) => res.body ?? []))
      .pipe(map((reports: IReport[]) => this.reportService.addReportToCollectionIfMissing<IReport>(reports, this.reportBlocks?.report)))
      .subscribe((reports: IReport[]) => (this.reportsSharedCollection = reports));
  }
}

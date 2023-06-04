import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ReportBlocksContentDataFormService, ReportBlocksContentDataFormGroup } from './report-blocks-content-data-form.service';
import { IReportBlocksContentData } from '../report-blocks-content-data.model';
import { ReportBlocksContentDataService } from '../service/report-blocks-content-data.service';
import { IReportBlocksContent } from 'app/entities/report-blocks-content/report-blocks-content.model';
import { ReportBlocksContentService } from 'app/entities/report-blocks-content/service/report-blocks-content.service';
import { ICountries } from 'app/entities/countries/countries.model';
import { CountriesService } from 'app/entities/countries/service/countries.service';

@Component({
  selector: 'jhi-report-blocks-content-data-update',
  templateUrl: './report-blocks-content-data-update.component.html',
})
export class ReportBlocksContentDataUpdateComponent implements OnInit {
  isSaving = false;
  reportBlocksContentData: IReportBlocksContentData | null = null;

  reportBlocksContentsSharedCollection: IReportBlocksContent[] = [];
  countriesSharedCollection: ICountries[] = [];

  editForm: ReportBlocksContentDataFormGroup = this.reportBlocksContentDataFormService.createReportBlocksContentDataFormGroup();

  constructor(
    protected reportBlocksContentDataService: ReportBlocksContentDataService,
    protected reportBlocksContentDataFormService: ReportBlocksContentDataFormService,
    protected reportBlocksContentService: ReportBlocksContentService,
    protected countriesService: CountriesService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareReportBlocksContent = (o1: IReportBlocksContent | null, o2: IReportBlocksContent | null): boolean =>
    this.reportBlocksContentService.compareReportBlocksContent(o1, o2);

  compareCountries = (o1: ICountries | null, o2: ICountries | null): boolean => this.countriesService.compareCountries(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ reportBlocksContentData }) => {
      this.reportBlocksContentData = reportBlocksContentData;
      if (reportBlocksContentData) {
        this.updateForm(reportBlocksContentData);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const reportBlocksContentData = this.reportBlocksContentDataFormService.getReportBlocksContentData(this.editForm);
    if (reportBlocksContentData.id !== null) {
      this.subscribeToSaveResponse(this.reportBlocksContentDataService.update(reportBlocksContentData));
    } else {
      this.subscribeToSaveResponse(this.reportBlocksContentDataService.create(reportBlocksContentData));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReportBlocksContentData>>): void {
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

  protected updateForm(reportBlocksContentData: IReportBlocksContentData): void {
    this.reportBlocksContentData = reportBlocksContentData;
    this.reportBlocksContentDataFormService.resetForm(this.editForm, reportBlocksContentData);

    this.reportBlocksContentsSharedCollection =
      this.reportBlocksContentService.addReportBlocksContentToCollectionIfMissing<IReportBlocksContent>(
        this.reportBlocksContentsSharedCollection,
        reportBlocksContentData.reportBlocksContent
      );
    this.countriesSharedCollection = this.countriesService.addCountriesToCollectionIfMissing<ICountries>(
      this.countriesSharedCollection,
      reportBlocksContentData.country
    );
  }

  protected loadRelationshipsOptions(): void {
    this.reportBlocksContentService
      .query()
      .pipe(map((res: HttpResponse<IReportBlocksContent[]>) => res.body ?? []))
      .pipe(
        map((reportBlocksContents: IReportBlocksContent[]) =>
          this.reportBlocksContentService.addReportBlocksContentToCollectionIfMissing<IReportBlocksContent>(
            reportBlocksContents,
            this.reportBlocksContentData?.reportBlocksContent
          )
        )
      )
      .subscribe((reportBlocksContents: IReportBlocksContent[]) => (this.reportBlocksContentsSharedCollection = reportBlocksContents));

    this.countriesService
      .query()
      .pipe(map((res: HttpResponse<ICountries[]>) => res.body ?? []))
      .pipe(
        map((countries: ICountries[]) =>
          this.countriesService.addCountriesToCollectionIfMissing<ICountries>(countries, this.reportBlocksContentData?.country)
        )
      )
      .subscribe((countries: ICountries[]) => (this.countriesSharedCollection = countries));
  }
}

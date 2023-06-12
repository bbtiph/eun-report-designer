import { Component, OnInit } from '@angular/core';
import { IReportBlocks } from '../report-blocks.model';
import { ICountries } from '../../countries/countries.model';
import { ReportBlocksFormGroup, ReportBlocksFormService } from '../update/report-blocks-form.service';
import { ReportBlocksService } from '../service/report-blocks.service';
import { ReportBlocksContentService } from '../../report-blocks-content/service/report-blocks-content.service';
import { CountriesService } from '../../countries/service/countries.service';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { HttpResponse } from '@angular/common/http';
import { finalize, map } from 'rxjs/operators';
import { IReport } from '../../report/report.model';

@Component({
  selector: 'jhi-report-blocks-country-content',
  templateUrl: './report-blocks-country-content.component.html',
  styleUrls: ['./report-blocks-country-content.component.scss'],
})
export class ReportBlocksCountryContentComponent implements OnInit {
  isSaving = false;
  type: string = '';
  reportBlocks: IReportBlocks | null = null;
  selectedCountryId: number | null = null;

  countriesSharedCollection: ICountries[] = [];
  form: ReportBlocksFormGroup = this.reportBlocksFormService.createReportBlocksFormGroup();

  constructor(
    protected reportBlocksService: ReportBlocksService,
    protected reportBlocksContentService: ReportBlocksContentService,
    protected reportBlocksFormService: ReportBlocksFormService,
    protected countriesService: CountriesService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    // @ts-ignore
    this.type = this.activatedRoute.data.value.type;
    this.activatedRoute.data.subscribe(({ reportBlocks }) => {
      this.reportBlocks = reportBlocks;
      if (reportBlocks) {
        this.updateForm(reportBlocks);
      }
    });
  }

  protected updateForm(reportBlocks: IReportBlocks): void {
    this.reportBlocks = reportBlocks;
    this.reportBlocksFormService.resetForm(this.form, reportBlocks);

    this.countriesSharedCollection = this.countriesService.addCountriesToCollectionIfMissing<ICountries>(
      this.countriesSharedCollection,
      ...(reportBlocks.countryIds ?? [])
    );
  }

  previousState(): void {
    window.history.back();
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
}

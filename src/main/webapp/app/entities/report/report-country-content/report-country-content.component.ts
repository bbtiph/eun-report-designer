import { Component, OnInit } from '@angular/core';
import { ICountries } from '../../countries/countries.model';
import { ActivatedRoute } from '@angular/router';
import { ICountry } from '../../country/country.model';
import { CountryService } from '../../country/service/country.service';
import { Period, PeriodService } from '../../../shared/data/period.service';

@Component({
  selector: 'jhi-report-country-content',
  templateUrl: './report-country-content.component.html',
})
export class ReportCountryContentComponent implements OnInit {
  isSaving = false;
  public country: ICountry | undefined;
  public period: Period | undefined;
  reportId: number | undefined;
  selectedCountryId: number | null = null;
  selectedPeriodId: number | null = null;

  countriesSharedCollection: ICountries[] = [];
  periodSharedCollection: Period[] = [];

  constructor(
    protected countriesService: CountryService,
    protected activatedRoute: ActivatedRoute,
    protected periodService: PeriodService
  ) {}

  ngOnInit(): void {
    this.periodSharedCollection = this.periodService.getPeriods();
    // @ts-ignore
    this.reportId = this.activatedRoute.params.value.id;
    this.countriesService.findAllByReport(this.reportId ?? 0).subscribe((countries: ICountry[]) => {
      this.countriesSharedCollection = countries;
    });
  }
}

import { Component, OnInit } from '@angular/core';
import { ICountries } from '../../countries/countries.model';
import { ActivatedRoute } from '@angular/router';
import { ICountry } from '../../country/country.model';
import { CountryService } from '../../country/service/country.service';

@Component({
  selector: 'jhi-report-country-content',
  templateUrl: './report-country-content.component.html',
})
export class ReportCountryContentComponent implements OnInit {
  isSaving = false;
  public country: ICountry | undefined;
  reportId: number | undefined;

  countriesSharedCollection: ICountries[] = [];

  constructor(protected countriesService: CountryService, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    // @ts-ignore
    this.reportId = this.activatedRoute.params.value.id;
    console.log('rrrr>', this.activatedRoute);
    console.log('rrrr>', this.reportId);
    this.countriesService.findAll().subscribe((countries: ICountry[]) => {
      this.countriesSharedCollection = countries;
    });
  }
}

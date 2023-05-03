import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { MinistryFormService, MinistryFormGroup } from './ministry-form.service';
import { IMinistry } from '../ministry.model';
import { MinistryService } from '../service/ministry.service';
import { ICountries } from 'app/entities/countries/countries.model';
import { CountriesService } from 'app/entities/countries/service/countries.service';

@Component({
  selector: 'jhi-ministry-update',
  templateUrl: './ministry-update.component.html',
})
export class MinistryUpdateComponent implements OnInit {
  isSaving = false;
  ministry: IMinistry | null = null;

  countriesSharedCollection: ICountries[] = [];

  editForm: MinistryFormGroup = this.ministryFormService.createMinistryFormGroup();

  constructor(
    protected ministryService: MinistryService,
    protected ministryFormService: MinistryFormService,
    protected countriesService: CountriesService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareCountries = (o1: ICountries | null, o2: ICountries | null): boolean => this.countriesService.compareCountries(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ministry }) => {
      this.ministry = ministry;
      if (ministry) {
        this.updateForm(ministry);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ministry = this.ministryFormService.getMinistry(this.editForm);
    if (ministry.id !== null) {
      this.subscribeToSaveResponse(this.ministryService.update(ministry));
    } else {
      this.subscribeToSaveResponse(this.ministryService.create(ministry));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMinistry>>): void {
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

  protected updateForm(ministry: IMinistry): void {
    this.ministry = ministry;
    this.ministryFormService.resetForm(this.editForm, ministry);

    this.countriesSharedCollection = this.countriesService.addCountriesToCollectionIfMissing<ICountries>(
      this.countriesSharedCollection,
      ministry.country
    );
  }

  protected loadRelationshipsOptions(): void {
    this.countriesService
      .query()
      .pipe(map((res: HttpResponse<ICountries[]>) => res.body ?? []))
      .pipe(
        map((countries: ICountries[]) =>
          this.countriesService.addCountriesToCollectionIfMissing<ICountries>(countries, this.ministry?.country)
        )
      )
      .subscribe((countries: ICountries[]) => (this.countriesSharedCollection = countries));
  }
}

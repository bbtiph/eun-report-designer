import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { MOEParticipationReferencesFormService, MOEParticipationReferencesFormGroup } from './moe-participation-references-form.service';
import { IMOEParticipationReferences } from '../moe-participation-references.model';
import { MOEParticipationReferencesService } from '../service/moe-participation-references.service';
import { ICountries } from 'app/entities/countries/countries.model';
import { CountriesService } from 'app/entities/countries/service/countries.service';

@Component({
  selector: 'jhi-moe-participation-references-update',
  templateUrl: './moe-participation-references-update.component.html',
})
export class MOEParticipationReferencesUpdateComponent implements OnInit {
  isSaving = false;
  mOEParticipationReferences: IMOEParticipationReferences | null = null;

  countriesSharedCollection: ICountries[] = [];

  editForm: MOEParticipationReferencesFormGroup = this.mOEParticipationReferencesFormService.createMOEParticipationReferencesFormGroup();

  constructor(
    protected mOEParticipationReferencesService: MOEParticipationReferencesService,
    protected mOEParticipationReferencesFormService: MOEParticipationReferencesFormService,
    protected countriesService: CountriesService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareCountries = (o1: ICountries | null, o2: ICountries | null): boolean => this.countriesService.compareCountries(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mOEParticipationReferences }) => {
      this.mOEParticipationReferences = mOEParticipationReferences;
      if (mOEParticipationReferences) {
        this.updateForm(mOEParticipationReferences);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const mOEParticipationReferences = this.mOEParticipationReferencesFormService.getMOEParticipationReferences(this.editForm);
    if (mOEParticipationReferences.id !== null) {
      this.subscribeToSaveResponse(this.mOEParticipationReferencesService.update(mOEParticipationReferences));
    } else {
      this.subscribeToSaveResponse(this.mOEParticipationReferencesService.create(mOEParticipationReferences));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMOEParticipationReferences>>): void {
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

  protected updateForm(mOEParticipationReferences: IMOEParticipationReferences): void {
    this.mOEParticipationReferences = mOEParticipationReferences;
    this.mOEParticipationReferencesFormService.resetForm(this.editForm, mOEParticipationReferences);

    this.countriesSharedCollection = this.countriesService.addCountriesToCollectionIfMissing<ICountries>(
      this.countriesSharedCollection,
      ...(mOEParticipationReferences.countries ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.countriesService
      .query()
      .pipe(map((res: HttpResponse<ICountries[]>) => res.body ?? []))
      .pipe(
        map((countries: ICountries[]) =>
          this.countriesService.addCountriesToCollectionIfMissing<ICountries>(
            countries,
            ...(this.mOEParticipationReferences?.countries ?? [])
          )
        )
      )
      .subscribe((countries: ICountries[]) => (this.countriesSharedCollection = countries));
  }
}

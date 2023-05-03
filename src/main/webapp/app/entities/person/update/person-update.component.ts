import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { PersonFormService, PersonFormGroup } from './person-form.service';
import { IPerson } from '../person.model';
import { PersonService } from '../service/person.service';
import { ICountries } from 'app/entities/countries/countries.model';
import { CountriesService } from 'app/entities/countries/service/countries.service';
import { GdprStatus } from 'app/entities/enumerations/gdpr-status.model';

@Component({
  selector: 'jhi-person-update',
  templateUrl: './person-update.component.html',
})
export class PersonUpdateComponent implements OnInit {
  isSaving = false;
  person: IPerson | null = null;
  gdprStatusValues = Object.keys(GdprStatus);

  countriesSharedCollection: ICountries[] = [];

  editForm: PersonFormGroup = this.personFormService.createPersonFormGroup();

  constructor(
    protected personService: PersonService,
    protected personFormService: PersonFormService,
    protected countriesService: CountriesService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareCountries = (o1: ICountries | null, o2: ICountries | null): boolean => this.countriesService.compareCountries(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ person }) => {
      this.person = person;
      if (person) {
        this.updateForm(person);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const person = this.personFormService.getPerson(this.editForm);
    if (person.id !== null) {
      this.subscribeToSaveResponse(this.personService.update(person));
    } else {
      this.subscribeToSaveResponse(this.personService.create(person));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPerson>>): void {
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

  protected updateForm(person: IPerson): void {
    this.person = person;
    this.personFormService.resetForm(this.editForm, person);

    this.countriesSharedCollection = this.countriesService.addCountriesToCollectionIfMissing<ICountries>(
      this.countriesSharedCollection,
      person.country
    );
  }

  protected loadRelationshipsOptions(): void {
    this.countriesService
      .query()
      .pipe(map((res: HttpResponse<ICountries[]>) => res.body ?? []))
      .pipe(
        map((countries: ICountries[]) =>
          this.countriesService.addCountriesToCollectionIfMissing<ICountries>(countries, this.person?.country)
        )
      )
      .subscribe((countries: ICountries[]) => (this.countriesSharedCollection = countries));
  }
}

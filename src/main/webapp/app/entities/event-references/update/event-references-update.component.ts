import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { EventReferencesFormService, EventReferencesFormGroup } from './event-references-form.service';
import { IEventReferences } from '../event-references.model';
import { EventReferencesService } from '../service/event-references.service';
import { ICountries } from 'app/entities/countries/countries.model';
import { CountriesService } from 'app/entities/countries/service/countries.service';

@Component({
  selector: 'jhi-event-references-update',
  templateUrl: './event-references-update.component.html',
})
export class EventReferencesUpdateComponent implements OnInit {
  isSaving = false;
  eventReferences: IEventReferences | null = null;

  countriesSharedCollection: ICountries[] = [];

  editForm: EventReferencesFormGroup = this.eventReferencesFormService.createEventReferencesFormGroup();

  constructor(
    protected eventReferencesService: EventReferencesService,
    protected eventReferencesFormService: EventReferencesFormService,
    protected countriesService: CountriesService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareCountries = (o1: ICountries | null, o2: ICountries | null): boolean => this.countriesService.compareCountries(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ eventReferences }) => {
      this.eventReferences = eventReferences;
      if (eventReferences) {
        this.updateForm(eventReferences);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const eventReferences = this.eventReferencesFormService.getEventReferences(this.editForm);
    if (eventReferences.id !== null) {
      this.subscribeToSaveResponse(this.eventReferencesService.update(eventReferences));
    } else {
      this.subscribeToSaveResponse(this.eventReferencesService.create(eventReferences));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEventReferences>>): void {
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

  protected updateForm(eventReferences: IEventReferences): void {
    this.eventReferences = eventReferences;
    this.eventReferencesFormService.resetForm(this.editForm, eventReferences);

    this.countriesSharedCollection = this.countriesService.addCountriesToCollectionIfMissing<ICountries>(
      this.countriesSharedCollection,
      ...(eventReferences.countries ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.countriesService
      .query()
      .pipe(map((res: HttpResponse<ICountries[]>) => res.body ?? []))
      .pipe(
        map((countries: ICountries[]) =>
          this.countriesService.addCountriesToCollectionIfMissing<ICountries>(countries, ...(this.eventReferences?.countries ?? []))
        )
      )
      .subscribe((countries: ICountries[]) => (this.countriesSharedCollection = countries));
  }
}

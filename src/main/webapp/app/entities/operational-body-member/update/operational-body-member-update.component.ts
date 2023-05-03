import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { OperationalBodyMemberFormService, OperationalBodyMemberFormGroup } from './operational-body-member-form.service';
import { IOperationalBodyMember } from '../operational-body-member.model';
import { OperationalBodyMemberService } from '../service/operational-body-member.service';
import { ICountries } from 'app/entities/countries/countries.model';
import { CountriesService } from 'app/entities/countries/service/countries.service';

@Component({
  selector: 'jhi-operational-body-member-update',
  templateUrl: './operational-body-member-update.component.html',
})
export class OperationalBodyMemberUpdateComponent implements OnInit {
  isSaving = false;
  operationalBodyMember: IOperationalBodyMember | null = null;

  countriesSharedCollection: ICountries[] = [];

  editForm: OperationalBodyMemberFormGroup = this.operationalBodyMemberFormService.createOperationalBodyMemberFormGroup();

  constructor(
    protected operationalBodyMemberService: OperationalBodyMemberService,
    protected operationalBodyMemberFormService: OperationalBodyMemberFormService,
    protected countriesService: CountriesService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareCountries = (o1: ICountries | null, o2: ICountries | null): boolean => this.countriesService.compareCountries(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ operationalBodyMember }) => {
      this.operationalBodyMember = operationalBodyMember;
      if (operationalBodyMember) {
        this.updateForm(operationalBodyMember);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const operationalBodyMember = this.operationalBodyMemberFormService.getOperationalBodyMember(this.editForm);
    if (operationalBodyMember.id !== null) {
      this.subscribeToSaveResponse(this.operationalBodyMemberService.update(operationalBodyMember));
    } else {
      this.subscribeToSaveResponse(this.operationalBodyMemberService.create(operationalBodyMember));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOperationalBodyMember>>): void {
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

  protected updateForm(operationalBodyMember: IOperationalBodyMember): void {
    this.operationalBodyMember = operationalBodyMember;
    this.operationalBodyMemberFormService.resetForm(this.editForm, operationalBodyMember);

    this.countriesSharedCollection = this.countriesService.addCountriesToCollectionIfMissing<ICountries>(
      this.countriesSharedCollection,
      operationalBodyMember.country
    );
  }

  protected loadRelationshipsOptions(): void {
    this.countriesService
      .query()
      .pipe(map((res: HttpResponse<ICountries[]>) => res.body ?? []))
      .pipe(
        map((countries: ICountries[]) =>
          this.countriesService.addCountriesToCollectionIfMissing<ICountries>(countries, this.operationalBodyMember?.country)
        )
      )
      .subscribe((countries: ICountries[]) => (this.countriesSharedCollection = countries));
  }
}

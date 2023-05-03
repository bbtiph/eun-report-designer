import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { PersonInOrganizationFormService, PersonInOrganizationFormGroup } from './person-in-organization-form.service';
import { IPersonInOrganization } from '../person-in-organization.model';
import { PersonInOrganizationService } from '../service/person-in-organization.service';

@Component({
  selector: 'jhi-person-in-organization-update',
  templateUrl: './person-in-organization-update.component.html',
})
export class PersonInOrganizationUpdateComponent implements OnInit {
  isSaving = false;
  personInOrganization: IPersonInOrganization | null = null;

  editForm: PersonInOrganizationFormGroup = this.personInOrganizationFormService.createPersonInOrganizationFormGroup();

  constructor(
    protected personInOrganizationService: PersonInOrganizationService,
    protected personInOrganizationFormService: PersonInOrganizationFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ personInOrganization }) => {
      this.personInOrganization = personInOrganization;
      if (personInOrganization) {
        this.updateForm(personInOrganization);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const personInOrganization = this.personInOrganizationFormService.getPersonInOrganization(this.editForm);
    if (personInOrganization.id !== null) {
      this.subscribeToSaveResponse(this.personInOrganizationService.update(personInOrganization));
    } else {
      this.subscribeToSaveResponse(this.personInOrganizationService.create(personInOrganization));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPersonInOrganization>>): void {
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

  protected updateForm(personInOrganization: IPersonInOrganization): void {
    this.personInOrganization = personInOrganization;
    this.personInOrganizationFormService.resetForm(this.editForm, personInOrganization);
  }
}

import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { EventInOrganizationFormService, EventInOrganizationFormGroup } from './event-in-organization-form.service';
import { IEventInOrganization } from '../event-in-organization.model';
import { EventInOrganizationService } from '../service/event-in-organization.service';

@Component({
  selector: 'jhi-event-in-organization-update',
  templateUrl: './event-in-organization-update.component.html',
})
export class EventInOrganizationUpdateComponent implements OnInit {
  isSaving = false;
  eventInOrganization: IEventInOrganization | null = null;

  editForm: EventInOrganizationFormGroup = this.eventInOrganizationFormService.createEventInOrganizationFormGroup();

  constructor(
    protected eventInOrganizationService: EventInOrganizationService,
    protected eventInOrganizationFormService: EventInOrganizationFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ eventInOrganization }) => {
      this.eventInOrganization = eventInOrganization;
      if (eventInOrganization) {
        this.updateForm(eventInOrganization);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const eventInOrganization = this.eventInOrganizationFormService.getEventInOrganization(this.editForm);
    if (eventInOrganization.id !== null) {
      this.subscribeToSaveResponse(this.eventInOrganizationService.update(eventInOrganization));
    } else {
      this.subscribeToSaveResponse(this.eventInOrganizationService.create(eventInOrganization));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEventInOrganization>>): void {
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

  protected updateForm(eventInOrganization: IEventInOrganization): void {
    this.eventInOrganization = eventInOrganization;
    this.eventInOrganizationFormService.resetForm(this.editForm, eventInOrganization);
  }
}

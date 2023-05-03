import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { OrganizationFormService, OrganizationFormGroup } from './organization-form.service';
import { IOrganization } from '../organization.model';
import { OrganizationService } from '../service/organization.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IEventInOrganization } from 'app/entities/event-in-organization/event-in-organization.model';
import { EventInOrganizationService } from 'app/entities/event-in-organization/service/event-in-organization.service';
import { IOrganizationInMinistry } from 'app/entities/organization-in-ministry/organization-in-ministry.model';
import { OrganizationInMinistryService } from 'app/entities/organization-in-ministry/service/organization-in-ministry.service';
import { IOrganizationInProject } from 'app/entities/organization-in-project/organization-in-project.model';
import { OrganizationInProjectService } from 'app/entities/organization-in-project/service/organization-in-project.service';
import { IPersonInOrganization } from 'app/entities/person-in-organization/person-in-organization.model';
import { PersonInOrganizationService } from 'app/entities/person-in-organization/service/person-in-organization.service';
import { OrgStatus } from 'app/entities/enumerations/org-status.model';

@Component({
  selector: 'jhi-organization-update',
  templateUrl: './organization-update.component.html',
})
export class OrganizationUpdateComponent implements OnInit {
  isSaving = false;
  organization: IOrganization | null = null;
  orgStatusValues = Object.keys(OrgStatus);

  eventInOrganizationsSharedCollection: IEventInOrganization[] = [];
  organizationInMinistriesSharedCollection: IOrganizationInMinistry[] = [];
  organizationInProjectsSharedCollection: IOrganizationInProject[] = [];
  personInOrganizationsSharedCollection: IPersonInOrganization[] = [];

  editForm: OrganizationFormGroup = this.organizationFormService.createOrganizationFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected organizationService: OrganizationService,
    protected organizationFormService: OrganizationFormService,
    protected eventInOrganizationService: EventInOrganizationService,
    protected organizationInMinistryService: OrganizationInMinistryService,
    protected organizationInProjectService: OrganizationInProjectService,
    protected personInOrganizationService: PersonInOrganizationService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareEventInOrganization = (o1: IEventInOrganization | null, o2: IEventInOrganization | null): boolean =>
    this.eventInOrganizationService.compareEventInOrganization(o1, o2);

  compareOrganizationInMinistry = (o1: IOrganizationInMinistry | null, o2: IOrganizationInMinistry | null): boolean =>
    this.organizationInMinistryService.compareOrganizationInMinistry(o1, o2);

  compareOrganizationInProject = (o1: IOrganizationInProject | null, o2: IOrganizationInProject | null): boolean =>
    this.organizationInProjectService.compareOrganizationInProject(o1, o2);

  comparePersonInOrganization = (o1: IPersonInOrganization | null, o2: IPersonInOrganization | null): boolean =>
    this.personInOrganizationService.comparePersonInOrganization(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ organization }) => {
      this.organization = organization;
      if (organization) {
        this.updateForm(organization);
      }

      this.loadRelationshipsOptions();
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('eunReportDesignerApp.error', { message: err.message })),
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const organization = this.organizationFormService.getOrganization(this.editForm);
    if (organization.id !== null) {
      this.subscribeToSaveResponse(this.organizationService.update(organization));
    } else {
      this.subscribeToSaveResponse(this.organizationService.create(organization));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrganization>>): void {
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

  protected updateForm(organization: IOrganization): void {
    this.organization = organization;
    this.organizationFormService.resetForm(this.editForm, organization);

    this.eventInOrganizationsSharedCollection =
      this.eventInOrganizationService.addEventInOrganizationToCollectionIfMissing<IEventInOrganization>(
        this.eventInOrganizationsSharedCollection,
        organization.eventInOrganization
      );
    this.organizationInMinistriesSharedCollection =
      this.organizationInMinistryService.addOrganizationInMinistryToCollectionIfMissing<IOrganizationInMinistry>(
        this.organizationInMinistriesSharedCollection,
        organization.organizationInMinistry
      );
    this.organizationInProjectsSharedCollection =
      this.organizationInProjectService.addOrganizationInProjectToCollectionIfMissing<IOrganizationInProject>(
        this.organizationInProjectsSharedCollection,
        organization.organizationInProject
      );
    this.personInOrganizationsSharedCollection =
      this.personInOrganizationService.addPersonInOrganizationToCollectionIfMissing<IPersonInOrganization>(
        this.personInOrganizationsSharedCollection,
        organization.personInOrganization
      );
  }

  protected loadRelationshipsOptions(): void {
    this.eventInOrganizationService
      .query()
      .pipe(map((res: HttpResponse<IEventInOrganization[]>) => res.body ?? []))
      .pipe(
        map((eventInOrganizations: IEventInOrganization[]) =>
          this.eventInOrganizationService.addEventInOrganizationToCollectionIfMissing<IEventInOrganization>(
            eventInOrganizations,
            this.organization?.eventInOrganization
          )
        )
      )
      .subscribe((eventInOrganizations: IEventInOrganization[]) => (this.eventInOrganizationsSharedCollection = eventInOrganizations));

    this.organizationInMinistryService
      .query()
      .pipe(map((res: HttpResponse<IOrganizationInMinistry[]>) => res.body ?? []))
      .pipe(
        map((organizationInMinistries: IOrganizationInMinistry[]) =>
          this.organizationInMinistryService.addOrganizationInMinistryToCollectionIfMissing<IOrganizationInMinistry>(
            organizationInMinistries,
            this.organization?.organizationInMinistry
          )
        )
      )
      .subscribe(
        (organizationInMinistries: IOrganizationInMinistry[]) => (this.organizationInMinistriesSharedCollection = organizationInMinistries)
      );

    this.organizationInProjectService
      .query()
      .pipe(map((res: HttpResponse<IOrganizationInProject[]>) => res.body ?? []))
      .pipe(
        map((organizationInProjects: IOrganizationInProject[]) =>
          this.organizationInProjectService.addOrganizationInProjectToCollectionIfMissing<IOrganizationInProject>(
            organizationInProjects,
            this.organization?.organizationInProject
          )
        )
      )
      .subscribe(
        (organizationInProjects: IOrganizationInProject[]) => (this.organizationInProjectsSharedCollection = organizationInProjects)
      );

    this.personInOrganizationService
      .query()
      .pipe(map((res: HttpResponse<IPersonInOrganization[]>) => res.body ?? []))
      .pipe(
        map((personInOrganizations: IPersonInOrganization[]) =>
          this.personInOrganizationService.addPersonInOrganizationToCollectionIfMissing<IPersonInOrganization>(
            personInOrganizations,
            this.organization?.personInOrganization
          )
        )
      )
      .subscribe((personInOrganizations: IPersonInOrganization[]) => (this.personInOrganizationsSharedCollection = personInOrganizations));
  }
}

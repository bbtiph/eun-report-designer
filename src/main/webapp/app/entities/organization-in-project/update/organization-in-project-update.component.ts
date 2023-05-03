import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { OrganizationInProjectFormService, OrganizationInProjectFormGroup } from './organization-in-project-form.service';
import { IOrganizationInProject } from '../organization-in-project.model';
import { OrganizationInProjectService } from '../service/organization-in-project.service';
import { IProject } from 'app/entities/project/project.model';
import { ProjectService } from 'app/entities/project/service/project.service';
import { IOrganization } from 'app/entities/organization/organization.model';
import { OrganizationService } from 'app/entities/organization/service/organization.service';

@Component({
  selector: 'jhi-organization-in-project-update',
  templateUrl: './organization-in-project-update.component.html',
})
export class OrganizationInProjectUpdateComponent implements OnInit {
  isSaving = false;
  organizationInProject: IOrganizationInProject | null = null;

  projectsSharedCollection: IProject[] = [];
  organizationsSharedCollection: IOrganization[] = [];

  editForm: OrganizationInProjectFormGroup = this.organizationInProjectFormService.createOrganizationInProjectFormGroup();

  constructor(
    protected organizationInProjectService: OrganizationInProjectService,
    protected organizationInProjectFormService: OrganizationInProjectFormService,
    protected projectService: ProjectService,
    protected organizationService: OrganizationService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareProject = (o1: IProject | null, o2: IProject | null): boolean => this.projectService.compareProject(o1, o2);

  compareOrganization = (o1: IOrganization | null, o2: IOrganization | null): boolean =>
    this.organizationService.compareOrganization(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ organizationInProject }) => {
      this.organizationInProject = organizationInProject;
      if (organizationInProject) {
        this.updateForm(organizationInProject);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const organizationInProject = this.organizationInProjectFormService.getOrganizationInProject(this.editForm);
    if (organizationInProject.id !== null) {
      this.subscribeToSaveResponse(this.organizationInProjectService.update(organizationInProject));
    } else {
      this.subscribeToSaveResponse(this.organizationInProjectService.create(organizationInProject));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrganizationInProject>>): void {
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

  protected updateForm(organizationInProject: IOrganizationInProject): void {
    this.organizationInProject = organizationInProject;
    this.organizationInProjectFormService.resetForm(this.editForm, organizationInProject);

    this.projectsSharedCollection = this.projectService.addProjectToCollectionIfMissing<IProject>(
      this.projectsSharedCollection,
      organizationInProject.project
    );
    this.organizationsSharedCollection = this.organizationService.addOrganizationToCollectionIfMissing<IOrganization>(
      this.organizationsSharedCollection,
      organizationInProject.organization
    );
  }

  protected loadRelationshipsOptions(): void {
    this.projectService
      .query()
      .pipe(map((res: HttpResponse<IProject[]>) => res.body ?? []))
      .pipe(
        map((projects: IProject[]) =>
          this.projectService.addProjectToCollectionIfMissing<IProject>(projects, this.organizationInProject?.project)
        )
      )
      .subscribe((projects: IProject[]) => (this.projectsSharedCollection = projects));

    this.organizationService
      .query()
      .pipe(map((res: HttpResponse<IOrganization[]>) => res.body ?? []))
      .pipe(
        map((organizations: IOrganization[]) =>
          this.organizationService.addOrganizationToCollectionIfMissing<IOrganization>(
            organizations,
            this.organizationInProject?.organization
          )
        )
      )
      .subscribe((organizations: IOrganization[]) => (this.organizationsSharedCollection = organizations));
  }
}

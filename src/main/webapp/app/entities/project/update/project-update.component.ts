import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ProjectFormService, ProjectFormGroup } from './project-form.service';
import { IProject } from '../project.model';
import { ProjectService } from '../service/project.service';
import { IOrganizationInProject } from 'app/entities/organization-in-project/organization-in-project.model';
import { OrganizationInProjectService } from 'app/entities/organization-in-project/service/organization-in-project.service';
import { IPersonInProject } from 'app/entities/person-in-project/person-in-project.model';
import { PersonInProjectService } from 'app/entities/person-in-project/service/person-in-project.service';
import { ProjectStatus } from 'app/entities/enumerations/project-status.model';

@Component({
  selector: 'jhi-project-update',
  templateUrl: './project-update.component.html',
})
export class ProjectUpdateComponent implements OnInit {
  isSaving = false;
  project: IProject | null = null;
  projectStatusValues = Object.keys(ProjectStatus);

  organizationInProjectsSharedCollection: IOrganizationInProject[] = [];
  personInProjectsSharedCollection: IPersonInProject[] = [];

  editForm: ProjectFormGroup = this.projectFormService.createProjectFormGroup();

  constructor(
    protected projectService: ProjectService,
    protected projectFormService: ProjectFormService,
    protected organizationInProjectService: OrganizationInProjectService,
    protected personInProjectService: PersonInProjectService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareOrganizationInProject = (o1: IOrganizationInProject | null, o2: IOrganizationInProject | null): boolean =>
    this.organizationInProjectService.compareOrganizationInProject(o1, o2);

  comparePersonInProject = (o1: IPersonInProject | null, o2: IPersonInProject | null): boolean =>
    this.personInProjectService.comparePersonInProject(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ project }) => {
      this.project = project;
      if (project) {
        this.updateForm(project);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const project = this.projectFormService.getProject(this.editForm);
    if (project.id !== null) {
      this.subscribeToSaveResponse(this.projectService.update(project));
    } else {
      this.subscribeToSaveResponse(this.projectService.create(project));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProject>>): void {
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

  protected updateForm(project: IProject): void {
    this.project = project;
    this.projectFormService.resetForm(this.editForm, project);

    this.organizationInProjectsSharedCollection =
      this.organizationInProjectService.addOrganizationInProjectToCollectionIfMissing<IOrganizationInProject>(
        this.organizationInProjectsSharedCollection,
        project.organizationInProject
      );
    this.personInProjectsSharedCollection = this.personInProjectService.addPersonInProjectToCollectionIfMissing<IPersonInProject>(
      this.personInProjectsSharedCollection,
      project.personInProject
    );
  }

  protected loadRelationshipsOptions(): void {
    this.organizationInProjectService
      .query()
      .pipe(map((res: HttpResponse<IOrganizationInProject[]>) => res.body ?? []))
      .pipe(
        map((organizationInProjects: IOrganizationInProject[]) =>
          this.organizationInProjectService.addOrganizationInProjectToCollectionIfMissing<IOrganizationInProject>(
            organizationInProjects,
            this.project?.organizationInProject
          )
        )
      )
      .subscribe(
        (organizationInProjects: IOrganizationInProject[]) => (this.organizationInProjectsSharedCollection = organizationInProjects)
      );

    this.personInProjectService
      .query()
      .pipe(map((res: HttpResponse<IPersonInProject[]>) => res.body ?? []))
      .pipe(
        map((personInProjects: IPersonInProject[]) =>
          this.personInProjectService.addPersonInProjectToCollectionIfMissing<IPersonInProject>(
            personInProjects,
            this.project?.personInProject
          )
        )
      )
      .subscribe((personInProjects: IPersonInProject[]) => (this.personInProjectsSharedCollection = personInProjects));
  }
}

import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ProjectFormService, ProjectFormGroup } from './project-form.service';
import { IProject } from '../project.model';
import { ProjectService } from '../service/project.service';
import { IFunding } from 'app/entities/funding/funding.model';
import { FundingService } from 'app/entities/funding/service/funding.service';
import { ProjectStatus } from 'app/entities/enumerations/project-status.model';

@Component({
  selector: 'jhi-project-update',
  templateUrl: './project-update.component.html',
})
export class ProjectUpdateComponent implements OnInit {
  isSaving = false;
  project: IProject | null = null;
  projectStatusValues = Object.keys(ProjectStatus);

  fundingsSharedCollection: IFunding[] = [];

  editForm: ProjectFormGroup = this.projectFormService.createProjectFormGroup();

  constructor(
    protected projectService: ProjectService,
    protected projectFormService: ProjectFormService,
    protected fundingService: FundingService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareFunding = (o1: IFunding | null, o2: IFunding | null): boolean => this.fundingService.compareFunding(o1, o2);

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

    this.fundingsSharedCollection = this.fundingService.addFundingToCollectionIfMissing<IFunding>(
      this.fundingsSharedCollection,
      project.funding
    );
  }

  protected loadRelationshipsOptions(): void {
    this.fundingService
      .query()
      .pipe(map((res: HttpResponse<IFunding[]>) => res.body ?? []))
      .pipe(map((fundings: IFunding[]) => this.fundingService.addFundingToCollectionIfMissing<IFunding>(fundings, this.project?.funding)))
      .subscribe((fundings: IFunding[]) => (this.fundingsSharedCollection = fundings));
  }
}

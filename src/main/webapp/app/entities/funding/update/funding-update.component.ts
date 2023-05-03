import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { FundingFormService, FundingFormGroup } from './funding-form.service';
import { IFunding } from '../funding.model';
import { FundingService } from '../service/funding.service';
import { IProject } from 'app/entities/project/project.model';
import { ProjectService } from 'app/entities/project/service/project.service';

@Component({
  selector: 'jhi-funding-update',
  templateUrl: './funding-update.component.html',
})
export class FundingUpdateComponent implements OnInit {
  isSaving = false;
  funding: IFunding | null = null;

  projectsSharedCollection: IProject[] = [];

  editForm: FundingFormGroup = this.fundingFormService.createFundingFormGroup();

  constructor(
    protected fundingService: FundingService,
    protected fundingFormService: FundingFormService,
    protected projectService: ProjectService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareProject = (o1: IProject | null, o2: IProject | null): boolean => this.projectService.compareProject(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ funding }) => {
      this.funding = funding;
      if (funding) {
        this.updateForm(funding);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const funding = this.fundingFormService.getFunding(this.editForm);
    if (funding.id !== null) {
      this.subscribeToSaveResponse(this.fundingService.update(funding));
    } else {
      this.subscribeToSaveResponse(this.fundingService.create(funding));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFunding>>): void {
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

  protected updateForm(funding: IFunding): void {
    this.funding = funding;
    this.fundingFormService.resetForm(this.editForm, funding);

    this.projectsSharedCollection = this.projectService.addProjectToCollectionIfMissing<IProject>(
      this.projectsSharedCollection,
      funding.project
    );
  }

  protected loadRelationshipsOptions(): void {
    this.projectService
      .query()
      .pipe(map((res: HttpResponse<IProject[]>) => res.body ?? []))
      .pipe(map((projects: IProject[]) => this.projectService.addProjectToCollectionIfMissing<IProject>(projects, this.funding?.project)))
      .subscribe((projects: IProject[]) => (this.projectsSharedCollection = projects));
  }
}

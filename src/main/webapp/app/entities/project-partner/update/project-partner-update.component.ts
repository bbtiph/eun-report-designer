import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ProjectPartnerFormService, ProjectPartnerFormGroup } from './project-partner-form.service';
import { IProjectPartner } from '../project-partner.model';
import { ProjectPartnerService } from '../service/project-partner.service';
import { IJobInfo } from 'app/entities/job-info/job-info.model';
import { JobInfoService } from 'app/entities/job-info/service/job-info.service';

@Component({
  selector: 'jhi-project-partner-update',
  templateUrl: './project-partner-update.component.html',
})
export class ProjectPartnerUpdateComponent implements OnInit {
  isSaving = false;
  projectPartner: IProjectPartner | null = null;

  jobInfosSharedCollection: IJobInfo[] = [];

  editForm: ProjectPartnerFormGroup = this.projectPartnerFormService.createProjectPartnerFormGroup();

  constructor(
    protected projectPartnerService: ProjectPartnerService,
    protected projectPartnerFormService: ProjectPartnerFormService,
    protected jobInfoService: JobInfoService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareJobInfo = (o1: IJobInfo | null, o2: IJobInfo | null): boolean => this.jobInfoService.compareJobInfo(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ projectPartner }) => {
      this.projectPartner = projectPartner;
      if (projectPartner) {
        this.updateForm(projectPartner);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const projectPartner = this.projectPartnerFormService.getProjectPartner(this.editForm);
    if (projectPartner.id !== null) {
      this.subscribeToSaveResponse(this.projectPartnerService.update(projectPartner));
    } else {
      this.subscribeToSaveResponse(this.projectPartnerService.create(projectPartner));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProjectPartner>>): void {
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

  protected updateForm(projectPartner: IProjectPartner): void {
    this.projectPartner = projectPartner;
    this.projectPartnerFormService.resetForm(this.editForm, projectPartner);

    this.jobInfosSharedCollection = this.jobInfoService.addJobInfoToCollectionIfMissing<IJobInfo>(
      this.jobInfosSharedCollection,
      projectPartner.jobInfo
    );
  }

  protected loadRelationshipsOptions(): void {
    this.jobInfoService
      .query()
      .pipe(map((res: HttpResponse<IJobInfo[]>) => res.body ?? []))
      .pipe(
        map((jobInfos: IJobInfo[]) => this.jobInfoService.addJobInfoToCollectionIfMissing<IJobInfo>(jobInfos, this.projectPartner?.jobInfo))
      )
      .subscribe((jobInfos: IJobInfo[]) => (this.jobInfosSharedCollection = jobInfos));
  }
}

import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { JobInfoFormService, JobInfoFormGroup } from './job-info-form.service';
import { IJobInfo } from '../job-info.model';
import { JobInfoService } from '../service/job-info.service';

@Component({
  selector: 'jhi-job-info-update',
  templateUrl: './job-info-update.component.html',
})
export class JobInfoUpdateComponent implements OnInit {
  isSaving = false;
  jobInfo: IJobInfo | null = null;

  editForm: JobInfoFormGroup = this.jobInfoFormService.createJobInfoFormGroup();

  constructor(
    protected jobInfoService: JobInfoService,
    protected jobInfoFormService: JobInfoFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ jobInfo }) => {
      this.jobInfo = jobInfo;
      if (jobInfo) {
        this.updateForm(jobInfo);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const jobInfo = this.jobInfoFormService.getJobInfo(this.editForm);
    if (jobInfo.id !== null) {
      this.subscribeToSaveResponse(this.jobInfoService.update(jobInfo));
    } else {
      this.subscribeToSaveResponse(this.jobInfoService.create(jobInfo));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IJobInfo>>): void {
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

  protected updateForm(jobInfo: IJobInfo): void {
    this.jobInfo = jobInfo;
    this.jobInfoFormService.resetForm(this.editForm, jobInfo);
  }
}

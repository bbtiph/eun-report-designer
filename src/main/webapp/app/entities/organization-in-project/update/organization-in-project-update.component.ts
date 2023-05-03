import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { OrganizationInProjectFormService, OrganizationInProjectFormGroup } from './organization-in-project-form.service';
import { IOrganizationInProject } from '../organization-in-project.model';
import { OrganizationInProjectService } from '../service/organization-in-project.service';

@Component({
  selector: 'jhi-organization-in-project-update',
  templateUrl: './organization-in-project-update.component.html',
})
export class OrganizationInProjectUpdateComponent implements OnInit {
  isSaving = false;
  organizationInProject: IOrganizationInProject | null = null;

  editForm: OrganizationInProjectFormGroup = this.organizationInProjectFormService.createOrganizationInProjectFormGroup();

  constructor(
    protected organizationInProjectService: OrganizationInProjectService,
    protected organizationInProjectFormService: OrganizationInProjectFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ organizationInProject }) => {
      this.organizationInProject = organizationInProject;
      if (organizationInProject) {
        this.updateForm(organizationInProject);
      }
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
  }
}

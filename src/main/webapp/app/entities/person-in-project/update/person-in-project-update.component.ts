import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { PersonInProjectFormService, PersonInProjectFormGroup } from './person-in-project-form.service';
import { IPersonInProject } from '../person-in-project.model';
import { PersonInProjectService } from '../service/person-in-project.service';

@Component({
  selector: 'jhi-person-in-project-update',
  templateUrl: './person-in-project-update.component.html',
})
export class PersonInProjectUpdateComponent implements OnInit {
  isSaving = false;
  personInProject: IPersonInProject | null = null;

  editForm: PersonInProjectFormGroup = this.personInProjectFormService.createPersonInProjectFormGroup();

  constructor(
    protected personInProjectService: PersonInProjectService,
    protected personInProjectFormService: PersonInProjectFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ personInProject }) => {
      this.personInProject = personInProject;
      if (personInProject) {
        this.updateForm(personInProject);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const personInProject = this.personInProjectFormService.getPersonInProject(this.editForm);
    if (personInProject.id !== null) {
      this.subscribeToSaveResponse(this.personInProjectService.update(personInProject));
    } else {
      this.subscribeToSaveResponse(this.personInProjectService.create(personInProject));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPersonInProject>>): void {
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

  protected updateForm(personInProject: IPersonInProject): void {
    this.personInProject = personInProject;
    this.personInProjectFormService.resetForm(this.editForm, personInProject);
  }
}

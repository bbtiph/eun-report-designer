import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { PersonInProjectFormService, PersonInProjectFormGroup } from './person-in-project-form.service';
import { IPersonInProject } from '../person-in-project.model';
import { PersonInProjectService } from '../service/person-in-project.service';
import { IPerson } from 'app/entities/person/person.model';
import { PersonService } from 'app/entities/person/service/person.service';
import { IProject } from 'app/entities/project/project.model';
import { ProjectService } from 'app/entities/project/service/project.service';

@Component({
  selector: 'jhi-person-in-project-update',
  templateUrl: './person-in-project-update.component.html',
})
export class PersonInProjectUpdateComponent implements OnInit {
  isSaving = false;
  personInProject: IPersonInProject | null = null;

  peopleSharedCollection: IPerson[] = [];
  projectsSharedCollection: IProject[] = [];

  editForm: PersonInProjectFormGroup = this.personInProjectFormService.createPersonInProjectFormGroup();

  constructor(
    protected personInProjectService: PersonInProjectService,
    protected personInProjectFormService: PersonInProjectFormService,
    protected personService: PersonService,
    protected projectService: ProjectService,
    protected activatedRoute: ActivatedRoute
  ) {}

  comparePerson = (o1: IPerson | null, o2: IPerson | null): boolean => this.personService.comparePerson(o1, o2);

  compareProject = (o1: IProject | null, o2: IProject | null): boolean => this.projectService.compareProject(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ personInProject }) => {
      this.personInProject = personInProject;
      if (personInProject) {
        this.updateForm(personInProject);
      }

      this.loadRelationshipsOptions();
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

    this.peopleSharedCollection = this.personService.addPersonToCollectionIfMissing<IPerson>(
      this.peopleSharedCollection,
      personInProject.person
    );
    this.projectsSharedCollection = this.projectService.addProjectToCollectionIfMissing<IProject>(
      this.projectsSharedCollection,
      personInProject.project
    );
  }

  protected loadRelationshipsOptions(): void {
    this.personService
      .query()
      .pipe(map((res: HttpResponse<IPerson[]>) => res.body ?? []))
      .pipe(map((people: IPerson[]) => this.personService.addPersonToCollectionIfMissing<IPerson>(people, this.personInProject?.person)))
      .subscribe((people: IPerson[]) => (this.peopleSharedCollection = people));

    this.projectService
      .query()
      .pipe(map((res: HttpResponse<IProject[]>) => res.body ?? []))
      .pipe(
        map((projects: IProject[]) =>
          this.projectService.addProjectToCollectionIfMissing<IProject>(projects, this.personInProject?.project)
        )
      )
      .subscribe((projects: IProject[]) => (this.projectsSharedCollection = projects));
  }
}

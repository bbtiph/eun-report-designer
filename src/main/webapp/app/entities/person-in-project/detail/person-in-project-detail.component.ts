import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPersonInProject } from '../person-in-project.model';

@Component({
  selector: 'jhi-person-in-project-detail',
  templateUrl: './person-in-project-detail.component.html',
})
export class PersonInProjectDetailComponent implements OnInit {
  personInProject: IPersonInProject | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ personInProject }) => {
      this.personInProject = personInProject;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

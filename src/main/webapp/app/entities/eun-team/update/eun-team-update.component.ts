import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { EunTeamFormService, EunTeamFormGroup } from './eun-team-form.service';
import { IEunTeam } from '../eun-team.model';
import { EunTeamService } from '../service/eun-team.service';

@Component({
  selector: 'jhi-eun-team-update',
  templateUrl: './eun-team-update.component.html',
})
export class EunTeamUpdateComponent implements OnInit {
  isSaving = false;
  eunTeam: IEunTeam | null = null;

  editForm: EunTeamFormGroup = this.eunTeamFormService.createEunTeamFormGroup();

  constructor(
    protected eunTeamService: EunTeamService,
    protected eunTeamFormService: EunTeamFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ eunTeam }) => {
      this.eunTeam = eunTeam;
      if (eunTeam) {
        this.updateForm(eunTeam);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const eunTeam = this.eunTeamFormService.getEunTeam(this.editForm);
    if (eunTeam.id !== null) {
      this.subscribeToSaveResponse(this.eunTeamService.update(eunTeam));
    } else {
      this.subscribeToSaveResponse(this.eunTeamService.create(eunTeam));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEunTeam>>): void {
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

  protected updateForm(eunTeam: IEunTeam): void {
    this.eunTeam = eunTeam;
    this.eunTeamFormService.resetForm(this.editForm, eunTeam);
  }
}

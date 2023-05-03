import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { EunTeamMemberFormService, EunTeamMemberFormGroup } from './eun-team-member-form.service';
import { IEunTeamMember } from '../eun-team-member.model';
import { EunTeamMemberService } from '../service/eun-team-member.service';

@Component({
  selector: 'jhi-eun-team-member-update',
  templateUrl: './eun-team-member-update.component.html',
})
export class EunTeamMemberUpdateComponent implements OnInit {
  isSaving = false;
  eunTeamMember: IEunTeamMember | null = null;

  editForm: EunTeamMemberFormGroup = this.eunTeamMemberFormService.createEunTeamMemberFormGroup();

  constructor(
    protected eunTeamMemberService: EunTeamMemberService,
    protected eunTeamMemberFormService: EunTeamMemberFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ eunTeamMember }) => {
      this.eunTeamMember = eunTeamMember;
      if (eunTeamMember) {
        this.updateForm(eunTeamMember);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const eunTeamMember = this.eunTeamMemberFormService.getEunTeamMember(this.editForm);
    if (eunTeamMember.id !== null) {
      this.subscribeToSaveResponse(this.eunTeamMemberService.update(eunTeamMember));
    } else {
      this.subscribeToSaveResponse(this.eunTeamMemberService.create(eunTeamMember));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEunTeamMember>>): void {
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

  protected updateForm(eunTeamMember: IEunTeamMember): void {
    this.eunTeamMember = eunTeamMember;
    this.eunTeamMemberFormService.resetForm(this.editForm, eunTeamMember);
  }
}

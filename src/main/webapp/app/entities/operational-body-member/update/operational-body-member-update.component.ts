import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { OperationalBodyMemberFormService, OperationalBodyMemberFormGroup } from './operational-body-member-form.service';
import { IOperationalBodyMember } from '../operational-body-member.model';
import { OperationalBodyMemberService } from '../service/operational-body-member.service';

@Component({
  selector: 'jhi-operational-body-member-update',
  templateUrl: './operational-body-member-update.component.html',
})
export class OperationalBodyMemberUpdateComponent implements OnInit {
  isSaving = false;
  operationalBodyMember: IOperationalBodyMember | null = null;

  editForm: OperationalBodyMemberFormGroup = this.operationalBodyMemberFormService.createOperationalBodyMemberFormGroup();

  constructor(
    protected operationalBodyMemberService: OperationalBodyMemberService,
    protected operationalBodyMemberFormService: OperationalBodyMemberFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ operationalBodyMember }) => {
      this.operationalBodyMember = operationalBodyMember;
      if (operationalBodyMember) {
        this.updateForm(operationalBodyMember);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const operationalBodyMember = this.operationalBodyMemberFormService.getOperationalBodyMember(this.editForm);
    if (operationalBodyMember.id !== null) {
      this.subscribeToSaveResponse(this.operationalBodyMemberService.update(operationalBodyMember));
    } else {
      this.subscribeToSaveResponse(this.operationalBodyMemberService.create(operationalBodyMember));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOperationalBodyMember>>): void {
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

  protected updateForm(operationalBodyMember: IOperationalBodyMember): void {
    this.operationalBodyMember = operationalBodyMember;
    this.operationalBodyMemberFormService.resetForm(this.editForm, operationalBodyMember);
  }
}

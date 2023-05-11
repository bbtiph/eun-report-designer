import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { PrivilegeFormService, PrivilegeFormGroup } from './privilege-form.service';
import { IPrivilege } from '../privilege.model';
import { PrivilegeService } from '../service/privilege.service';

@Component({
  selector: 'jhi-privilege-update',
  templateUrl: './privilege-update.component.html',
})
export class PrivilegeUpdateComponent implements OnInit {
  isSaving = false;
  privilege: IPrivilege | null = null;

  editForm: PrivilegeFormGroup = this.privilegeFormService.createPrivilegeFormGroup();

  constructor(
    protected privilegeService: PrivilegeService,
    protected privilegeFormService: PrivilegeFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ privilege }) => {
      this.privilege = privilege;
      if (privilege) {
        this.updateForm(privilege);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const privilege = this.privilegeFormService.getPrivilege(this.editForm);
    if (privilege.id !== null) {
      this.subscribeToSaveResponse(this.privilegeService.update(privilege));
    } else {
      this.subscribeToSaveResponse(this.privilegeService.create(privilege));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPrivilege>>): void {
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

  protected updateForm(privilege: IPrivilege): void {
    this.privilege = privilege;
    this.privilegeFormService.resetForm(this.editForm, privilege);
  }
}

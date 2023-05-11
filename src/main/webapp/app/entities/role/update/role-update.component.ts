import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { RoleFormService, RoleFormGroup } from './role-form.service';
import { IRole } from '../role.model';
import { RoleService } from '../service/role.service';
import { IPrivilege } from 'app/entities/privilege/privilege.model';
import { PrivilegeService } from 'app/entities/privilege/service/privilege.service';

@Component({
  selector: 'jhi-role-update',
  templateUrl: './role-update.component.html',
})
export class RoleUpdateComponent implements OnInit {
  isSaving = false;
  role: IRole | null = null;

  privilegesSharedCollection: IPrivilege[] = [];

  editForm: RoleFormGroup = this.roleFormService.createRoleFormGroup();

  constructor(
    protected roleService: RoleService,
    protected roleFormService: RoleFormService,
    protected privilegeService: PrivilegeService,
    protected activatedRoute: ActivatedRoute
  ) {}

  comparePrivilege = (o1: IPrivilege | null, o2: IPrivilege | null): boolean => this.privilegeService.comparePrivilege(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ role }) => {
      this.role = role;
      if (role) {
        this.updateForm(role);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const role = this.roleFormService.getRole(this.editForm);
    if (role.id !== null) {
      this.subscribeToSaveResponse(this.roleService.update(role));
    } else {
      this.subscribeToSaveResponse(this.roleService.create(role));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRole>>): void {
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

  protected updateForm(role: IRole): void {
    this.role = role;
    this.roleFormService.resetForm(this.editForm, role);

    this.privilegesSharedCollection = this.privilegeService.addPrivilegeToCollectionIfMissing<IPrivilege>(
      this.privilegesSharedCollection,
      ...(role.privileges ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.privilegeService
      .query()
      .pipe(map((res: HttpResponse<IPrivilege[]>) => res.body ?? []))
      .pipe(
        map((privileges: IPrivilege[]) =>
          this.privilegeService.addPrivilegeToCollectionIfMissing<IPrivilege>(privileges, ...(this.role?.privileges ?? []))
        )
      )
      .subscribe((privileges: IPrivilege[]) => (this.privilegesSharedCollection = privileges));
  }
}

import { Component, OnInit } from '@angular/core';
import { IRole } from '../role.model';
import { IPrivilege } from '../../privilege/privilege.model';
import { RoleService } from '../service/role.service';
import { PrivilegeService } from '../../privilege/service/privilege.service';
import { Observable } from 'rxjs';
import { HttpResponse } from '@angular/common/http';
import { finalize } from 'rxjs/operators';
import { AccountService } from '../../../core/auth/account.service';

@Component({
  selector: 'app-role-privilege-table',
  templateUrl: './role-privilege.component.html',
})
export class RolePrivilegeComponent implements OnInit {
  isSaving = false;
  isEditing = false;
  roles: IRole[] = [];
  privileges: IPrivilege[] = [];

  constructor(private roleService: RoleService, private privilegeService: PrivilegeService, private accountService: AccountService) {}

  ngOnInit() {
    this.getRolesAndPrivileges();
    this.isEditing = this.accountService.hasAnyAuthority('MP');
  }

  getRolesAndPrivileges(): void {
    this.roleService.getRoles().subscribe(roles => {
      this.roles = roles;
    });
    this.privilegeService.getPrivileges().subscribe(privileges => {
      this.privileges = privileges;
    });
  }

  isPrivilegeSelected(role: IRole, privilege: IPrivilege): boolean {
    return role.privileges?.find(iPrivilege => iPrivilege.name === privilege.name) !== undefined;
  }

  togglePrivilege(role: IRole, privilege: IPrivilege): void {
    if (this.isPrivilegeSelected(role, privilege)) {
      role.privileges = role.privileges!.filter(p => p.name !== privilege.name);
    } else {
      role.privileges!.push(privilege);
    }
    this.isSaving = true;
    this.subscribeToSaveResponse(this.roleService.update(role));
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRole>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    // Api for inheritance.
  }

  previousState(): void {
    window.history.back();
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }
}

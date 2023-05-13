import { Component, OnInit } from '@angular/core';
import { IRole } from '../role.model';
import { IPrivilege } from '../../privilege/privilege.model';
import { RoleService } from '../service/role.service';
import { PrivilegeService } from '../../privilege/service/privilege.service';

@Component({
  selector: 'app-role-privilege-table',
  templateUrl: './role-privilege.component.html',
})
export class RolePrivilegeComponent implements OnInit {
  roles: IRole[] = [];
  privileges: IPrivilege[] = [];

  constructor(private roleService: RoleService, private privilegeService: PrivilegeService) {}

  ngOnInit() {
    this.getRolesAndPrivileges();
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
      role.privileges = role.privileges!.filter(p => p !== privilege);
    } else {
      role.privileges!.push(privilege);
    }
    this.roleService.update(role).subscribe();
  }
}

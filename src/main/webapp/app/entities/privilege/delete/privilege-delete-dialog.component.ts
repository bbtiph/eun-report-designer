import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPrivilege } from '../privilege.model';
import { PrivilegeService } from '../service/privilege.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './privilege-delete-dialog.component.html',
})
export class PrivilegeDeleteDialogComponent {
  privilege?: IPrivilege;

  constructor(protected privilegeService: PrivilegeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.privilegeService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

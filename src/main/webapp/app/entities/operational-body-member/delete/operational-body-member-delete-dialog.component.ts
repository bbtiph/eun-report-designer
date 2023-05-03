import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IOperationalBodyMember } from '../operational-body-member.model';
import { OperationalBodyMemberService } from '../service/operational-body-member.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './operational-body-member-delete-dialog.component.html',
})
export class OperationalBodyMemberDeleteDialogComponent {
  operationalBodyMember?: IOperationalBodyMember;

  constructor(protected operationalBodyMemberService: OperationalBodyMemberService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.operationalBodyMemberService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

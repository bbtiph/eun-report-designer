import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEunTeamMember } from '../eun-team-member.model';
import { EunTeamMemberService } from '../service/eun-team-member.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './eun-team-member-delete-dialog.component.html',
})
export class EunTeamMemberDeleteDialogComponent {
  eunTeamMember?: IEunTeamMember;

  constructor(protected eunTeamMemberService: EunTeamMemberService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.eunTeamMemberService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

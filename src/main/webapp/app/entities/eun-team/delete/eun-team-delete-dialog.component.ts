import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEunTeam } from '../eun-team.model';
import { EunTeamService } from '../service/eun-team.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './eun-team-delete-dialog.component.html',
})
export class EunTeamDeleteDialogComponent {
  eunTeam?: IEunTeam;

  constructor(protected eunTeamService: EunTeamService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.eunTeamService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IParticipantsEunIndicator } from '../participants-eun-indicator.model';
import { ParticipantsEunIndicatorService } from '../service/participants-eun-indicator.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './participants-eun-indicator-delete-dialog.component.html',
})
export class ParticipantsEunIndicatorDeleteDialogComponent {
  participantsEunIndicator?: IParticipantsEunIndicator;

  constructor(protected participantsEunIndicatorService: ParticipantsEunIndicatorService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.participantsEunIndicatorService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

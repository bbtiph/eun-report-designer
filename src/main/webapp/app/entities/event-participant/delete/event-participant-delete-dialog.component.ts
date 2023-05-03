import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEventParticipant } from '../event-participant.model';
import { EventParticipantService } from '../service/event-participant.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './event-participant-delete-dialog.component.html',
})
export class EventParticipantDeleteDialogComponent {
  eventParticipant?: IEventParticipant;

  constructor(protected eventParticipantService: EventParticipantService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.eventParticipantService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

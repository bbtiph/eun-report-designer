import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEventReferencesParticipantsCategory } from '../event-references-participants-category.model';
import { EventReferencesParticipantsCategoryService } from '../service/event-references-participants-category.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './event-references-participants-category-delete-dialog.component.html',
})
export class EventReferencesParticipantsCategoryDeleteDialogComponent {
  eventReferencesParticipantsCategory?: IEventReferencesParticipantsCategory;

  constructor(
    protected eventReferencesParticipantsCategoryService: EventReferencesParticipantsCategoryService,
    protected activeModal: NgbActiveModal
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.eventReferencesParticipantsCategoryService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

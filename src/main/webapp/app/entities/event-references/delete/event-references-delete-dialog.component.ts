import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEventReferences } from '../event-references.model';
import { EventReferencesService } from '../service/event-references.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './event-references-delete-dialog.component.html',
})
export class EventReferencesDeleteDialogComponent {
  eventReferences?: IEventReferences;

  constructor(protected eventReferencesService: EventReferencesService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.eventReferencesService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

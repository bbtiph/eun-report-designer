import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IMoeContacts } from '../moe-contacts.model';
import { MoeContactsService } from '../service/moe-contacts.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './moe-contacts-delete-dialog.component.html',
})
export class MoeContactsDeleteDialogComponent {
  moeContacts?: IMoeContacts;

  constructor(protected moeContactsService: MoeContactsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.moeContactsService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

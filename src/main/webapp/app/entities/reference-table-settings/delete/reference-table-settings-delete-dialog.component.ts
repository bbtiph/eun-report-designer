import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IReferenceTableSettings } from '../reference-table-settings.model';
import { ReferenceTableSettingsService } from '../service/reference-table-settings.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './reference-table-settings-delete-dialog.component.html',
})
export class ReferenceTableSettingsDeleteDialogComponent {
  referenceTableSettings?: IReferenceTableSettings;

  constructor(protected referenceTableSettingsService: ReferenceTableSettingsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.referenceTableSettingsService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

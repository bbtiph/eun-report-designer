import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IMOEParticipationReferences } from '../moe-participation-references.model';
import { MOEParticipationReferencesService } from '../service/moe-participation-references.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './moe-participation-references-delete-dialog.component.html',
})
export class MOEParticipationReferencesDeleteDialogComponent {
  mOEParticipationReferences?: IMOEParticipationReferences;

  constructor(protected mOEParticipationReferencesService: MOEParticipationReferencesService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.mOEParticipationReferencesService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

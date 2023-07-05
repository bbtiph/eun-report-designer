import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IWorkingGroupReferences } from '../working-group-references.model';
import { WorkingGroupReferencesService } from '../service/working-group-references.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './working-group-references-delete-dialog.component.html',
})
export class WorkingGroupReferencesDeleteDialogComponent {
  workingGroupReferences?: IWorkingGroupReferences;

  constructor(protected workingGroupReferencesService: WorkingGroupReferencesService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.workingGroupReferencesService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

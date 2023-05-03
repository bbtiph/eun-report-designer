import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IOperationalBody } from '../operational-body.model';
import { OperationalBodyService } from '../service/operational-body.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './operational-body-delete-dialog.component.html',
})
export class OperationalBodyDeleteDialogComponent {
  operationalBody?: IOperationalBody;

  constructor(protected operationalBodyService: OperationalBodyService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.operationalBodyService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

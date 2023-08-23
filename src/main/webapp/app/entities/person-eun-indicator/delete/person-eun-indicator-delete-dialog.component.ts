import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPersonEunIndicator } from '../person-eun-indicator.model';
import { PersonEunIndicatorService } from '../service/person-eun-indicator.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './person-eun-indicator-delete-dialog.component.html',
})
export class PersonEunIndicatorDeleteDialogComponent {
  personEunIndicator?: IPersonEunIndicator;

  constructor(protected personEunIndicatorService: PersonEunIndicatorService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.personEunIndicatorService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

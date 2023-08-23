import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IOrganizationEunIndicator } from '../organization-eun-indicator.model';
import { OrganizationEunIndicatorService } from '../service/organization-eun-indicator.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './organization-eun-indicator-delete-dialog.component.html',
})
export class OrganizationEunIndicatorDeleteDialogComponent {
  organizationEunIndicator?: IOrganizationEunIndicator;

  constructor(protected organizationEunIndicatorService: OrganizationEunIndicatorService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.organizationEunIndicatorService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

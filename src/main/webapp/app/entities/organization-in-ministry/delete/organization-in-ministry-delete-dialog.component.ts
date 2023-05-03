import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IOrganizationInMinistry } from '../organization-in-ministry.model';
import { OrganizationInMinistryService } from '../service/organization-in-ministry.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './organization-in-ministry-delete-dialog.component.html',
})
export class OrganizationInMinistryDeleteDialogComponent {
  organizationInMinistry?: IOrganizationInMinistry;

  constructor(protected organizationInMinistryService: OrganizationInMinistryService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.organizationInMinistryService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

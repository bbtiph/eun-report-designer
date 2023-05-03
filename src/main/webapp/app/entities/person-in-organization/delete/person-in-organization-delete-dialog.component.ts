import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPersonInOrganization } from '../person-in-organization.model';
import { PersonInOrganizationService } from '../service/person-in-organization.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './person-in-organization-delete-dialog.component.html',
})
export class PersonInOrganizationDeleteDialogComponent {
  personInOrganization?: IPersonInOrganization;

  constructor(protected personInOrganizationService: PersonInOrganizationService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.personInOrganizationService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEventInOrganization } from '../event-in-organization.model';
import { EventInOrganizationService } from '../service/event-in-organization.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './event-in-organization-delete-dialog.component.html',
})
export class EventInOrganizationDeleteDialogComponent {
  eventInOrganization?: IEventInOrganization;

  constructor(protected eventInOrganizationService: EventInOrganizationService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.eventInOrganizationService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

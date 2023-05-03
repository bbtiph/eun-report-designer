import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IOrganizationInProject } from '../organization-in-project.model';
import { OrganizationInProjectService } from '../service/organization-in-project.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './organization-in-project-delete-dialog.component.html',
})
export class OrganizationInProjectDeleteDialogComponent {
  organizationInProject?: IOrganizationInProject;

  constructor(protected organizationInProjectService: OrganizationInProjectService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.organizationInProjectService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

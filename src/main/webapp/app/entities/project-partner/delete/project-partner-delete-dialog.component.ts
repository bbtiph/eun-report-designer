import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IProjectPartner } from '../project-partner.model';
import { ProjectPartnerService } from '../service/project-partner.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './project-partner-delete-dialog.component.html',
})
export class ProjectPartnerDeleteDialogComponent {
  projectPartner?: IProjectPartner;

  constructor(protected projectPartnerService: ProjectPartnerService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.projectPartnerService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

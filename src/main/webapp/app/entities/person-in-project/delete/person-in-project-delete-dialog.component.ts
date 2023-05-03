import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPersonInProject } from '../person-in-project.model';
import { PersonInProjectService } from '../service/person-in-project.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './person-in-project-delete-dialog.component.html',
})
export class PersonInProjectDeleteDialogComponent {
  personInProject?: IPersonInProject;

  constructor(protected personInProjectService: PersonInProjectService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.personInProjectService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

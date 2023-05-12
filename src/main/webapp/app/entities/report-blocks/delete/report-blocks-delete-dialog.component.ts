import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IReportBlocks } from '../report-blocks.model';
import { ReportBlocksService } from '../service/report-blocks.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './report-blocks-delete-dialog.component.html',
})
export class ReportBlocksDeleteDialogComponent {
  reportBlocks?: IReportBlocks;

  constructor(protected reportBlocksService: ReportBlocksService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.reportBlocksService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

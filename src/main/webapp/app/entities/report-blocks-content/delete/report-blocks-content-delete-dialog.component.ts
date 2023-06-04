import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IReportBlocksContent } from '../report-blocks-content.model';
import { ReportBlocksContentService } from '../service/report-blocks-content.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './report-blocks-content-delete-dialog.component.html',
})
export class ReportBlocksContentDeleteDialogComponent {
  reportBlocksContent?: IReportBlocksContent;

  constructor(protected reportBlocksContentService: ReportBlocksContentService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.reportBlocksContentService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

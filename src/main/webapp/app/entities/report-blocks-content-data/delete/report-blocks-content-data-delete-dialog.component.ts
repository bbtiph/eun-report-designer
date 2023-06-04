import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IReportBlocksContentData } from '../report-blocks-content-data.model';
import { ReportBlocksContentDataService } from '../service/report-blocks-content-data.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './report-blocks-content-data-delete-dialog.component.html',
})
export class ReportBlocksContentDataDeleteDialogComponent {
  reportBlocksContentData?: IReportBlocksContentData;

  constructor(protected reportBlocksContentDataService: ReportBlocksContentDataService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.reportBlocksContentDataService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

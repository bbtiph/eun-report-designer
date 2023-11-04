import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IGeneratedReport } from '../generated-report.model';
import { GeneratedReportService } from '../service/generated-report.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './generated-report-delete-dialog.component.html',
})
export class GeneratedReportDeleteDialogComponent {
  generatedReport?: IGeneratedReport;

  constructor(protected generatedReportService: GeneratedReportService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.generatedReportService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

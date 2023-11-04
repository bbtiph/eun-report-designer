import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { GeneratedReportComponent } from './list/generated-report.component';
import { GeneratedReportDetailComponent } from './detail/generated-report-detail.component';
import { GeneratedReportUpdateComponent } from './update/generated-report-update.component';
import { GeneratedReportDeleteDialogComponent } from './delete/generated-report-delete-dialog.component';
import { GeneratedReportRoutingModule } from './route/generated-report-routing.module';

@NgModule({
  imports: [SharedModule, GeneratedReportRoutingModule],
  declarations: [
    GeneratedReportComponent,
    GeneratedReportDetailComponent,
    GeneratedReportUpdateComponent,
    GeneratedReportDeleteDialogComponent,
  ],
})
export class GeneratedReportModule {}

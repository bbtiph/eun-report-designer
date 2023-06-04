import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ReportBlocksContentDataComponent } from './list/report-blocks-content-data.component';
import { ReportBlocksContentDataDetailComponent } from './detail/report-blocks-content-data-detail.component';
import { ReportBlocksContentDataUpdateComponent } from './update/report-blocks-content-data-update.component';
import { ReportBlocksContentDataDeleteDialogComponent } from './delete/report-blocks-content-data-delete-dialog.component';
import { ReportBlocksContentDataRoutingModule } from './route/report-blocks-content-data-routing.module';

@NgModule({
  imports: [SharedModule, ReportBlocksContentDataRoutingModule],
  declarations: [
    ReportBlocksContentDataComponent,
    ReportBlocksContentDataDetailComponent,
    ReportBlocksContentDataUpdateComponent,
    ReportBlocksContentDataDeleteDialogComponent,
  ],
})
export class ReportBlocksContentDataModule {}

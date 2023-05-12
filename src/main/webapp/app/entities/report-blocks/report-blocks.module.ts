import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ReportBlocksComponent } from './list/report-blocks.component';
import { ReportBlocksDetailComponent } from './detail/report-blocks-detail.component';
import { ReportBlocksUpdateComponent } from './update/report-blocks-update.component';
import { ReportBlocksDeleteDialogComponent } from './delete/report-blocks-delete-dialog.component';
import { ReportBlocksRoutingModule } from './route/report-blocks-routing.module';

@NgModule({
  imports: [SharedModule, ReportBlocksRoutingModule],
  declarations: [ReportBlocksComponent, ReportBlocksDetailComponent, ReportBlocksUpdateComponent, ReportBlocksDeleteDialogComponent],
})
export class ReportBlocksModule {}

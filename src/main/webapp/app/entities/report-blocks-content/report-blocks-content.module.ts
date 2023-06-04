import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ReportBlocksContentComponent } from './list/report-blocks-content.component';
import { ReportBlocksContentDetailComponent } from './detail/report-blocks-content-detail.component';
import { ReportBlocksContentUpdateComponent } from './update/report-blocks-content-update.component';
import { ReportBlocksContentDeleteDialogComponent } from './delete/report-blocks-content-delete-dialog.component';
import { ReportBlocksContentRoutingModule } from './route/report-blocks-content-routing.module';

@NgModule({
  imports: [SharedModule, ReportBlocksContentRoutingModule],
  declarations: [
    ReportBlocksContentComponent,
    ReportBlocksContentDetailComponent,
    ReportBlocksContentUpdateComponent,
    ReportBlocksContentDeleteDialogComponent,
  ],
})
export class ReportBlocksContentModule {}

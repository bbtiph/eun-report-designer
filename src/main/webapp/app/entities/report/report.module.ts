import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ReportComponent } from './list/report.component';
import { ReportDetailComponent } from './detail/report-detail.component';
import { ReportUpdateComponent } from './update/report-update.component';
import { ReportDeleteDialogComponent } from './delete/report-delete-dialog.component';
import { ReportRoutingModule } from './route/report-routing.module';
import { ReportBlocksManageComponent } from './report-blocks-manage/report-blocks-manage.component';

@NgModule({
  imports: [SharedModule, ReportRoutingModule],
  declarations: [ReportComponent, ReportDetailComponent, ReportUpdateComponent, ReportDeleteDialogComponent, ReportBlocksManageComponent],
})
export class ReportModule {}

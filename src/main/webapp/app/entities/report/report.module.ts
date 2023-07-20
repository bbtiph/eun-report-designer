import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ReportComponent } from './list/report.component';
import { ReportDetailComponent } from './detail/report-detail.component';
import { ReportUpdateComponent } from './update/report-update.component';
import { ReportDeleteDialogComponent } from './delete/report-delete-dialog.component';
import { ReportRoutingModule } from './route/report-routing.module';
import { ReportBlocksManageComponent } from './report-blocks-manage/report-blocks-manage.component';
import { ReportBlockEdit } from './report-blocks-manage/report-block-edit/report-block-edit.component';
import { AngularEditorModule } from '@kolkov/angular-editor';
import { ReportBlocksModule } from '../report-blocks/report-blocks.module';
import { ReportCountryContentComponent } from './report-country-content/report-country-content.component';
import { DragulaModule } from 'ng2-dragula';

@NgModule({
  imports: [SharedModule, ReportRoutingModule, AngularEditorModule, ReportBlocksModule, DragulaModule],
  declarations: [
    ReportComponent,
    ReportDetailComponent,
    ReportUpdateComponent,
    ReportDeleteDialogComponent,
    ReportBlocksManageComponent,
    ReportBlockEdit,
    ReportCountryContentComponent,
  ],
})
export class ReportModule {}

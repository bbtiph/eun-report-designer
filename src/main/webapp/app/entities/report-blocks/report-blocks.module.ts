import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ReportBlocksComponent } from './list/report-blocks.component';
import { ReportBlocksDetailComponent } from './detail/report-blocks-detail.component';
import { ReportBlocksUpdateComponent } from './update/report-blocks-update.component';
import { ReportBlocksDeleteDialogComponent } from './delete/report-blocks-delete-dialog.component';
import { ReportBlocksRoutingModule } from './route/report-blocks-routing.module';
import { AngularEditorModule } from '@kolkov/angular-editor';
import { ReportBlocksCountryContentComponent } from './report-blocks-country-content/report-blocks-country-content.component';
import { SortContentByPriorityPipe } from './sort/sort-content-by-priority.pipe';
import { WorkingGroupReferencesModule } from '../working-group-references/working-group-references.module';

@NgModule({
  imports: [SharedModule, ReportBlocksRoutingModule, AngularEditorModule, WorkingGroupReferencesModule],
  declarations: [
    ReportBlocksComponent,
    ReportBlocksDetailComponent,
    ReportBlocksUpdateComponent,
    ReportBlocksDeleteDialogComponent,
    ReportBlocksCountryContentComponent,
    SortContentByPriorityPipe,
  ],
  exports: [ReportBlocksUpdateComponent],
})
export class ReportBlocksModule {}

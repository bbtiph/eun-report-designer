import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ReferenceTableSettingsComponent } from './list/reference-table-settings.component';
import { ReferenceTableSettingsDetailComponent } from './detail/reference-table-settings-detail.component';
import { ReferenceTableSettingsUpdateComponent } from './update/reference-table-settings-update.component';
import { ReferenceTableSettingsDeleteDialogComponent } from './delete/reference-table-settings-delete-dialog.component';
import { ReferenceTableSettingsRoutingModule } from './route/reference-table-settings-routing.module';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatDialogModule } from '@angular/material/dialog';
import { ReferenceTableSettingsManageComponent } from './manage/reference-table-settings-manage.component';
import { NgSelectModule } from '@ng-select/ng-select';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

@NgModule({
  imports: [
    SharedModule,
    ReferenceTableSettingsRoutingModule,
    MatSnackBarModule,
    MatDialogModule,
    NgSelectModule,
    MatProgressSpinnerModule,
  ],
  declarations: [
    ReferenceTableSettingsComponent,
    ReferenceTableSettingsDetailComponent,
    ReferenceTableSettingsUpdateComponent,
    ReferenceTableSettingsDeleteDialogComponent,
    ReferenceTableSettingsManageComponent,
  ],
  exports: [ReferenceTableSettingsManageComponent],
})
export class ReferenceTableSettingsModule {}

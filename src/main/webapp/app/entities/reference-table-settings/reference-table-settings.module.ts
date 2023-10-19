import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ReferenceTableSettingsComponent } from './list/reference-table-settings.component';
import { ReferenceTableSettingsDetailComponent } from './detail/reference-table-settings-detail.component';
import { ReferenceTableSettingsUpdateComponent } from './update/reference-table-settings-update.component';
import { ReferenceTableSettingsDeleteDialogComponent } from './delete/reference-table-settings-delete-dialog.component';
import { ReferenceTableSettingsRoutingModule } from './route/reference-table-settings-routing.module';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatDialogModule } from '@angular/material/dialog';

@NgModule({
  imports: [SharedModule, ReferenceTableSettingsRoutingModule, MatSnackBarModule, MatDialogModule],
  declarations: [
    ReferenceTableSettingsComponent,
    ReferenceTableSettingsDetailComponent,
    ReferenceTableSettingsUpdateComponent,
    ReferenceTableSettingsDeleteDialogComponent,
  ],
})
export class ReferenceTableSettingsModule {}

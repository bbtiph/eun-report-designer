import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ReferenceTableSettingsComponent } from './list/reference-table-settings.component';
import { ReferenceTableSettingsDetailComponent } from './detail/reference-table-settings-detail.component';
import { ReferenceTableSettingsUpdateComponent } from './update/reference-table-settings-update.component';
import { ReferenceTableSettingsDeleteDialogComponent } from './delete/reference-table-settings-delete-dialog.component';
import { ReferenceTableSettingsRoutingModule } from './route/reference-table-settings-routing.module';

@NgModule({
  imports: [SharedModule, ReferenceTableSettingsRoutingModule],
  declarations: [
    ReferenceTableSettingsComponent,
    ReferenceTableSettingsDetailComponent,
    ReferenceTableSettingsUpdateComponent,
    ReferenceTableSettingsDeleteDialogComponent,
  ],
})
export class ReferenceTableSettingsModule {}

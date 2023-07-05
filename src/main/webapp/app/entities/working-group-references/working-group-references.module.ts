import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { WorkingGroupReferencesComponent } from './list/working-group-references.component';
import { WorkingGroupReferencesDetailComponent } from './detail/working-group-references-detail.component';
import { WorkingGroupReferencesUpdateComponent } from './update/working-group-references-update.component';
import { WorkingGroupReferencesDeleteDialogComponent } from './delete/working-group-references-delete-dialog.component';
import { WorkingGroupReferencesRoutingModule } from './route/working-group-references-routing.module';

@NgModule({
  imports: [SharedModule, WorkingGroupReferencesRoutingModule],
  declarations: [
    WorkingGroupReferencesComponent,
    WorkingGroupReferencesDetailComponent,
    WorkingGroupReferencesUpdateComponent,
    WorkingGroupReferencesDeleteDialogComponent,
  ],
})
export class WorkingGroupReferencesModule {}

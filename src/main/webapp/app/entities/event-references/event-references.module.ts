import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { EventReferencesComponent } from './list/event-references.component';
import { EventReferencesDetailComponent } from './detail/event-references-detail.component';
import { EventReferencesUpdateComponent } from './update/event-references-update.component';
import { EventReferencesDeleteDialogComponent } from './delete/event-references-delete-dialog.component';
import { EventReferencesRoutingModule } from './route/event-references-routing.module';

@NgModule({
  imports: [SharedModule, EventReferencesRoutingModule],
  declarations: [
    EventReferencesComponent,
    EventReferencesDetailComponent,
    EventReferencesUpdateComponent,
    EventReferencesDeleteDialogComponent,
  ],
})
export class EventReferencesModule {}

import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { EventParticipantComponent } from './list/event-participant.component';
import { EventParticipantDetailComponent } from './detail/event-participant-detail.component';
import { EventParticipantUpdateComponent } from './update/event-participant-update.component';
import { EventParticipantDeleteDialogComponent } from './delete/event-participant-delete-dialog.component';
import { EventParticipantRoutingModule } from './route/event-participant-routing.module';

@NgModule({
  imports: [SharedModule, EventParticipantRoutingModule],
  declarations: [
    EventParticipantComponent,
    EventParticipantDetailComponent,
    EventParticipantUpdateComponent,
    EventParticipantDeleteDialogComponent,
  ],
})
export class EventParticipantModule {}

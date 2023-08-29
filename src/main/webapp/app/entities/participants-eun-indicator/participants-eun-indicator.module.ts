import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ParticipantsEunIndicatorComponent } from './list/participants-eun-indicator.component';
import { ParticipantsEunIndicatorDetailComponent } from './detail/participants-eun-indicator-detail.component';
import { ParticipantsEunIndicatorUpdateComponent } from './update/participants-eun-indicator-update.component';
import { ParticipantsEunIndicatorDeleteDialogComponent } from './delete/participants-eun-indicator-delete-dialog.component';
import { ParticipantsEunIndicatorRoutingModule } from './route/participants-eun-indicator-routing.module';

@NgModule({
  imports: [SharedModule, ParticipantsEunIndicatorRoutingModule],
  declarations: [
    ParticipantsEunIndicatorComponent,
    ParticipantsEunIndicatorDetailComponent,
    ParticipantsEunIndicatorUpdateComponent,
    ParticipantsEunIndicatorDeleteDialogComponent,
  ],
})
export class ParticipantsEunIndicatorModule {}

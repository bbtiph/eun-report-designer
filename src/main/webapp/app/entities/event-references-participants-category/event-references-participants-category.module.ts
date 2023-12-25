import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { EventReferencesParticipantsCategoryComponent } from './list/event-references-participants-category.component';
import { EventReferencesParticipantsCategoryDetailComponent } from './detail/event-references-participants-category-detail.component';
import { EventReferencesParticipantsCategoryUpdateComponent } from './update/event-references-participants-category-update.component';
import { EventReferencesParticipantsCategoryDeleteDialogComponent } from './delete/event-references-participants-category-delete-dialog.component';
import { EventReferencesParticipantsCategoryRoutingModule } from './route/event-references-participants-category-routing.module';

@NgModule({
  imports: [SharedModule, EventReferencesParticipantsCategoryRoutingModule],
  declarations: [
    EventReferencesParticipantsCategoryComponent,
    EventReferencesParticipantsCategoryDetailComponent,
    EventReferencesParticipantsCategoryUpdateComponent,
    EventReferencesParticipantsCategoryDeleteDialogComponent,
  ],
})
export class EventReferencesParticipantsCategoryModule {}

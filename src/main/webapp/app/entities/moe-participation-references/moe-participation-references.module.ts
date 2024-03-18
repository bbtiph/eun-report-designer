import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { MOEParticipationReferencesComponent } from './list/moe-participation-references.component';
import { MOEParticipationReferencesDetailComponent } from './detail/moe-participation-references-detail.component';
import { MOEParticipationReferencesUpdateComponent } from './update/moe-participation-references-update.component';
import { MOEParticipationReferencesDeleteDialogComponent } from './delete/moe-participation-references-delete-dialog.component';
import { MOEParticipationReferencesRoutingModule } from './route/moe-participation-references-routing.module';

@NgModule({
  imports: [SharedModule, MOEParticipationReferencesRoutingModule],
  declarations: [
    MOEParticipationReferencesComponent,
    MOEParticipationReferencesDetailComponent,
    MOEParticipationReferencesUpdateComponent,
    MOEParticipationReferencesDeleteDialogComponent,
  ],
})
export class MOEParticipationReferencesModule {}

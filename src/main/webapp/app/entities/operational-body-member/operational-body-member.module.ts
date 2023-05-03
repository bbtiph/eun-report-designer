import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { OperationalBodyMemberComponent } from './list/operational-body-member.component';
import { OperationalBodyMemberDetailComponent } from './detail/operational-body-member-detail.component';
import { OperationalBodyMemberUpdateComponent } from './update/operational-body-member-update.component';
import { OperationalBodyMemberDeleteDialogComponent } from './delete/operational-body-member-delete-dialog.component';
import { OperationalBodyMemberRoutingModule } from './route/operational-body-member-routing.module';

@NgModule({
  imports: [SharedModule, OperationalBodyMemberRoutingModule],
  declarations: [
    OperationalBodyMemberComponent,
    OperationalBodyMemberDetailComponent,
    OperationalBodyMemberUpdateComponent,
    OperationalBodyMemberDeleteDialogComponent,
  ],
})
export class OperationalBodyMemberModule {}

import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { EunTeamMemberComponent } from './list/eun-team-member.component';
import { EunTeamMemberDetailComponent } from './detail/eun-team-member-detail.component';
import { EunTeamMemberUpdateComponent } from './update/eun-team-member-update.component';
import { EunTeamMemberDeleteDialogComponent } from './delete/eun-team-member-delete-dialog.component';
import { EunTeamMemberRoutingModule } from './route/eun-team-member-routing.module';

@NgModule({
  imports: [SharedModule, EunTeamMemberRoutingModule],
  declarations: [EunTeamMemberComponent, EunTeamMemberDetailComponent, EunTeamMemberUpdateComponent, EunTeamMemberDeleteDialogComponent],
})
export class EunTeamMemberModule {}

import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { EunTeamComponent } from './list/eun-team.component';
import { EunTeamDetailComponent } from './detail/eun-team-detail.component';
import { EunTeamUpdateComponent } from './update/eun-team-update.component';
import { EunTeamDeleteDialogComponent } from './delete/eun-team-delete-dialog.component';
import { EunTeamRoutingModule } from './route/eun-team-routing.module';

@NgModule({
  imports: [SharedModule, EunTeamRoutingModule],
  declarations: [EunTeamComponent, EunTeamDetailComponent, EunTeamUpdateComponent, EunTeamDeleteDialogComponent],
})
export class EunTeamModule {}

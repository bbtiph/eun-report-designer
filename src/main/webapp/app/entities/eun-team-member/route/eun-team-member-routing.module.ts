import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EunTeamMemberComponent } from '../list/eun-team-member.component';
import { EunTeamMemberDetailComponent } from '../detail/eun-team-member-detail.component';
import { EunTeamMemberUpdateComponent } from '../update/eun-team-member-update.component';
import { EunTeamMemberRoutingResolveService } from './eun-team-member-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const eunTeamMemberRoute: Routes = [
  {
    path: '',
    component: EunTeamMemberComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EunTeamMemberDetailComponent,
    resolve: {
      eunTeamMember: EunTeamMemberRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EunTeamMemberUpdateComponent,
    resolve: {
      eunTeamMember: EunTeamMemberRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EunTeamMemberUpdateComponent,
    resolve: {
      eunTeamMember: EunTeamMemberRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(eunTeamMemberRoute)],
  exports: [RouterModule],
})
export class EunTeamMemberRoutingModule {}

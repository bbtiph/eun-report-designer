import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EunTeamComponent } from '../list/eun-team.component';
import { EunTeamDetailComponent } from '../detail/eun-team-detail.component';
import { EunTeamUpdateComponent } from '../update/eun-team-update.component';
import { EunTeamRoutingResolveService } from './eun-team-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const eunTeamRoute: Routes = [
  {
    path: '',
    component: EunTeamComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EunTeamDetailComponent,
    resolve: {
      eunTeam: EunTeamRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EunTeamUpdateComponent,
    resolve: {
      eunTeam: EunTeamRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EunTeamUpdateComponent,
    resolve: {
      eunTeam: EunTeamRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(eunTeamRoute)],
  exports: [RouterModule],
})
export class EunTeamRoutingModule {}

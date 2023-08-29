import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ParticipantsEunIndicatorComponent } from '../list/participants-eun-indicator.component';
import { ParticipantsEunIndicatorDetailComponent } from '../detail/participants-eun-indicator-detail.component';
import { ParticipantsEunIndicatorUpdateComponent } from '../update/participants-eun-indicator-update.component';
import { ParticipantsEunIndicatorRoutingResolveService } from './participants-eun-indicator-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const participantsEunIndicatorRoute: Routes = [
  {
    path: '',
    component: ParticipantsEunIndicatorComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ParticipantsEunIndicatorDetailComponent,
    resolve: {
      participantsEunIndicator: ParticipantsEunIndicatorRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ParticipantsEunIndicatorUpdateComponent,
    resolve: {
      participantsEunIndicator: ParticipantsEunIndicatorRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ParticipantsEunIndicatorUpdateComponent,
    resolve: {
      participantsEunIndicator: ParticipantsEunIndicatorRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(participantsEunIndicatorRoute)],
  exports: [RouterModule],
})
export class ParticipantsEunIndicatorRoutingModule {}

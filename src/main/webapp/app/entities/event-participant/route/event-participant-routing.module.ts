import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EventParticipantComponent } from '../list/event-participant.component';
import { EventParticipantDetailComponent } from '../detail/event-participant-detail.component';
import { EventParticipantUpdateComponent } from '../update/event-participant-update.component';
import { EventParticipantRoutingResolveService } from './event-participant-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const eventParticipantRoute: Routes = [
  {
    path: '',
    component: EventParticipantComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EventParticipantDetailComponent,
    resolve: {
      eventParticipant: EventParticipantRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EventParticipantUpdateComponent,
    resolve: {
      eventParticipant: EventParticipantRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EventParticipantUpdateComponent,
    resolve: {
      eventParticipant: EventParticipantRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(eventParticipantRoute)],
  exports: [RouterModule],
})
export class EventParticipantRoutingModule {}

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EventInOrganizationComponent } from '../list/event-in-organization.component';
import { EventInOrganizationDetailComponent } from '../detail/event-in-organization-detail.component';
import { EventInOrganizationUpdateComponent } from '../update/event-in-organization-update.component';
import { EventInOrganizationRoutingResolveService } from './event-in-organization-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const eventInOrganizationRoute: Routes = [
  {
    path: '',
    component: EventInOrganizationComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EventInOrganizationDetailComponent,
    resolve: {
      eventInOrganization: EventInOrganizationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EventInOrganizationUpdateComponent,
    resolve: {
      eventInOrganization: EventInOrganizationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EventInOrganizationUpdateComponent,
    resolve: {
      eventInOrganization: EventInOrganizationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(eventInOrganizationRoute)],
  exports: [RouterModule],
})
export class EventInOrganizationRoutingModule {}

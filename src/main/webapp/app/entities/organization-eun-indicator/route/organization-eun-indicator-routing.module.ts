import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { OrganizationEunIndicatorComponent } from '../list/organization-eun-indicator.component';
import { OrganizationEunIndicatorDetailComponent } from '../detail/organization-eun-indicator-detail.component';
import { OrganizationEunIndicatorUpdateComponent } from '../update/organization-eun-indicator-update.component';
import { OrganizationEunIndicatorRoutingResolveService } from './organization-eun-indicator-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const organizationEunIndicatorRoute: Routes = [
  {
    path: '',
    component: OrganizationEunIndicatorComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: OrganizationEunIndicatorDetailComponent,
    resolve: {
      organizationEunIndicator: OrganizationEunIndicatorRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: OrganizationEunIndicatorUpdateComponent,
    resolve: {
      organizationEunIndicator: OrganizationEunIndicatorRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: OrganizationEunIndicatorUpdateComponent,
    resolve: {
      organizationEunIndicator: OrganizationEunIndicatorRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(organizationEunIndicatorRoute)],
  exports: [RouterModule],
})
export class OrganizationEunIndicatorRoutingModule {}

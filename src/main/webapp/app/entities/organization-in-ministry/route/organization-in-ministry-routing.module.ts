import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { OrganizationInMinistryComponent } from '../list/organization-in-ministry.component';
import { OrganizationInMinistryDetailComponent } from '../detail/organization-in-ministry-detail.component';
import { OrganizationInMinistryUpdateComponent } from '../update/organization-in-ministry-update.component';
import { OrganizationInMinistryRoutingResolveService } from './organization-in-ministry-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const organizationInMinistryRoute: Routes = [
  {
    path: '',
    component: OrganizationInMinistryComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: OrganizationInMinistryDetailComponent,
    resolve: {
      organizationInMinistry: OrganizationInMinistryRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: OrganizationInMinistryUpdateComponent,
    resolve: {
      organizationInMinistry: OrganizationInMinistryRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: OrganizationInMinistryUpdateComponent,
    resolve: {
      organizationInMinistry: OrganizationInMinistryRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(organizationInMinistryRoute)],
  exports: [RouterModule],
})
export class OrganizationInMinistryRoutingModule {}

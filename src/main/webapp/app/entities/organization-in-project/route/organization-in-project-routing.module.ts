import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { OrganizationInProjectComponent } from '../list/organization-in-project.component';
import { OrganizationInProjectDetailComponent } from '../detail/organization-in-project-detail.component';
import { OrganizationInProjectUpdateComponent } from '../update/organization-in-project-update.component';
import { OrganizationInProjectRoutingResolveService } from './organization-in-project-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const organizationInProjectRoute: Routes = [
  {
    path: '',
    component: OrganizationInProjectComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: OrganizationInProjectDetailComponent,
    resolve: {
      organizationInProject: OrganizationInProjectRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: OrganizationInProjectUpdateComponent,
    resolve: {
      organizationInProject: OrganizationInProjectRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: OrganizationInProjectUpdateComponent,
    resolve: {
      organizationInProject: OrganizationInProjectRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(organizationInProjectRoute)],
  exports: [RouterModule],
})
export class OrganizationInProjectRoutingModule {}

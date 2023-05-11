import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PrivilegeComponent } from '../list/privilege.component';
import { PrivilegeDetailComponent } from '../detail/privilege-detail.component';
import { PrivilegeUpdateComponent } from '../update/privilege-update.component';
import { PrivilegeRoutingResolveService } from './privilege-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const privilegeRoute: Routes = [
  {
    path: '',
    component: PrivilegeComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PrivilegeDetailComponent,
    resolve: {
      privilege: PrivilegeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PrivilegeUpdateComponent,
    resolve: {
      privilege: PrivilegeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PrivilegeUpdateComponent,
    resolve: {
      privilege: PrivilegeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(privilegeRoute)],
  exports: [RouterModule],
})
export class PrivilegeRoutingModule {}

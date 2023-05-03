import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { OperationalBodyMemberComponent } from '../list/operational-body-member.component';
import { OperationalBodyMemberDetailComponent } from '../detail/operational-body-member-detail.component';
import { OperationalBodyMemberUpdateComponent } from '../update/operational-body-member-update.component';
import { OperationalBodyMemberRoutingResolveService } from './operational-body-member-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const operationalBodyMemberRoute: Routes = [
  {
    path: '',
    component: OperationalBodyMemberComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: OperationalBodyMemberDetailComponent,
    resolve: {
      operationalBodyMember: OperationalBodyMemberRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: OperationalBodyMemberUpdateComponent,
    resolve: {
      operationalBodyMember: OperationalBodyMemberRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: OperationalBodyMemberUpdateComponent,
    resolve: {
      operationalBodyMember: OperationalBodyMemberRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(operationalBodyMemberRoute)],
  exports: [RouterModule],
})
export class OperationalBodyMemberRoutingModule {}

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { OperationalBodyComponent } from '../list/operational-body.component';
import { OperationalBodyDetailComponent } from '../detail/operational-body-detail.component';
import { OperationalBodyUpdateComponent } from '../update/operational-body-update.component';
import { OperationalBodyRoutingResolveService } from './operational-body-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const operationalBodyRoute: Routes = [
  {
    path: '',
    component: OperationalBodyComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: OperationalBodyDetailComponent,
    resolve: {
      operationalBody: OperationalBodyRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: OperationalBodyUpdateComponent,
    resolve: {
      operationalBody: OperationalBodyRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: OperationalBodyUpdateComponent,
    resolve: {
      operationalBody: OperationalBodyRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(operationalBodyRoute)],
  exports: [RouterModule],
})
export class OperationalBodyRoutingModule {}
